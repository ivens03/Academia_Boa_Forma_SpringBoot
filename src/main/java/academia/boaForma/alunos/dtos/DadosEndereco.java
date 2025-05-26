package academia.boaForma.alunos.dtos;

public record DadosEndereco(
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cep
) {}
