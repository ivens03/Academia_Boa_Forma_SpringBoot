package academia.boaForma.models.alunos.informacoes;

import academia.boaForma.models.compartilhados.Endereco;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "alunos")
public class AlunosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAluno")
    private Integer idAluno;

    @Column(name = "nomeAluno")
    private String nomeAluno;

    @Column(name = "idadeAluno")
    private Byte idadeAluno;

    @Column(name = "senhaAluno")
    private String senhaAluno;

    @Embedded
    private Endereco endereco;

    //CONSTRUTOR
    public AlunosModel() {}

    public AlunosModel(Integer idAluno, String nomeAluno, Byte idadeAluno, String senhaAluno, Endereco endereco) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.idadeAluno = idadeAluno;
        this.senhaAluno = senhaAluno;
        this.endereco = endereco;
    }

    //GETTERS E SETTERS

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Byte getIdadeAluno() {
        return idadeAluno;
    }

    public void setIdadeAluno(Byte idadeAluno) {
        this.idadeAluno = idadeAluno;
    }

    public String getSenhaAluno() {
        return senhaAluno;
    }

    public void setSenhaAluno(String senhaAluno) {
        this.senhaAluno = senhaAluno;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
