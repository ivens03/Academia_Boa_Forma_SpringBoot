package academia.boaForma.alunos.models.informacoes;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alunos")
@DiscriminatorValue("ALUNO")
public class Alunos extends Usuario {

    private static final long serialVersionUID = 1L;

    @Column(length = 11)
    private String telefoneEmergencia;

    @Column(nullable = false)
    private Boolean possuiDoenca;

    @Column
    private String descricaoDoenca;

    @Enumerated(EnumType.STRING)
    private FocoAluno focoAluno;

    @Embedded
    private Endereco endereco;

    @Column
    private LocalDateTime ultimoAcesso;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professorResponsavel;

    public Alunos() {}

    public Alunos(DadosCadastroAluno dadosCadastroAluno) {
        this.id = dadosCadastroAluno.id();
        this.nome = dadosCadastroAluno.nome();
        this.cpf = dadosCadastroAluno.cpf();
        this.email = dadosCadastroAluno.email();
        this.senha = dadosCadastroAluno.senha();
        this.telefone = dadosCadastroAluno.telefone();
        this.criadoEm = dadosCadastroAluno.criadoEm();
        this.ativo = dadosCadastroAluno.ativo();
        this.acessoSistema = dadosCadastroAluno.acessoSistema();
        this.idade = dadosCadastroAluno.idade();
        this.dataNascimento = dadosCadastroAluno.dataNascimento();
        this.telefoneEmergencia = dadosCadastroAluno.telefoneEmergencia();
        this.possuiDoenca = dadosCadastroAluno.possuiDoenca();
        this.descricaoDoenca = dadosCadastroAluno.descricaoDoenca();
        this.focoAluno = dadosCadastroAluno.focoAluno();
        this.endereco = dadosCadastroAluno.endereco();
    }

}