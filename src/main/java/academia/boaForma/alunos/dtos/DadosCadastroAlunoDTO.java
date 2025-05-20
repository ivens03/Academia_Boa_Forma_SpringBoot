package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.informacoes.FocoAlunoEnum;
import java.time.LocalDateTime;

public record DadosCadastroAlunoDTO(
        Integer id_aluno,
        String nome_aluno,
        Byte idade_aluno,
        String cpf,
        String senha_aluno,
        String numero_celular_aluno,
        String numero_energencia,
        Boolean doenca,
        String descricao_doenca,
        FocoAlunoEnum foco_aluno,
        DadosEnderecoDTO endereco,
        LocalDateTime ultimo_acesso
    ) {

    public String getNome_aluno() {
        return nome_aluno;
    }

}