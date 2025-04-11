package academia.boaForma.dtos.alunos;

import academia.boaForma.models.compartilhados.Endereco;

// Não foi colocado o idAluno pois ele foi criado automaticamente e a senha ainda pois não tem segurançã ainda.
public record AlunosDto(
        String nomeAluno,
        Byte idadeAluno,
        String senhaAluno,
        String numeroCelularAluno,
        String numeroEnergencia,
        Boolean doenca,
        String descricaoDoenca,
        Endereco endereco) {
}
