/* src/main/resources/static/js/registroAluno.js */

// Função para buscar endereço via CEP (usando a API ViaCEP)
// Deixamos esta função no escopo global para que o Alpine.js possa chamá-la.
function fetchAddress() {
    const cepInput = document.getElementById('cep');
    const cep = cepInput.value.replace(/\D/g, ''); // Remove caracteres não numéricos
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
                alert('CEP não encontrado.');
            }
        })
        .catch(error => console.error('Erro ao buscar CEP:', error));
}


document.addEventListener('DOMContentLoaded', () => {
    // Inicializa os ícones do Lucide
    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }

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
    const form = document.querySelector('form');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

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
            possuiDoenca: document.querySelector('input[x-model=\"hasDisease\"]:checked')?.value === 'true',
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
                alert('Aluno cadastrado com sucesso!');
                window.location.href = '/homeGestor'; // ou outra página de sucesso
            } else {
                const errorData = await response.json();
                alert(`Erro: ${errorData.message || 'Não foi possível cadastrar o aluno.'}`);
            }
        } catch (error) {
            console.error('Erro na submissão:', error);
            alert('Ocorreu um erro de rede. Tente novamente.');
        }
    });
});