package academia.boaForma.models.alunos.pagamentos;

import academia.boaForma.models.alunos.informacoes.AlunosModel;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pagamentos_alunos")
public class PagamentosAlunosModel extends RepresentationModel<PagamentosAlunosModel> implements Serializable {

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_Pagamento;

    @Column(name = "dia_pagamento")
    private Date dia_pagamento;

    @Column(name = "dia_pago")
    private Date dia_pago;

    @Embedded
    private TipoPagamentoEnum tipoPagamento;

    @Column(name = "valor_pago")
    private BigDecimal valor_pago;
    // REVER NOS DTOs E FAZER OS DEVIDOS TESTES
    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private AlunosModel id_aluno;

    @ManyToOne
    @JoinColumn(name = "nome_aluno")
    private AlunosModel nome_aluno;
    /****************************************************************************************/
    //CONSTRUTOR

    public PagamentosAlunosModel() {}

    public PagamentosAlunosModel(Integer id_Pagamento, Date dia_pagamento, Date dia_pago, TipoPagamentoEnum tipoPagamento, BigDecimal valor_pago) {
        this.id_Pagamento = id_Pagamento;
        this.dia_pagamento = dia_pagamento;
        this.dia_pago = dia_pago;
        this.tipoPagamento = tipoPagamento;
        this.valor_pago = valor_pago;
    }

//GETTERS E SETTERS

    public Integer getIdPagamento() {
        return id_Pagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.id_Pagamento = idPagamento;
    }

    public Date getDia_pagamento() {
        return dia_pagamento;
    }

    public void setDia_pagamento(Date dia_pagamento) {
        this.dia_pagamento = dia_pagamento;
    }

    public Date getDia_pago() {
        return dia_pago;
    }

    public void setDia_pago(Date dia_pago) {
        this.dia_pago = dia_pago;
    }

    public TipoPagamentoEnum getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public BigDecimal getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(BigDecimal valor_pago) {
        this.valor_pago = valor_pago;
    }
}
