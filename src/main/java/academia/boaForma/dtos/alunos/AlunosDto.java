package academia.boaForma.dtos.alunos;

import academia.boaForma.models.compartilhados.EnderecoModel;

public record AlunosDto(
        Integer id_aluno,
        String nome_aluno,
        Byte idade_aluno,
        String senha_aluno,
        String numero_celular_aluno,
        String numero_energencia,
        Boolean doenca,
        String descricao_doenca,
        EnderecoModel endereco
) {

    public AlunosDto {
        if (doenca != null && !doenca) {
            descricao_doenca = "Não possui problemas medicos";
        }
    }

}