package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.informacoes.FocoAluno;

import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosCadastroAluno(
        Integer id,
        String nome,
        String cpf,
        String email,
        Genero genero,
        String senha,
        String telefone,
        LocalDate criadoEm,
        Boolean ativo,
        Boolean acessoSistema,
        Byte idade,
        LocalDate dataNascimento,
        String telefoneEmergencia,
        Boolean possuiDoenca,
        String descricaoDoenca,
        FocoAluno focoAluno,
        Endereco endereco,
        LocalDateTime ultimoAcesso,
        Integer professorResponsavelId
    ) {
}