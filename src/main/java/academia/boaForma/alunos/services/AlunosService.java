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
        var professor = professorRepositorie.professorById(dados.professorResponsavelId());
        var aluno = new AlunosModel(dados);
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        aluno.setSenha(senhaCriptografada);
        aluno.setProfessorResponsavelId(professor);
        return alunosRepositorie.save(aluno);
    }
}
