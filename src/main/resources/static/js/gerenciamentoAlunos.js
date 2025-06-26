/* src/main/resources/static/js/gerenciamentoAlunos.js */

// Configuração para o Tailwind JIT CDN garantir que as classes dinâmicas sejam geradas.
// É uma boa prática manter isso junto com o JS que as utiliza.
tailwind.config = {
    safelist: [
        'border-red-500', 'bg-red-100', 'text-red-800', 'text-red-600',
        'border-yellow-500', 'bg-yellow-100', 'text-yellow-800',
        'border-green-500', 'bg-green-100', 'text-green-800',
        'border-gray-500', 'bg-gray-100', 'text-gray-800',
    ]
}

document.addEventListener('DOMContentLoaded', () => {

    // --- ESTADO DA APLICAÇÃO ---
    let students = [];
    let selectedStudentId = null;
    let searchTerm = '';
    let paymentStatusFilter = 'all';
    let userStatusFilter = 'all';
    let searchTimeout = null;
    let isFetching = false;

    // --- ELEMENTOS DO DOM ---
    const studentListEl = document.getElementById('student-list');
    const searchInputEl = document.getElementById('search-input');
    const initialViewEl = document.getElementById('initial-view');
    const studentDetailsContainerEl = document.getElementById('student-details-container');
    const paymentModalEl = document.getElementById('payment-modal');
    const paymentFormEl = document.getElementById('payment-form');
    const cancelPaymentBtn = document.getElementById('cancel-payment-btn');
    const confirmModalEl = document.getElementById('confirm-modal');

    // Elementos dos filtros dropdown
    const paymentStatusFilterBtn = document.getElementById('payment-status-filter-btn');
    const paymentStatusFilterText = document.getElementById('payment-status-filter-text');
    const paymentStatusFilterOptions = document.getElementById('payment-status-filter-options');
    const userStatusFilterBtn = document.getElementById('user-status-filter-btn');
    const userStatusFilterText = document.getElementById('user-status-filter-text');
    const userStatusFilterOptions = document.getElementById('user-status-filter-options');

    // --- FUNÇÕES HELPER ---
    const calculateAge = (dob) => {
        if (!dob) return '';
        const birthDate = new Date(dob);
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) age--;
        return age;
    };

    const getPaymentStatus = (dueDate) => {
        if (!dueDate) return { key: 'na', text: 'N/A', color: 'gray-500', variant: 'bg-gray-100 text-gray-800' };
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const vencimento = new Date(dueDate);
        vencimento.setHours(23, 59, 59, 999);
        const diffTime = vencimento - today;
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        if (diffDays < 0) return { key: 'overdue', text: 'Atrasado', color: 'red-500', variant: 'bg-red-100 text-red-800' };
        if (diffDays <= 7) return { key: 'due', text: 'Perto do Vencimento', color: 'yellow-500', variant: 'bg-yellow-100 text-yellow-800' };
        return { key: 'paid', text: 'Em dia', color: 'green-500', variant: 'bg-green-100 text-green-800' };
    };

    const formatDate = (dateString) => {
        if (!dateString) return '';
        const date = new Date(dateString);
        const userTimezoneOffset = date.getTimezoneOffset() * 60000;
        return new Date(date.getTime() + userTimezoneOffset).toLocaleDateString('pt-BR');
    };

    // --- LÓGICA DE API ---
    async function buscarAlunos(nome = '') {
        isFetching = true;
        renderStudentList(); // Mostra estado de carregamento

        try {
            const url = nome ? `/alunos/all?nome=${encodeURIComponent(nome)}` : '/alunos/all';
            const response = await fetch(url);
            if (!response.ok) throw new Error(`Erro ao buscar alunos: ${response.status}`);

            const alunosData = await response.json();
            students = alunosData.content.map(aluno => ({
                ...aluno,
                pagamentos: aluno.pagamentos || [],
                endereco: aluno.endereco || { cep: '', logradouro: '', numero: '', complemento: '', bairro: '' },
                ativo: aluno.ativo !== false,
                foto: aluno.foto || `https://ui-avatars.com/api/?name=${encodeURIComponent(aluno.nome || '')}&background=262D68&color=fff`
            }));

            renderStudentList();
        } catch (error) {
            console.error('Erro ao buscar alunos:', error);
            studentListEl.innerHTML = `<li class="text-center p-4 text-red-500"><i data-lucide="alert-circle" class="w-5 h-5 inline-block mr-1"></i>Erro ao carregar alunos: ${error.message}</li>`;
            lucide.createIcons();
        } finally {
            isFetching = false;
        }
    }

    // --- FUNÇÕES DE RENDERIZAÇÃO ---
    function renderStudentList() {
        if (isFetching) {
            studentListEl.innerHTML = `<li class="flex items-center justify-center p-4 text-gray-400"><i data-lucide="loader-2" class="w-5 h-5 mr-2 animate-spin"></i>Buscando alunos...</li>`;
            lucide.createIcons();
            return;
        }

        const filteredStudents = students.filter(student => {
            const searchMatch = !searchTerm || (student.nome && student.nome.toLowerCase().includes(searchTerm.toLowerCase()));
            const payment = [...(student.pagamentos || [])].sort((a, b) => new Date(b.vencimento) - new Date(a.vencimento))[0];
            const status = getPaymentStatus(payment ? payment.vencimento : null);
            const paymentStatusMatch = paymentStatusFilter === 'all' || status.key === paymentStatusFilter;
            const userStatusMatch = userStatusFilter === 'all' || (userStatusFilter === 'active' && student.ativo) || (userStatusFilter === 'inactive' && !student.ativo);
            return searchMatch && paymentStatusMatch && userStatusMatch;
        });

        if (filteredStudents.length === 0) {
            studentListEl.innerHTML = `<li class="text-center text-gray-400 p-4"><i data-lucide="search" class="w-5 h-5 inline-block mr-1"></i>Nenhum aluno encontrado.</li>`;
            lucide.createIcons();
            return;
        }

        studentListEl.innerHTML = filteredStudents.map(student => {
            const payment = [...(student.pagamentos || [])].sort((a, b) => new Date(b.vencimento) - new Date(a.vencimento))[0];
            const status = getPaymentStatus(payment ? payment.vencimento : null);
            return `
                <li class="flex items-center p-3 rounded-lg cursor-pointer transition-all mb-2 ${selectedStudentId === student.id ? 'bg-indigo-500 bg-opacity-30' : 'hover:bg-gray-700'}" data-id="${student.id}">
                    <div class="relative">
                        <img src="${student.foto}" alt="${student.nome}" class="w-12 h-12 rounded-full object-cover border-2 border-${status.color}" onerror="this.onerror=null; this.src='https://ui-avatars.com/api/?name=${encodeURIComponent(student.nome)}&background=262D68&color=fff'" />
                    </div>
                    <div class="flex-grow ml-3 truncate">
                        <div class="font-medium text-gray-100 truncate">${student.nome}</div>
                        <span class="text-xs px-2 py-0.5 rounded-full ${student.ativo ? 'bg-green-200 text-green-800' : 'bg-gray-500 text-white'}">
                            ${student.ativo ? 'Ativo' : 'Inativo'}
                        </span>
                    </div>
                </li>
            `;
        }).join('');
    }

    function renderStudentDetails() {
        if (selectedStudentId === null) {
            initialViewEl.classList.remove('hidden');
            studentDetailsContainerEl.classList.add('hidden');
            return;
        }

        const student = students.find(s => s.id === selectedStudentId);
        if (!student) return;

        const latestPayment = [...(student.pagamentos || [])].sort((a, b) => new Date(b.vencimento) - new Date(a.vencimento))[0];
        const status = getPaymentStatus(latestPayment ? latestPayment.vencimento : null);

        const paymentsHTML = (student.pagamentos || [])
            .sort((a, b) => new Date(b.dataPagamento) - new Date(a.dataPagamento))
            .map(p => {
                const pStatus = getPaymentStatus(p.vencimento);
                return `<tr>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">${formatDate(p.dataPagamento)}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">${formatDate(p.vencimento)}</td>
                    <td class="px-6 py-4 whitespace-nowrap"><span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${pStatus.variant}">${pStatus.text}</span></td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">R$ ${(p.valor || 0).toFixed(2)}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">${p.formaPagamento}</td>
                </tr>`;
            }).join('');

        studentDetailsContainerEl.innerHTML = `
            <div class="bg-white p-6 rounded-xl shadow-sm">
                <div class="flex items-start justify-between mb-6">
                    <div>
                        <div class="flex items-center gap-3">
                           <h2 class="text-3xl font-bold text-gray-900">${student.nome}</h2>
                           <span class="text-sm mt-1 px-2.5 py-1 rounded-full font-medium ${student.ativo ? 'bg-green-100 text-green-800' : 'bg-gray-200 text-gray-800'}">${student.ativo ? 'Ativo' : 'Inativo'}</span>
                        </div>
                        <div class="mt-2 inline-flex items-center px-3 py-1 rounded-full text-sm font-medium ${status.variant}">
                            <i data-lucide="${status.key === 'paid' ? 'check-circle' : (status.key === 'due' ? 'alert-triangle' : 'x-circle')}" class="w-4 h-4 mr-2"></i>
                            Mensalidade ${status.text}
                        </div>
                    </div>
                    <img src="${student.foto}" alt="${student.nome}" class="w-20 h-20 rounded-full object-cover border-4 border-white shadow-md -mt-2"/>
                </div>
                <div class="border-b border-gray-200"><nav id="tabs" class="-mb-px flex space-x-6">
                    <button data-tab="personal" class="py-3 px-1 border-b-2 font-medium text-sm border-indigo-500 text-indigo-600">Dados Pessoais</button>
                    <button data-tab="address" class="py-3 px-1 border-b-2 font-medium text-sm border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300">Endereço</button>
                    <button data-tab="payments" class="py-3 px-1 border-b-2 font-medium text-sm border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300">Pagamentos</button>
                </nav></div>

                <div class="mt-6">
                    <div id="tab-personal" class=""><div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">CPF</label><p class="text-gray-800 font-semibold">${student.cpf}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Gênero</label><p class="text-gray-800 font-semibold">${student.genero}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Data de Nascimento</label><p class="text-gray-800 font-semibold">${formatDate(student.dataNascimento)}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Idade</label><p class="text-gray-800 font-semibold">${calculateAge(student.dataNascimento)} anos</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">E-mail</label><p class="text-gray-800 font-semibold">${student.email}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Telefone</label><p class="text-gray-800 font-semibold">${student.telefone}</p></div>
                    </div></div>
                    <div id="tab-address" class="hidden"><div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">CEP</label><p class="text-gray-800 font-semibold">${student.endereco.cep}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Logradouro</label><p class="text-gray-800 font-semibold">${student.endereco.logradouro}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Número</label><p class="text-gray-800 font-semibold">${student.endereco.numero}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Complemento</label><p class="text-gray-800 font-semibold">${student.endereco.complemento}</p></div>
                        <div class="bg-gray-50 p-4 rounded-lg"><label class="text-sm font-medium text-gray-500">Bairro</label><p class="text-gray-800 font-semibold">${student.endereco.bairro}</p></div>
                    </div></div>
                    <div id="tab-payments" class="hidden">
                        <div class="flex justify-between items-center mb-4">
                             <h3 class="text-xl font-bold text-gray-800">Histórico de Pagamentos</h3>
                             <button id="add-payment-btn" class="flex items-center gap-2 px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors">
                                <i data-lucide="plus-circle" class="w-5 h-5"></i>Registrar Pagamento
                             </button>
                        </div>
                        <div class="overflow-x-auto"><table class="min-w-full bg-white">
                            <thead class="bg-gray-50"><tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Data Pag.</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Vencimento</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Valor</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Forma Pag.</th>
                            </tr></thead>
                            <tbody class="divide-y divide-gray-200">${paymentsHTML}</tbody>
                        </table></div>
                    </div>
                </div>

                <div class="mt-8 pt-6 border-t border-gray-200">
                    <h3 class="text-lg font-bold text-gray-800 mb-2">Ações do Aluno</h3>
                    <div class="flex items-center gap-4">
                         <button id="toggle-active-btn" class="px-4 py-2 rounded-md font-medium text-sm ${student.ativo ? 'bg-yellow-100 text-yellow-800 hover:bg-yellow-200' : 'bg-gray-200 text-gray-800 hover:bg-gray-300'}">${student.ativo ? 'Desativar Aluno' : 'Ativar Aluno'}</button>
                    </div>
                </div>
            </div>`;

        initialViewEl.classList.add('hidden');
        studentDetailsContainerEl.classList.remove('hidden');
        addDetailsEventListeners();
        lucide.createIcons();
    }

    // --- EVENT LISTENERS E LÓGICA DE INTERAÇÃO ---
    function addDetailsEventListeners() {
        document.getElementById('tabs').addEventListener('click', (e) => {
            if (e.target.tagName === 'BUTTON') {
                const tabName = e.target.dataset.tab;
                document.querySelectorAll('[id^="tab-"]').forEach(el => el.classList.add('hidden'));
                document.getElementById(`tab-${tabName}`).classList.remove('hidden');
                document.querySelectorAll('#tabs button').forEach(btn => {
                    btn.classList.remove('border-indigo-500', 'text-indigo-600');
                    btn.classList.add('border-transparent', 'text-gray-500');
                });
                e.target.classList.add('border-indigo-500', 'text-indigo-600');
            }
        });
        document.getElementById('add-payment-btn').addEventListener('click', () => paymentModalEl.classList.remove('hidden'));
        document.getElementById('toggle-active-btn').addEventListener('click', showToggleActiveConfirm);
    }

    function showConfirmationModal({ title, message, confirmText, onConfirm }) {
        document.getElementById('confirm-modal-title').textContent = title;
        document.getElementById('confirm-modal-message').textContent = message;
        const confirmBtn = document.getElementById('confirm-action-btn');
        confirmBtn.textContent = confirmText;
        confirmModalEl.classList.remove('hidden');

        const cancelBtn = document.getElementById('cancel-confirm-btn');
        const confirmAction = () => { onConfirm(); closeModal(); };
        const closeModal = () => {
            confirmModalEl.classList.add('hidden');
            confirmBtn.removeEventListener('click', confirmAction);
            cancelBtn.removeEventListener('click', closeModal);
        };
        confirmBtn.addEventListener('click', confirmAction);
        cancelBtn.addEventListener('click', closeModal);
    }

    function showToggleActiveConfirm() {
        const student = students.find(s => s.id === selectedStudentId);
        const isActivating = !student.ativo;
        showConfirmationModal({
            title: `${isActivating ? 'Ativar' : 'Desativar'} Aluno`,
            message: `Você tem certeza que deseja ${isActivating ? 'ativar' : 'desativar'} este aluno?`,
            confirmText: `Sim, ${isActivating ? 'Ativar' : 'Desativar'}`,
            onConfirm: () => {
                student.ativo = isActivating;
                renderStudentDetails();
                renderStudentList();
            }
        });
    }

    function setupDropdown(buttonEl, optionsEl, onSelect) {
        buttonEl.addEventListener('click', (e) => {
            e.stopPropagation();
            optionsEl.classList.toggle('hidden');
        });
        optionsEl.querySelectorAll('a').forEach(option => {
            option.addEventListener('click', (e) => {
                e.preventDefault();
                onSelect(option.dataset.filter, option.querySelector('span').textContent);
                optionsEl.classList.add('hidden');
            });
        });
    }

    document.addEventListener('click', (e) => {
        if (!paymentStatusFilterBtn.contains(e.target)) paymentStatusFilterOptions.classList.add('hidden');
        if (!userStatusFilterBtn.contains(e.target)) userStatusFilterOptions.classList.add('hidden');
    });

    searchInputEl.addEventListener('input', (e) => {
        clearTimeout(searchTimeout);
        searchTerm = e.target.value;
        searchTimeout = setTimeout(() => renderStudentList(), 300);
    });

    studentListEl.addEventListener('click', (e) => {
        const li = e.target.closest('li');
        if (li && li.dataset.id) {
            selectedStudentId = parseInt(li.dataset.id, 10);
            renderStudentList();
            renderStudentDetails();
        }
    });

    cancelPaymentBtn.addEventListener('click', () => paymentModalEl.classList.add('hidden'));
    paymentFormEl.addEventListener('submit', (e) => {
        e.preventDefault();
        const student = students.find(s => s.id === selectedStudentId);
        student.pagamentos.push({
            id: Date.now(),
            valor: parseFloat(e.target.valor.value),
            formaPagamento: e.target.formaPagamento.value,
            vencimento: e.target.vencimento.value,
            dataPagamento: new Date().toISOString().split('T')[0]
        });
        renderStudentDetails();
        renderStudentList();
        paymentModalEl.classList.add('hidden');
        paymentFormEl.reset();
    });

    // --- INICIALIZAÇÃO ---
    lucide.createIcons();
    setupDropdown(paymentStatusFilterBtn, paymentStatusFilterOptions, (value, text) => {
        paymentStatusFilter = value;
        paymentStatusFilterText.textContent = text;
        renderStudentList();
    });
    setupDropdown(userStatusFilterBtn, userStatusFilterOptions, (value, text) => {
        userStatusFilter = value;
        userStatusFilterText.textContent = text;
        renderStudentList();
    });
    buscarAlunos();
});