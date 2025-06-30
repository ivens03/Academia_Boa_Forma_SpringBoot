package academia.boaForma.alunos.models.informacoes;

import academia.boaForma.alunos.dtos.alunosDtos.DadosAtualizar;
import academia.boaForma.alunos.dtos.alunosDtos.DadosCadastroAluno;
import academia.boaForma.alunos.models.endereco.Endereco;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.StatusValidacaoTelefone;
import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
@DiscriminatorValue("ALUNO")
public class AlunosModel extends UsuarioModel {

    private static final long serialVersionUID = 1L;

    @Column(length = 11)
    private String telefoneEmergencia;

    @JoinColumn
    @Enumerated(EnumType.STRING)
    protected StatusValidacaoTelefone statusValidacaoTelefoneEmergencia;

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
    private Professor professorResponsavelId;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PagamentosAlunosModel> pagamentos = new ArrayList<>();

    public AlunosModel() {
        this.statusValidacaoTelefoneEmergencia = statusValidacaoTelefoneEmergencia.PENDENTE;
    }

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

    public void atualizarInformacoes(@Valid DadosAtualizar dadosAtualizar) {
        if (dadosAtualizar.acessoSistema() != null) { this.acessoSistema = dadosAtualizar.acessoSistema(); }
        if (dadosAtualizar.email() != null) { this.email = dadosAtualizar.email(); }
        if (dadosAtualizar.genero() != null) { this.genero = dadosAtualizar.genero(); }
        if (dadosAtualizar.telefone() != null) { this.telefone = dadosAtualizar.telefone(); }
        if (dadosAtualizar.telefoneEmergencia() != null) { this.telefoneEmergencia = dadosAtualizar.telefoneEmergencia(); }
        if (dadosAtualizar.possuiDoenca() != null) { this.possuiDoenca = dadosAtualizar.possuiDoenca(); }
        if (dadosAtualizar.descricaoDoenca() != null) { this.descricaoDoenca = dadosAtualizar.descricaoDoenca(); }
        if (dadosAtualizar.endereco() != null) { this.endereco = dadosAtualizar.endereco(); }
        if (dadosAtualizar.focoAluno() != null) { this.focoAluno = dadosAtualizar.focoAluno(); }
        if (dadosAtualizar.professorResponsavelID() != null) { this.professorResponsavelId = dadosAtualizar.professorResponsavelID(); }
        if (dadosAtualizar.senha() != null) { this.senha = dadosAtualizar.senha(); }
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

    public Professor getProfessorResponsavelId() {
        return professorResponsavelId;
    }

    public void setProfessorResponsavelId(Professor professorResponsavelId) {
        this.professorResponsavelId = professorResponsavelId;
    }
}