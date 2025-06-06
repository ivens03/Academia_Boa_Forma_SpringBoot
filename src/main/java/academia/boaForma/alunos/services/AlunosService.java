package academia.boaForma.alunos.services;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.exception.CampoDuplicadoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunosService {

    private final AlunosRepositorie alunosRepositorie;

    public AlunosService(AlunosRepositorie alunosRepositorie) {
        this.alunosRepositorie = alunosRepositorie;
    }

    public void cadastrarAluno(DadosCadastroAluno dadosCadastroAluno) {
        List<String> camposDuplicados = new ArrayList<>();

        if (alunosRepositorie.existsByCpf(dadosCadastroAluno.cpf())) {
            camposDuplicados.add("CPF");
        }
        if (alunosRepositorie.existsByEmail(dadosCadastroAluno.email())){
            camposDuplicados.add("Email");
        }
        if (alunosRepositorie.existsByNome(dadosCadastroAluno.nome())) {
            camposDuplicados.add("Nome");
        }
        if (alunosRepositorie.existsByTelefone(dadosCadastroAluno.telefone())) {
            camposDuplicados.add("Telefone");
        }
        if (alunosRepositorie.existsByTelefoneEmergencia(dadosCadastroAluno.telefoneEmergencia())) {
            camposDuplicados.add("Telefone Emergência");
        }
        if (!camposDuplicados.isEmpty()) {
            throw new CampoDuplicadoException(camposDuplicados);
        }
    }
}
