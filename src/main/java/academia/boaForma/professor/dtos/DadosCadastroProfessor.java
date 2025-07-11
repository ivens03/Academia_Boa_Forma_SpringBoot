package academia.boaForma.professor.dtos;

import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosCadastroProfessor(
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
        Endereco endereco,
        LocalDateTime ultimoAcesso,
        Boolean professorMaster
) { }
