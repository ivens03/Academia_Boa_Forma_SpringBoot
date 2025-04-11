package academia.boaForma.models.alunos.pagamentos;

import academia.boaForma.models.alunos.informacoes.AlunosModel;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pagamentosAlunos")
public class PagamentosAlunosModel implements Serializable {

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAluno", referencedColumnName = "idAluno")
    private AlunosModel idAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomeAluno", referencedColumnName = "nomeAluno")
    private AlunosModel nomeAluno;

    @Column(name = "diaPagamento")
    private Date diaPagamento;

    @Column(name = "diaPago")
    private Date diaPago;

    //CONSTRUTOR

    public PagamentosAlunosModel() {}

    public PagamentosAlunosModel(Integer idPagamento, AlunosModel idAluno, AlunosModel nomeAluno, Date diaPagamento, Date diaPago) {
        this.idPagamento = idPagamento;
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.diaPagamento = diaPagamento;
        this.diaPago = diaPago;
    }

//GETTERS E SETTERS

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public AlunosModel getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(AlunosModel idAluno) {
        this.idAluno = idAluno;
    }

    public AlunosModel getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(AlunosModel nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Date getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(Date diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public Date getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Date diaPago) {
        this.diaPago = diaPago;
    }
}
