package academia.boaForma.usuarios.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario")
public class UsuarioModel implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, unique = true)
    protected String nome;

    @Column(length = 11, unique = true)
    protected String cpf;

    @Enumerated(EnumType.STRING)
    protected Genero genero;

    @Column(unique = true)
    protected String email;

    @Column
    protected String senha;

    @Column(unique = true, length = 11)
    protected String telefone;

    @Enumerated(EnumType.STRING)
    protected StatusValidacaoTelefone statusValidacaoTelefone;

    @Column
    protected Byte idade;

    @Column
    protected LocalDate dataNascimento;

    @Column
    protected LocalDate criadoEm;

    @Column
    protected Boolean ativo;

    @Column
    protected Boolean acessoSistema;

    @Column(name = "tipo_usuario", insertable = false, updatable = false)
    protected String tipoUsuario;

    public UsuarioModel() { }

    //Registrar a data quando foi criado
    @PrePersist
    public void registrarDataCriacao() {
        this.criadoEm = LocalDate.now();
        this.ativo = true;
        this.acessoSistema = true;
    }

    public UsuarioModel(String nome, String cpf, String email, String senha, String telefone, Byte idade, LocalDate dataNascimento, Boolean acessoSistema, Boolean ativo, Genero genero, StatusValidacaoTelefone statusValidacaoTelefone) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.idade = idade;
        this.dataNascimento = dataNascimento;
        this.acessoSistema = acessoSistema;
        this.ativo = ativo;
        this.genero = genero;
        this.statusValidacaoTelefone = statusValidacaoTelefone;
    }

    // Rotas

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.tipoUsuario));
    }


    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //Verificar Metados

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.acessoSistema;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //-------------------------------------------------

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

    public Byte getIdade() {
        return idade;
    }

    public void setIdade(Byte idade) {
        this.idade = idade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setAcessoSistema(Boolean acessoSistema) {
        this.acessoSistema = acessoSistema;
    }

    public StatusValidacaoTelefone getStatusValidacaoTelefone() {
        return statusValidacaoTelefone;
    }

    public void setStatusValidacaoTelefone(StatusValidacaoTelefone statusValidacaoTelefone) {
        this.statusValidacaoTelefone = statusValidacaoTelefone;
    }

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

    //Todos os numeros no começo com o status de PENDENTE
    public void pendenciaTelefoneUsuario() { this.statusValidacaoTelefone = StatusValidacaoTelefone.PENDENTE; }

    public void usuarioDesativado() { this.ativo = false; }

    public void findAllAcessoSistema() { this.acessoSistema = false; }
}
