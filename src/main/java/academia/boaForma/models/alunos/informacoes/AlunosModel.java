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

    @Column(name = "numeroCelularAluno")
    private String numeroCelularAluno;

    @Column(name = "numeroEnergencia")
    private String numeroEnergencia;

    @Column(name = "doenca")
    private Boolean doenca;

    @Column(name = "descricaoDoenca")
    private String descricaoDoenca;

    @Embedded
    private Endereco endereco;

    //CONSTRUTOR
    public AlunosModel() {}

    public AlunosModel(Integer idAluno, String nomeAluno, Byte idadeAluno, String senhaAluno, String numeroCelularAluno, String numeroEnergencia, Boolean doenca, String descricaoDoenca, Endereco endereco) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.idadeAluno = idadeAluno;
        this.senhaAluno = senhaAluno;
        this.numeroCelularAluno = numeroCelularAluno;
        this.numeroEnergencia = numeroEnergencia;
        this.doenca = doenca;
        this.descricaoDoenca = descricaoDoenca;
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

    public String getNumeroCelularAluno() {
        return numeroCelularAluno;
    }

    public void setNumeroCelularAluno(String numeroCelularAluno) {
        this.numeroCelularAluno = numeroCelularAluno;
    }

    public String getNumeroEnergencia() {
        return numeroEnergencia;
    }

    public void setNumeroEnergencia(String numeroEnergencia) {
        this.numeroEnergencia = numeroEnergencia;
    }

    public Boolean getDoenca() {
        return doenca;
    }

    public void setDoenca(Boolean doenca) {
        this.doenca = doenca;
    }

    public String getDescricaoDoenca() {
        return descricaoDoenca;
    }

    public void setDescricaoDoenca(String descricaoDoenca) {
        this.descricaoDoenca = descricaoDoenca;
    }
}
