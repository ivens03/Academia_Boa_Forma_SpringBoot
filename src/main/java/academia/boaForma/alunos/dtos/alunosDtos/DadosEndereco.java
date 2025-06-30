package academia.boaForma.alunos.dtos.alunosDtos;

public record DadosEndereco(
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cep
) {}
