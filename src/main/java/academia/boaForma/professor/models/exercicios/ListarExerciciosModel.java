package academia.boaForma.professor.models.exercicios;

import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.*;

import java.io.Serializable;

/*
*   Tabela responsavel pelos os nomes dos exercicios dentro da academia
*   Somente @Param Professor Master pode incluir ou remover exercicios
*/

@Entity
@Table(name = "lista_de_exercicios")
public class ListarExerciciosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column
    protected String nome_exercicio;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    protected Professor professor;

    @ManyToOne
    @JoinColumn(name = "nome")  // Usa o nome como chave
    private UsuarioModel criado_por;

    public ListarExerciciosModel() {}

    public ListarExerciciosModel(Integer id, String nome_exercicio, Professor professor, UsuarioModel criado_por) {
        this.id = id;
        this.nome_exercicio = nome_exercicio;
        this.professor = professor;
        this.criado_por = criado_por;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome_exercicio() {
        return nome_exercicio;
    }

    public void setNome_exercicio(String nome_exercicio) {
        this.nome_exercicio = nome_exercicio;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public UsuarioModel getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(UsuarioModel criado_por) {
        this.criado_por = criado_por;
    }
}
