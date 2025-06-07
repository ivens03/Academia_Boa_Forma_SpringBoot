package academia.boaForma.alunos.models.pagamentos;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pagamentos_alunos")
public class PagamentosAlunosModel implements Serializable {

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_Pagamento;

    @Column(nullable = false)
    private Date data_de_pagamento;

    @Column(nullable = false)
    private Date data_pagamento_efetuado;

    @Column(nullable = false)
    private Date validade_pagamento;

    @Embedded
    private TipoPagamentoEnum tipoPagamento;

    @Embedded
    private StatusPagamentoEnum statusPagamento;

    @Column(nullable = false)
    private BigDecimal valor_pago;

    @Embedded
    private StatusRecebidos statusRecebidos;

    // Relacionamento com alunos

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aluno", nullable = false)
    private AlunosModel aluno;

    //CONSTRUTOR

    public PagamentosAlunosModel() {}

    //GETTERS E SETTERS

    public Integer getId_Pagamento() {
        return id_Pagamento;
    }

    public void setId_Pagamento(Integer id_Pagamento) {
        this.id_Pagamento = id_Pagamento;
    }

    public Date getData_de_pagamento() {
        return data_de_pagamento;
    }

    public void setData_de_pagamento(Date data_de_pagamento) {
        this.data_de_pagamento = data_de_pagamento;
    }

    public Date getData_pagamento_efetuado() {
        return data_pagamento_efetuado;
    }

    public void setData_pagamento_efetuado(Date data_pagamento_efetuado) {
        this.data_pagamento_efetuado = data_pagamento_efetuado;
    }

    public Date getValidade_pagamento() {
        return validade_pagamento;
    }

    public void setValidade_pagamento(Date validade_pagamento) {
        this.validade_pagamento = validade_pagamento;
    }

    public TipoPagamentoEnum getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public StatusPagamentoEnum getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamentoEnum statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public BigDecimal getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(BigDecimal valor_pago) {
        this.valor_pago = valor_pago;
    }

    public StatusRecebidos getStatusRecebidos() {
        return statusRecebidos;
    }

    public void setStatusRecebidos(StatusRecebidos statusRecebidos) {
        this.statusRecebidos = statusRecebidos;
    }

    public AlunosModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunosModel aluno) {
        this.aluno = aluno;
    }
}