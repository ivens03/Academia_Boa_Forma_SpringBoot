package academia.boaForma.models.alunos.informacoes;

import academia.boaForma.models.compartilhados.EnderecoModel;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Table(name = "alunos")
public class AlunosModel extends RepresentationModel<AlunosModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_aluno")
    private Integer id_aluno;

    @Column(name = "nome_aluno")
    private String nome_aluno;

    @Column(name = "idade_aluno")
    private Byte idade_aluno;

    @Column(name = "senha_aluno")
    private String senha_aluno;

    @Column(name = "numero_celular_aluno")
    private String numero_celular_aluno;

    @Column(name = "numero_energencia")
    private String numero_energencia;

    @Column(name = "doenca")
    private Boolean doenca;

    @Column(name = "descricao_doenca")
    private String descricao_doenca;

    @Embedded
    private EnderecoModel endereco;

    //CONSTRUTOR
    public AlunosModel() {}

    public AlunosModel(Integer id_aluno, String nome_aluno, Byte idade_aluno, String senha_aluno, String numero_celular_aluno, String numero_energencia, Boolean doenca, String descricao_doenca, EnderecoModel endereco) {
        this.id_aluno = id_aluno;
        this.nome_aluno = nome_aluno;
        this.idade_aluno = idade_aluno;
        this.senha_aluno = senha_aluno;
        this.numero_celular_aluno = numero_celular_aluno;
        this.numero_energencia = numero_energencia;
        this.doenca = doenca;
        this.descricao_doenca = descricao_doenca;
        this.endereco = endereco;
    }

    //GETTERS E SETTERS

    public Integer getIdAluno() {
        return id_aluno;
    }

    public void setIdAluno(Integer id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getNomeAluno() {
        return nome_aluno;
    }

    public void setNomeAluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public Byte getIdadeAluno() {
        return idade_aluno;
    }

    public void setIdadeAluno(Byte idade_aluno) {
        this.idade_aluno = idade_aluno;
    }

    public String getSenhaAluno() {
        return senha_aluno;
    }

    public void setSenhaAluno(String senha_aluno) {
        this.senha_aluno = senha_aluno;
    }

    public EnderecoModel getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoModel endereco) {
        this.endereco = endereco;
    }

    public String getNumeroCelularAluno() {
        return numero_celular_aluno;
    }

    public void setNumeroCelularAluno(String numero_celular_aluno) {
        this.numero_celular_aluno = numero_celular_aluno;
    }

    public String getNumeroEnergencia() {
        return numero_energencia;
    }

    public void setNumeroEnergencia(String numero_energencia) {
        this.numero_energencia = numero_energencia;
    }

    public Boolean getDoenca() {
        return doenca;
    }

    public void setDoenca(Boolean doenca) {
        this.doenca = doenca;
    }

    public String getDescricaoDoenca() {
        return descricao_doenca;
    }

    public void setDescricaoDoenca(String descricao_doenca) {
        this.descricao_doenca = descricao_doenca;
    }
}
