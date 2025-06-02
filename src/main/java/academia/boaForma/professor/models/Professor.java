package academia.boaForma.professor.models;

import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "professores")
@DiscriminatorValue("PROFESSOR")
public class Professor extends UsuarioModel {

    private static final long serialVersionUID = 1L;

    @Column
    private String fotoPerfil;

    @Column(nullable = false)
    private Boolean professorMaster;

    public Professor() {}

    public Professor(Boolean professorMaster) {
        this.professorMaster = false;
    }

    public Professor(String fotoPerfil, Boolean professorMaster) {
        this.fotoPerfil = fotoPerfil;
        this.professorMaster = professorMaster;
    }

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


    // Verificar se é melhor em outro arquivo
    // Verificar se o professor é master
    public boolean isProfessorMaster() {
        return professorMaster != null && professorMaster;
    }

    // Promove o professor para master
    public void promover() {
        this.professorMaster = true;
    }
}
