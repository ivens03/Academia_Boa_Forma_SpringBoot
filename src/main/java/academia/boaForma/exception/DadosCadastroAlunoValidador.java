package academia.boaForma.exception;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.validation.ValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class DadosCadastroAlunoValidador extends ResponseEntityExceptionHandler {

    AlunosRepositorie alunosRepositorie;

    public void nameNotNull(DadosCadastroAluno dadosCadastroAluno) {
        if (dadosCadastroAluno.nome() == null) {
            throw new ValidationException("Nome do aluno é obrigatório");
        }
    }


}
