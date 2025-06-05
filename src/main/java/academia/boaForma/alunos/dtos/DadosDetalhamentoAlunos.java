package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosDetalhamentoAlunos(
        String nome,
        String cpf,
        Genero genero,
        String email,
        String telefone,
        Byte idade,
        LocalDate dataNascimento,
        LocalDate criadoEm,
        Boolean ativo,
        Boolean acessoSistema,
        String telefoneEmergencia,
        Boolean possuiDoenca,
        String descricaoDoenca,
        FocoAluno focoAluno,
        Endereco endereco,
        LocalDateTime ultimoAcesso) {

    public DadosDetalhamentoAlunos(AlunosModel alunosModel) {
        this(
                alunosModel.getNome(),
                alunosModel.getCpf(),
                alunosModel.getGenero(),
                alunosModel.getEmail(),
                alunosModel.getTelefone(),
                alunosModel.getIdade(),
                alunosModel.getDataNascimento(),
                alunosModel.getCriadoEm(),
                alunosModel.getAtivo(),
                alunosModel.getAcessoSistema(),
                alunosModel.getTelefoneEmergencia(),
                alunosModel.getPossuiDoenca(),
                alunosModel.getDescricaoDoenca(),
                alunosModel.getFocoAluno(),
                alunosModel.getEndereco(),
                alunosModel.getUltimoAcesso()
        );
    }
}
