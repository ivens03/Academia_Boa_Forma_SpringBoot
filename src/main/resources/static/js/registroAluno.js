/* src/main/resources/static/js/registroAluno.js */

// As funções do modal precisam ser acessíveis globalmente ou no mesmo escopo das funções que as chamam.
// Colocamos a lógica do modal e a função fetchAddress juntas para garantir o acesso.

// Função para exibir o modal de feedback
function showModal(title, message, type = 'success') {
    const modal = document.getElementById('feedback-modal');
    const icons = {
        success: `<i data-lucide="check-circle" class="w-10 h-10 text-green-500"></i>`,
        error: `<i data-lucide="x-circle" class="w-10 h-10 text-red-500"></i>`,
        warning: `<i data-lucide="alert-triangle" class="w-10 h-10 text-yellow-500"></i>`
    };
    const colors = {
        success: 'bg-green-100',
        error: 'bg-red-100',
        warning: 'bg-yellow-100'
    };

    document.getElementById('modal-title').innerText = title;
    document.getElementById('modal-message').innerText = message;
    const iconContainer = document.getElementById('modal-icon');
    iconContainer.innerHTML = icons[type];
    iconContainer.className = `mx-auto flex items-center justify-center h-12 w-12 rounded-full ${colors[type]}`;
    lucide.createIcons();
    modal.style.display = 'block';
}


// Função para buscar endereço via CEP (usando a API ViaCEP)
function fetchAddress() {
    const cepInput = document.getElementById('cep');
    const cep = cepInput.value.replace(/\D/g, '');
    if (cep.length !== 8) return;

    fetch(`https://viacep.com.br/ws/${cep}/json/`)
        .then(response => response.json())
        .then(data => {
            if (!data.erro) {
                document.getElementById('logradouro').value = data.logradouro || '';
                document.getElementById('bairro').value = data.bairro || '';
            } else {
                document.getElementById('logradouro').value = '';
                document.getElementById('bairro').value = '';
                showModal('Erro', 'CEP não encontrado.', 'error');
            }
        })
        .catch(error => {
            console.error('Erro ao buscar CEP:', error);
            showModal('Erro de Rede', 'Não foi possível buscar o CEP. Verifique sua conexão.', 'error');
        });
}


document.addEventListener('DOMContentLoaded', () => {
    // Inicializa os ícones do Lucide
    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }

    // Lógica para fechar o modal
    const modal = document.getElementById('feedback-modal');
    const modalCloseBtn = document.getElementById('modal-close');
    modalCloseBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });
    window.addEventListener('click', (event) => {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });


    // Carrega os professores ativos
    async function carregarProfessores() {
        try {
            const response = await fetch('/professor/ativos');
            if (!response.ok) throw new Error(`Erro HTTP: ${response.status}`);

            const professores = await response.json();
            const select = document.getElementById('responsible_teacher');
            select.innerHTML = '<option value="">Selecione um professor</option>';

            professores.forEach(professor => {
                const option = document.createElement('option');
                option.value = professor.id;
                option.textContent = professor.nome;
                select.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar professores:', error);
            const select = document.getElementById('responsible_teacher');
            select.innerHTML = '<option value="">Erro ao carregar</option>';
        }
    }
    carregarProfessores();

    // Carrega os gêneros do backend
    const genderSelect = document.getElementById('gender');
    fetch('/alunos/generos')
        .then(response => response.json())
        .then(generos => {
            generos.forEach(genero => {
                const option = new Option(genero.replace('_', ' ').toLowerCase(), genero);
                genderSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar gêneros:', error));

    // Carrega os focos do backend
    const focusSelect = document.getElementById('student_focus');
    fetch('/alunos/focoAluno')
        .then(response => response.json())
        .then(focos => {
            focos.forEach(foco => {
                const option = new Option(foco.replace(/_/g, ' ').toLowerCase(), foco);
                focusSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar focos:', error));

    // Lógica para calcular a idade
    document.getElementById('birth_date').addEventListener('change', (e) => {
        const ageInput = document.getElementById('age');
        if (!e.target.value) {
            ageInput.value = '';
            return;
        }
        const birthDate = new Date(e.target.value);
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        ageInput.value = age >= 0 ? age : '';
    });

    // Funções de máscara
    const applyMask = (element, maskFunction) => {
        element.addEventListener('input', maskFunction);
    };

    const cpfMask = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        e.target.value = value.slice(0, 14);
    };

    const phoneMask = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 10) {
            value = value.replace(/^(\d\d)(\d{5})(\d{4}).*/,"($1) $2-$3");
        } else if (value.length > 5) {
            value = value.replace(/^(\d\d)(\d{4})(\d{0,4}).*/,"($1) $2-$3");
        } else if (value.length > 2) {
            value = value.replace(/^(\d\d)(\d{0,5}).*/,"($1) $2");
        } else {
            value = value.replace(/^(\d*)/, "($1");
        }
        e.target.value = value;
    };

    const cepMask = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/^(\d{5})(\d)/, '$1-$2');
        e.target.value = value.slice(0, 9);
    };

    applyMask(document.getElementById('cpf'), cpfMask);
    applyMask(document.getElementById('phone'), phoneMask);
    applyMask(document.getElementById('emergency_phone'), phoneMask);
    applyMask(document.getElementById('cep'), cepMask);

    // Lógica de submissão do formulário
    const form = document.getElementById('student-form');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (!form.checkValidity()) {
            form.reportValidity();
            showModal('Atenção', 'Por favor, preencha todos os campos obrigatórios.', 'warning');
            return;
        }

        const unmask = (value) => value.replace(/\D/g, '');

        const formData = {
            nome: document.getElementById('name').value,
            cpf: unmask(document.getElementById('cpf').value),
            email: document.getElementById('email').value,
            telefone: unmask(document.getElementById('phone').value),
            telefoneEmergencia: unmask(document.getElementById('emergency_phone').value),
            dataNascimento: document.getElementById('birth_date').value,
            idade: parseInt(document.getElementById('age').value),
            genero: document.getElementById('gender').value,
            possuiDoenca: document.querySelector('input[x-model="hasDisease"]:checked')?.value === 'true',
            descricaoDoenca: document.getElementById('disease_description')?.value || null,
            objetivo: document.getElementById('student_focus').value,
            professorResponsavelId: document.getElementById('responsible_teacher').value,
            cep: unmask(document.getElementById('cep').value),
            logradouro: document.getElementById('logradouro').value,
            numero: document.getElementById('numero').value,
            complemento: document.getElementById('complemento').value,
            bairro: document.getElementById('bairro').value
        };

        try {
            const response = await fetch('/alunos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                showModal('Sucesso!', 'Aluno cadastrado com sucesso!', 'success');
                setTimeout(() => {
                    window.location.href = '/homeGestor';
                }, 2000);
            } else {
                const errorData = await response.json();
                showModal('Erro', errorData.message || 'Não foi possível cadastrar o aluno.', 'error');
            }
        } catch (error) {
            console.error('Erro na submissão:', error);
            showModal('Erro de Rede', 'Ocorreu um erro de rede. Tente novamente.', 'error');
        }
    });
});