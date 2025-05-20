package academia.boaForma.alunos.models.informacoes;

import academia.boaForma.alunos.dtos.DadosCadastroAlunoDTO;
import academia.boaForma.alunos.models.endereco.EnderecoModel;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.usuarios.models.UsuariosModel;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alunos")
public class AlunosModel extends RepresentationModel<AlunosModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id_aluno;

    @Column(nullable = false, unique = true)
    private String nome_aluno;

    @Column(nullable = false)
    private Byte idade_aluno;

    @Column(length = 10)
    private String cpf;

    @Column(nullable = false)
    private String senha_aluno;

    @Column(length = 11)
    private String numero_celular_aluno;

    @Column(length = 11)
    private String numero_energencia;

    @Column(nullable = false)
    private Boolean doenca;

    @Column
    private String descricao_doenca;

    @Enumerated(EnumType.STRING)
    private FocoAlunoEnum foco_aluno;

    @Embedded
    private EnderecoModel endereco;

    @Column
    private LocalDateTime ultimo_acesso;

    // RELACIONAMENTOS UsuariosModel

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuariosModel id_usuario;

    @ManyToOne
    @JoinColumn(name = "acesso_sistema")
    private UsuariosModel acesso_sistema;

    // Para conexão com a tabela alunos_pagamentos

    @OneToMany(mappedBy = "id_aluno", cascade = CascadeType.ALL)
    private List<PagamentosAlunosModel> aluno_id;

    //CONSTRUTOR

    public AlunosModel() {}

    public AlunosModel(DadosCadastroAlunoDTO dadosCadastroAlunoDTO) {
        this.id_aluno = dadosCadastroAlunoDTO.id_aluno();
        this.nome_aluno = dadosCadastroAlunoDTO.nome_aluno();
        this.idade_aluno = dadosCadastroAlunoDTO.idade_aluno();
        this.cpf = dadosCadastroAlunoDTO.cpf();
        this.senha_aluno = dadosCadastroAlunoDTO.senha_aluno();
        this.numero_celular_aluno = dadosCadastroAlunoDTO.numero_celular_aluno();
        this.numero_energencia = dadosCadastroAlunoDTO.numero_energencia();
        this.doenca = dadosCadastroAlunoDTO.doenca();
        this.descricao_doenca = dadosCadastroAlunoDTO.descricao_doenca();
        this.foco_aluno = dadosCadastroAlunoDTO.foco_aluno();
        this.endereco = new EnderecoModel(dadosCadastroAlunoDTO.endereco());
    }


    //GETTERS E SETTERS

    public Integer getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(Integer id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public Byte getIdade_aluno() {
        return idade_aluno;
    }

    public void setIdade_aluno(Byte idade_aluno) {
        this.idade_aluno = idade_aluno;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha_aluno() {
        return senha_aluno;
    }

    public void setSenha_aluno(String senha_aluno) {
        this.senha_aluno = senha_aluno;
    }

    public String getNumero_celular_aluno() {
        return numero_celular_aluno;
    }

    public void setNumero_celular_aluno(String numero_celular_aluno) {
        this.numero_celular_aluno = numero_celular_aluno;
    }

    public String getNumero_energencia() {
        return numero_energencia;
    }

    public void setNumero_energencia(String numero_energencia) {
        this.numero_energencia = numero_energencia;
    }

    public Boolean getDoenca() {
        return doenca;
    }

    public void setDoenca(Boolean doenca) {
        this.doenca = doenca;
    }

    public String getDescricao_doenca() {
        return descricao_doenca;
    }

    public void setDescricao_doenca(String descricao_doenca) {
        this.descricao_doenca = descricao_doenca;
    }

    public FocoAlunoEnum getFoco_aluno() {
        return foco_aluno;
    }

    public void setFoco_aluno(FocoAlunoEnum foco_aluno) {
        this.foco_aluno = foco_aluno;
    }

    public EnderecoModel getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoModel endereco) {
        this.endereco = endereco;
    }

    public LocalDateTime getUltimo_acesso() {
        return ultimo_acesso;
    }

    public void setUltimo_acesso(LocalDateTime ultimo_acesso) {
        this.ultimo_acesso = ultimo_acesso;
    }

    public UsuariosModel getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(UsuariosModel id_usuario) {
        this.id_usuario = id_usuario;
    }

    public UsuariosModel getAcesso_sistema() {
        return acesso_sistema;
    }

    public void setAcesso_sistema(UsuariosModel acesso_sistema) {
        this.acesso_sistema = acesso_sistema;
    }

    public List<PagamentosAlunosModel> getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(List<PagamentosAlunosModel> aluno_id) {
        this.aluno_id = aluno_id;
    }
}
