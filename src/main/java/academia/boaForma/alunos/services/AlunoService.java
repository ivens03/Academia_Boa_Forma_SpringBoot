package academia.boaForma.alunos.services;

import academia.boaForma.alunos.dtos.DadosCadastroAlunoDTO;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlunoService {

    @Autowired
    AlunosRepositorie alunosRepositorie;

    private static final Logger logger = LoggerFactory.getLogger(AlunoService.class);

    public boolean existeAlunoComNome(String nome_aluno) {
        return alunosRepositorie.existsByNome_aluno(nome_aluno);
    }

    public void cadastrarAluno(DadosCadastroAlunoDTO dadosCadastroAlunoDTO) {
        try {
            // Validação de campos obrigatórios
            if (dadosCadastroAlunoDTO.nome_aluno() == null || dadosCadastroAlunoDTO.nome_aluno().trim().isEmpty()) {
                throw new ValidationException("Nome do aluno é obrigatório");
            }

            // Validação de duplicidade
            if (existeAlunoComNome(dadosCadastroAlunoDTO.nome_aluno())) {
                throw new ValidationException("Já existe um aluno com esse nome");
            }

            // Salvar aluno no banco de dados
            AlunosModel novoAluno = new AlunosModel(dadosCadastroAlunoDTO);
            alunosRepositorie.save(novoAluno);

            logger.info("Aluno cadastrado com sucesso: {}", dadosCadastroAlunoDTO.nome_aluno());

        } catch (ValidationException e) {
            logger.warn("Erro de validação ao cadastrar aluno: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao cadastrar aluno", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar aluno: " + e.getMessage());
        }
    }
}




