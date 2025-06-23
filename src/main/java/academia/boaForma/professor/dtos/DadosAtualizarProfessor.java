package academia.boaForma.professor.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.usuarios.models.Genero;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarProfessor(
        @NotNull Integer id,
        String senha,
        Boolean acessoSistema,
        String email,
        Genero genero,
        String telefone,
        String telefoneEmergencia,
        Endereco endereco,
        boolean professorMaster
        ) {
}
