package academia.boaForma.gestor.dtos;

import academia.boaForma.gestor.models.GestorModel;
import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;

public record DadosDetalhamentoGestor(
        String nome,
        String cpf,
        Genero genero,
        String email,
        String telefone,
        Byte idade,
        LocalDate dataNascimento,
        LocalDate criadoEm,
        Boolean ativo,
        Boolean acessoSistema) {

    public DadosDetalhamentoGestor(GestorModel gestor) {
        this(
                gestor.getNome(),
                gestor.getCpf(),
                gestor.getGenero(),
                gestor.getEmail(),
                gestor.getTelefone(),
                gestor.getIdade(),
                gestor.getDataNascimento(),
                gestor.getCriadoEm(),
                gestor.getAtivo(),
                gestor.getAcessoSistema());
    }
}
