package academia.boaForma.professor.models;

import academia.boaForma.professor.dtos.DadosAtualizarProfessor;
import academia.boaForma.professor.dtos.DadosCadastroProfessor;
import academia.boaForma.usuarios.models.Genero;
import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;

import java.time.LocalDate;

@Entity
@Table(name = "professores")
@DiscriminatorValue("PROFESSOR")
public class Professor extends UsuarioModel {

    private static final long serialVersionUID = 1L;

    @Column
    private String fotoPerfil;

    @Column
    private Boolean professorMaster;

    @Column(length = 11)
    private String telefoneEmergencia;

    public Professor() {}

    public Professor(Boolean professorMaster) {
        this.professorMaster = false;
    }

    public Professor(DadosCadastroProfessor dadosCadastroProfessor) {
        this.id = dadosCadastroProfessor.id();
        this.nome = dadosCadastroProfessor.nome();
        this.cpf = dadosCadastroProfessor.cpf();
        this.genero = dadosCadastroProfessor.genero();
        this.email = dadosCadastroProfessor.email();
        this.telefone = dadosCadastroProfessor.telefone();
        this.idade = dadosCadastroProfessor.idade();
        this.dataNascimento = dadosCadastroProfessor.dataNascimento();
        this.criadoEm = dadosCadastroProfessor.criadoEm();
        this.ativo = dadosCadastroProfessor.ativo();
        this.acessoSistema = dadosCadastroProfessor.acessoSistema();
        this.telefoneEmergencia = dadosCadastroProfessor.telefoneEmergencia();
        this.professorMaster = dadosCadastroProfessor.professorMaster();
    }

    public void atualizarInformacoes(@Valid DadosAtualizarProfessor dadosAtualizarProfessor) {
        if (dadosAtualizarProfessor.acessoSistema() != null) { this.acessoSistema = dadosAtualizarProfessor.acessoSistema(); }
        if (dadosAtualizarProfessor.email() != null) { this.email = dadosAtualizarProfessor.email(); }
        if (dadosAtualizarProfessor.genero() != null) { this.genero = dadosAtualizarProfessor.genero(); }
        if (dadosAtualizarProfessor.telefone() != null) { this.telefone = dadosAtualizarProfessor.telefone(); }
        if (dadosAtualizarProfessor.telefoneEmergencia() != null) { this.telefoneEmergencia = dadosAtualizarProfessor.telefoneEmergencia(); }
    }


    // Getters e Setters

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Boolean getProfessorMaster() {
        return professorMaster;
    }

    public void setProfessorMaster(Boolean professorMaster) {
        this.professorMaster = professorMaster;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }

    // Verificar se o professor é master
    public boolean isProfessorMaster() {
        return professorMaster != null && professorMaster;
    }

    // Promove o professor para master
    public void promover() {
        this.professorMaster = true;
    }

}
