package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.informacoes.Alunos;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosListarAlunos(
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

    public DadosListarAlunos(Alunos alunos) {
        this(
            alunos.getAcessoSistema(),
            alunos.getAtivo(),
            alunos.getCpf(),
            alunos.getEmail(),
            alunos.getGenero(),
            alunos.getIdade(),
            alunos.getNome(),
            alunos.getTelefone(),
            alunos.getTelefoneEmergencia(),
            alunos.getPossuiDoenca(),
            alunos.getDescricaoDoenca(),
            alunos.getEndereco(),
            alunos.getFocoAluno(),
            alunos.getCriadoEm(),
            alunos.getUltimoAcesso()
        );
    }

}
