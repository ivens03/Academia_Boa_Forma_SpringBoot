package academia.boaForma.professor.models.exercicios;

import academia.boaForma.professor.models.Professor;
import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.*;

import java.io.Serializable;

/*
 *   Tabela responsavel pelos os nomes das tecnicas avançadas de treino e sua descrição
 *   Somente @Param Professor Master pode incluir ou remover exercicios
*/

@Entity
@Table(name = "lista_tecnicas_avancadas")
public class ListarTecnicasModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column
    protected String tecnica_Avancada;

    @Column
    protected String descricao_tecnica;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    protected Professor professor;

    @ManyToOne
    @JoinColumn(name = "nome")  // Usa o nome como chave
    private UsuarioModel criado_por;

    public ListarTecnicasModel() {}

    public ListarTecnicasModel(Integer id, String tecnica_Avancada, String descricao_tecnica, Professor professor, UsuarioModel criado_por) {
        this.id = id;
        this.tecnica_Avancada = tecnica_Avancada;
        this.descricao_tecnica = descricao_tecnica;
        this.professor = professor;
        this.criado_por = criado_por;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTecnica_Avancada() {
        return tecnica_Avancada;
    }

    public void setTecnica_Avancada(String tecnica_Avancada) {
        this.tecnica_Avancada = tecnica_Avancada;
    }

    public String getDescricao_tecnica() {
        return descricao_tecnica;
    }

    public void setDescricao_tecnica(String descricao_tecnica) {
        this.descricao_tecnica = descricao_tecnica;
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
