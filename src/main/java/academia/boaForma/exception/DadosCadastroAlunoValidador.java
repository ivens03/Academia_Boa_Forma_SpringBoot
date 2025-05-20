package academia.boaForma.exception;

import academia.boaForma.alunos.dtos.DadosCadastroAlunoDTO;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.validation.ValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class DadosCadastroAlunoValidador extends ResponseEntityExceptionHandler {

    AlunosRepositorie alunosRepositorie;

    public void nameNotNull(DadosCadastroAlunoDTO dadosCadastroAlunoDTO) {
        if (dadosCadastroAlunoDTO.nome_aluno() == null) {
            throw new ValidationException("Nome do aluno é obrigatório");
        }
    }


}
