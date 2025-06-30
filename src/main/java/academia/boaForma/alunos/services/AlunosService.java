package academia.boaForma.alunos.services;

import academia.boaForma.alunos.dtos.alunosDtos.DadosCadastroAluno;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.professor.repositories.ProfessorRepositorie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AlunosService {

    private final AlunosRepositorie alunosRepositorie;
    private final ProfessorRepositorie professorRepositorie;
    private final PasswordEncoder passwordEncoder;

    public AlunosService(AlunosRepositorie alunosRepositorie, ProfessorRepositorie professorRepositorie, PasswordEncoder passwordEncoder) {
        this.alunosRepositorie = alunosRepositorie;
        this.professorRepositorie = professorRepositorie;
        this.passwordEncoder = passwordEncoder;
    }

    public AlunosModel cadastrar(DadosCadastroAluno dados) {
        // 1. Buscamos o professor (lógica que estava no controller)
        var professor = professorRepositorie.professorById(dados.professorResponsavelId());

        // 2. Criamos o novo aluno (lógica que estava no controller)
        var aluno = new AlunosModel(dados);

        // 3. CRIPTOGRAFAMOS A SENHA ANTES DE QUALQUER COISA
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        aluno.setSenha(senhaCriptografada);

        // 4. Associamos o professor (lógica que estava no controller)
        aluno.setProfessorResponsavelId(professor);

        // 5. Salvamos o aluno no banco de dados
        return alunosRepositorie.save(aluno);
    }
}
