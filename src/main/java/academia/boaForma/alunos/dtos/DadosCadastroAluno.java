package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.professor.models.Professor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosCadastroAluno(
        Integer id,
        String nome,
        String cpf,
        String email,
        String senha,
        String telefone,
        LocalDate criadoEm,
        Boolean ativo,
        Boolean acessoSistema,
        Byte idade,
        String telefoneEmergencia,
        Boolean possuiDoenca,
        String descricaoDoenca,
        FocoAluno focoAluno,
        Endereco endereco,
        LocalDateTime ultimoAcesso,
        Professor professorResponsavel
    ) {

/*    public String getNome_aluno() {
        return nome;
    }*/

}