/* src/main/resources/static/js/registroProfessor.js */

document.addEventListener('DOMContentLoaded', () => {
    // Inicializa os ícones do Lucide
    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }

    // Carrega os gêneros do backend
    const genderSelect = document.getElementById('gender');
    fetch('/alunos/generos')
        .then(response => response.json())
        .then(generos => {
            generos.forEach(genero => {
                const option = document.createElement('option');
                option.value = genero;
                option.textContent = genero.replace('_', ' ').toLowerCase();
                genderSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar gêneros:', error);
            // Se falhar, mantém as opções padrão
            const defaultOptions = ['MASCULINO', 'FEMININO', 'OUTROS', 'NAO_INDENTIFICADO'];
            defaultOptions.forEach(genero => {
                const option = document.createElement('option');
                option.value = genero;
                option.textContent = genero.replace('_', ' ').toLowerCase();
                genderSelect.appendChild(option);
            });
        });

    // Lógica para calcular a idade a partir da data de nascimento
    const birthDateInput = document.getElementById('birth_date');
    const ageInput = document.getElementById('age');

    birthDateInput.addEventListener('change', () => {
        if (birthDateInput.value) {
            const birthDate = new Date(birthDateInput.value);
            const today = new Date();
            let age = today.getFullYear() - birthDate.getFullYear();
            const m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            ageInput.value = age >= 0 ? age : '';
        } else {
            ageInput.value = '';
        }
    });

    // Função para aplicar máscara de CPF
    const applyCpfMask = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        e.target.value = value.slice(0, 14); // Limita ao formato 000.000.000-00
    };

    // Aplica a máscara de CPF
    document.getElementById('cpf').addEventListener('input', applyCpfMask);

    // Função para aplicar máscara de telefone
    const applyPhoneMask = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/^(\d{2})(\d)/g, '($1) $2');
        value = value.replace(/(\d{5})(\d)/, '$1-$2');
        e.target.value = value.slice(0, 15); // Limita ao formato (XX) XXXXX-XXXX
    };

    document.getElementById('phone').addEventListener('input', applyPhoneMask);
    document.getElementById('emergency_phone').addEventListener('input', applyPhoneMask);

    // Função para aplicar máscara de CEP e buscar endereço
    const cepInput = document.getElementById('cep');
    cepInput.addEventListener('input', (e) => {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/^(\d{5})(\d)/, '$1-$2');
        e.target.value = value.slice(0, 9);

        if (e.target.value.length === 9) {
            fetchAddress(e.target.value);
        }
    });

    // Função para buscar endereço via CEP (usando a API ViaCEP)
    const fetchAddress = (cep) => {
        const cleanCep = cep.replace(/\D/g, '');
        if (cleanCep.length !== 8) return;

        fetch(`https://viacep.com.br/ws/${cleanCep}/json/`)
            .then(response => response.json())
            .then(data => {
                if (!data.erro) {
                    document.getElementById('logradouro').value = data.logradouro || '';
                    document.getElementById('bairro').value = data.bairro || '';
                } else {
                    showModal('Erro', 'CEP não encontrado.', 'error');
                }
            })
            .catch(error => {
                console.error('Erro ao buscar CEP:', error);
                showModal('Erro de Rede', 'Não foi possível buscar o CEP. Verifique sua conexão.', 'error');
            });
    }

    // Lógica de submissão do formulário
    const form = document.getElementById('teacher-form');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Validação customizada
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
            idade: parseInt(document.getElementById('age').value) || 0,
            genero: document.getElementById('gender').value,
            professorMaster: document.querySelector('input[name="is_master"]:checked').value === 'true',
            cep: unmask(document.getElementById('cep').value),
            logradouro: document.getElementById('logradouro').value,
            numero: document.getElementById('numero').value,
            complemento: document.getElementById('complemento').value,
            bairro: document.getElementById('bairro').value
        };

        try {
            const response = await fetch('/professor', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                const data = await response.json();
                showModal('Sucesso!', 'Professor cadastrado com sucesso!', 'success');
                // Redireciona para a página de gestor após 2 segundos
                setTimeout(() => {
                    window.location.href = '/homeGestor';
                }, 2000);
            } else {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao cadastrar professor');
            }

        } catch (error) {
            console.error('Erro ao cadastrar professor:', error);
            showModal('Erro', 'Não foi possível cadastrar o professor. Tente novamente mais tarde.', 'error');
        }
    });

    // Funções do Modal
    const modal = document.getElementById('feedback-modal');
    const modalCloseBtn = document.getElementById('modal-close');

    const icons = {
        success: `<i data-lucide="check-circle" class="w-10 h-10 text-green-500"></i>`,
        error: `<i data-lucide="x-circle" class="w-10 h-10 text-red-500"></i>`,
        warning: `<i data-lucide="alert-triangle" class="w-10 h-10 text-yellow-500"></i>`
    };
    const colors = {
        success: 'bg-green-100',
        error: 'bg-red-100',
        warning: 'bg-yellow-100'
    }

    function showModal(title, message, type = 'success') {
        document.getElementById('modal-title').innerText = title;
        document.getElementById('modal-message').innerText = message;
        const iconContainer = document.getElementById('modal-icon');
        iconContainer.innerHTML = icons[type];
        iconContainer.className = `mx-auto flex items-center justify-center h-12 w-12 rounded-full ${colors[type]}`;
        lucide.createIcons(); // Recria o ícone no modal
        modal.style.display = 'block';
    }

    modalCloseBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });
    window.addEventListener('click', (event) => {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });
});