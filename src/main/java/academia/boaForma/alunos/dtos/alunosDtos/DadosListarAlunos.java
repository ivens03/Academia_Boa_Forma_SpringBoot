package academia.boaForma.alunos.dtos.alunosDtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosListarAlunos(
        Integer id,
        Boolean acessoSistema,
        Boolean ativo,
        String cpf,
        String email,
        Genero genero,
        Byte idade,
        String nome,
        String telefone,
        String telefoneEmergencia,
        Boolean possuiDoenca,
        String descricaoDoenca,
        Endereco endereco,
        FocoAluno focoAluno,
        LocalDate criadoEm,
        LocalDateTime ultimoAcesso
    ) {

    public DadosListarAlunos(AlunosModel alunosModel) {
        this(
            alunosModel.getId(),
            alunosModel.getAcessoSistema(),
            alunosModel.getAtivo(),
            alunosModel.getCpf(),
            alunosModel.getEmail(),
            alunosModel.getGenero(),
            alunosModel.getIdade(),
            alunosModel.getNome(),
            alunosModel.getTelefone(),
            alunosModel.getTelefoneEmergencia(),
            alunosModel.getPossuiDoenca(),
            alunosModel.getDescricaoDoenca(),
            alunosModel.getEndereco(),
            alunosModel.getFocoAluno(),
            alunosModel.getCriadoEm(),
            alunosModel.getUltimoAcesso()
        );
    }

}
