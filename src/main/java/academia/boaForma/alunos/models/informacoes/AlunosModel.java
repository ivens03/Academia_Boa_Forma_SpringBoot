package academia.boaForma.alunos.models.informacoes;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alunos")
@DiscriminatorValue("ALUNO")
public class AlunosModel extends UsuarioModel {

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

    public AlunosModel() {}

    public AlunosModel(DadosCadastroAluno dadosCadastroAluno) {
        this.id = dadosCadastroAluno.id();
        this.nome = dadosCadastroAluno.nome();
        this.cpf = dadosCadastroAluno.cpf();
        this.email = dadosCadastroAluno.email();
        this.genero = dadosCadastroAluno.genero();
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

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }

    public Boolean getPossuiDoenca() {
        return possuiDoenca;
    }

    public void setPossuiDoenca(Boolean possuiDoenca) {
        this.possuiDoenca = possuiDoenca;
    }

    public String getDescricaoDoenca() {
        return descricaoDoenca;
    }

    public void setDescricaoDoenca(String descricaoDoenca) {
        this.descricaoDoenca = descricaoDoenca;
    }

    public FocoAluno getFocoAluno() {
        return focoAluno;
    }

    public void setFocoAluno(FocoAluno focoAluno) {
        this.focoAluno = focoAluno;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public Professor getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(Professor professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }
}