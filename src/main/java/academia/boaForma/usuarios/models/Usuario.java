package academia.boaForma.usuarios.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected String nome;

    @Column(length = 11)
    protected String cpf;

    @Column( unique = true)
    protected String email;

    @Column(nullable = false)
    protected String senha;

    @Column(unique = true, length = 11)
    protected String telefone;

    @Column(nullable = false)
    protected LocalDate criadoEm;

    @Column(nullable = false)
    protected Boolean ativo;

    @Column(nullable = false)
    protected Boolean acessoSistema;

    public Usuario() {
        this.criadoEm = LocalDate.now();
        this.ativo = true;
    }

    public Usuario(String nome, String cpf, String email, String senha, String telefone, Boolean acessoSistema) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.acessoSistema = acessoSistema;
    }

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getAcessoSistema() {
        return acessoSistema;
    }

    public void setAcessoSistema(Boolean acessoSistema) {
        this.acessoSistema = acessoSistema;
    }

    //Verificar se é melhor em outro arquivo
    // Ativar o usuario
    public void ativar() {
        this.ativo = true;
    }

    // Desativar o usuario
    public void desativar() {
        this.ativo = false;
    }

    //Conceder o acesso ao sistema
    public void concederAcessoSistema() {
        this.acessoSistema = true;
    }

    //Tirar o acesso ao sistema
    public void retirarAcessoSistema() {
        this.acessoSistema = false;
    }
}
