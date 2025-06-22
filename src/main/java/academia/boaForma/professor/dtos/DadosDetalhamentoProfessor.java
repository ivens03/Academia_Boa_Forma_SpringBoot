package academia.boaForma.professor.dtos;

 import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.Genero;

import java.time.LocalDate;

public record DadosDetalhamentoProfessor(
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
        Boolean professorMaster,
        String fotoPerfil) {

    public DadosDetalhamentoProfessor(Professor professor) {
        this(
                professor.getNome(),
                professor.getCpf(),
                professor.getGenero(),
                professor.getEmail(),
                professor.getTelefone(),
                professor.getIdade(),
                professor.getDataNascimento(),
                professor.getCriadoEm(),
                professor.getAtivo(),
                professor.getAcessoSistema(),
                professor.getTelefoneEmergencia(),
                professor.getProfessorMaster(),
                professor.getFotoPerfil()
        );
    }
}
