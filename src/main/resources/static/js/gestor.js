/* src/main/resources/static/js/gestor.js */

document.addEventListener('DOMContentLoaded', async () => {
    // Inicializa os ícones Lucide
    lucide.createIcons();

    const userListContainer = document.getElementById('user-list');
    const searchInput = document.getElementById('user-search');
    let alunos = [];

    // Função para formatar CPF
    const formatarCPF = (cpf) => {
        if (!cpf) return '';
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    };

    // Função para formatar telefone
    const formatarTelefone = (telefone) => {
        if (!telefone) return '';
        const numero = telefone.replace(/\D/g, '');
        if (numero.length === 11) {
            return `(${numero.substring(0, 2)}) ${numero.substring(2, 7)}-${numero.substring(7)}`;
        }
        return telefone;
    };

    // Função para renderizar a lista de alunos
    const renderAlunos = (alunosParaRender) => {
        userListContainer.innerHTML = '';

        if (alunosParaRender.length === 0) {
            userListContainer.innerHTML = `
                <div class="text-center py-4">
                    <i data-lucide="search" class="w-8 h-8 mx-auto text-gray-400 mb-2"></i>
                    <p class="text-gray-500">Nenhum aluno encontrado</p>
                </div>`;
            lucide.createIcons();
            return;
        }

        alunosParaRender.forEach(aluno => {
            const alunoElement = document.createElement('div');
            alunoElement.className = 'relative bg-white rounded-lg p-3 mb-2 shadow-sm border border-gray-100 hover:shadow-md transition-shadow';

            alunoElement.innerHTML = `
                <div class="flex justify-between items-start">
                    <div class="flex-1 min-w-0">
                        <div class="flex items-center space-x-3">
                            <div class="flex-shrink-0">
                                <div class="h-10 w-10 rounded-full bg-indigo-100 flex items-center justify-center">
                                    <i data-lucide="user" class="w-5 h-5 text-indigo-600"></i>
                                </div>
                            </div>
                            <div class="min-w-0 flex-1">
                                <p class="text-sm font-medium text-gray-900 truncate">${aluno.nome}</p>
                                <p class="text-xs text-gray-500 truncate">${formatarCPF(aluno.cpf)}</p>
                            </div>
                        </div>
                        <div class="mt-2 flex flex-wrap gap-2">
                            <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-indigo-100 text-indigo-800">
                                ${aluno.idade} anos
                            </span>
                            <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                                ${aluno.genero?.toString().toLowerCase() || 'Não informado'}
                            </span>
                        </div>
                    </div>
                    <div class="ml-2 flex-shrink-0 flex">
                        <button type="button" class="text-indigo-600 hover:text-indigo-900 text-sm font-medium" onclick="mostrarDetalhesAluno(${aluno.id})">
                            Ver mais
                        </button>
                    </div>
                </div>
                <div class="mt-2 pt-2 border-t border-gray-100">
                    <div class="flex items-center text-sm text-gray-500">
                        <i data-lucide="phone" class="w-4 h-4 mr-1"></i>
                        <span>${formatarTelefone(aluno.telefone)}</span>
                    </div>
                </div>
            `;
            userListContainer.appendChild(alunoElement);
        });

        lucide.createIcons();
    };

    // Função para buscar alunos da API
    const buscarAlunos = async () => {
        try {
            const response = await fetch('/alunos');
            if (!response.ok) throw new Error('Erro ao buscar alunos');
            const data = await response.json();
            alunos = data.content || [];
            renderAlunos(alunos);
        } catch (error) {
            console.error('Erro ao buscar alunos:', error);
            userListContainer.innerHTML = `
                <div class="text-center py-4 text-red-500">
                    <i data-lucide="alert-circle" class="w-8 h-8 mx-auto mb-2"></i>
                    <p>Erro ao carregar alunos. Tente novamente.</p>
                </div>`;
            lucide.createIcons();
        }
    };

    // Buscar alunos ao carregar a página
    buscarAlunos();

    // Função de busca em tempo real
    searchInput.addEventListener('input', (e) => {
        const searchTerm = e.target.value.toLowerCase();
        const alunosFiltrados = alunos.filter(aluno =>
            aluno.nome?.toLowerCase().includes(searchTerm) ||
            aluno.cpf?.includes(searchTerm.replace(/\D/g, ''))
        );
        renderAlunos(alunosFiltrados);
    });

    // Função para mostrar detalhes do aluno (será implementada posteriormente)
    window.mostrarDetalhesAluno = (id) => {
        console.log('Mostrar detalhes do aluno:', id);
        // Implementar lógica para mostrar detalhes do aluno
        alert(`Detalhes do aluno ID: ${id} serão exibidos aqui.`);
    };

    // --- LÓGICA GERAL E ANIMAÇÕES GSAP ---

    const toTopButton = document.getElementById('to-top-button');
    window.onscroll = () => {
        if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
            toTopButton.classList.remove('hidden');
        } else {
            toTopButton.classList.add('hidden');
        }
    };
    toTopButton.onclick = () => window.scrollTo({ top: 0, behavior: 'smooth' });

    document.querySelectorAll('a.group').forEach(card => {
        const icon = card.querySelector('.card-icon, .background-icon');
        if (icon) {
            gsap.set(icon, { transformOrigin: 'center center' });

            card.addEventListener('mouseenter', () => {
                gsap.to(icon, {
                    duration: 0.4,
                    scale: 1.15,
                    rotation: -8,
                    ease: 'back.out(1.7)'
                });
            });

            card.addEventListener('mouseleave', () => {
                gsap.to(icon, {
                    duration: 0.3,
                    scale: 1,
                    rotation: 0,
                    ease: 'power1.out'
                });
            });
        }

        if (card.classList.contains('stats-card')) {
            const bars = card.querySelectorAll('.bar');
            gsap.set(bars, { scaleY: 0, transformOrigin: 'bottom' });

            card.addEventListener('mouseenter', () => {
                gsap.to(bars, { duration: 0.5, scaleY: 1, stagger: 0.1, ease: 'back.out(1.7)' });
            });

            card.addEventListener('mouseleave', () => {
                gsap.to(bars, { duration: 0.3, scaleY: 0, ease: 'power1.out' });
            });
        }
    });
});