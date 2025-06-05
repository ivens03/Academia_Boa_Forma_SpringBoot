package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.Genero;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizar(
        @NotNull Integer id,
        String senha,
        Boolean acessoSistema,
        String email,
        Genero genero,
        String telefone,
        String telefoneEmergencia,
        Boolean possuiDoenca,
        String descricaoDoenca,
        Endereco endereco,
        FocoAluno focoAluno,
        Professor professorResponsavelID
) {
}
