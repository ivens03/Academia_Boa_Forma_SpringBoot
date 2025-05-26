package academia.boaForma.alunos.services;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.models.informacoes.Alunos;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlunoService {

    private final AlunosRepositorie alunosRepositorie;

    public AlunoService(AlunosRepositorie alunosRepositorie) {
        this.alunosRepositorie = alunosRepositorie;
    }

    private static final Logger logger = LoggerFactory.getLogger(AlunoService.class);

    public boolean existeAlunoComNome(String nome) {
        return alunosRepositorie.existsByNome_aluno(nome);
    }

    public void cadastrarAluno(DadosCadastroAluno dadosCadastroAluno) {
        try {
            // Validação de campos obrigatórios
            if (dadosCadastroAluno.nome() == null || dadosCadastroAluno.nome().trim().isEmpty()) {
                throw new ValidationException("Nome do aluno é obrigatório");
            }

            // Validação de duplicidade
            if (existeAlunoComNome(dadosCadastroAluno.nome())) {
                throw new ValidationException("Já existe um aluno com esse nome");
            }

            // Salvar aluno no banco de dados
            Alunos novoAluno = new Alunos(dadosCadastroAluno);
            alunosRepositorie.save(novoAluno);

            logger.info("Aluno cadastrado com sucesso: {}", dadosCadastroAluno.nome());

        } catch (ValidationException e) {
            logger.warn("Erro de validação ao cadastrar aluno: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao cadastrar aluno", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar aluno: " + e.getMessage());
        }
    }
}




