package academia.boaForma.alunos.models.pagamentos;

import academia.boaForma.alunos.dtos.DadosPagamentosAlunos;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamentos_alunos")
public class PagamentosAlunosModel implements Serializable {

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id_Pagamento;

    @Column
    protected LocalDate data_de_pagamento;

    @Column
    protected LocalDate data_pagamento_efetuado;

    @Column
    protected LocalDate validade_pagamento;

    @Embedded
    protected TipoPagamentoEnum tipoPagamento;

    @Embedded
    protected StatusPagamentoEnum statusPagamento;

    @Column
    protected BigDecimal valor_pago;

    @Embedded
    protected StatusRecebidos statusRecebidos;

    // Relacionamento com alunos

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aluno")
    protected AlunosModel aluno;

    //CONSTRUTOR

    public PagamentosAlunosModel() {}


    public PagamentosAlunosModel(Integer id_Pagamento, LocalDate data_de_pagamento, LocalDate data_pagamento_efetuado, LocalDate validade_pagamento, TipoPagamentoEnum tipoPagamento, StatusPagamentoEnum statusPagamento, BigDecimal valor_pago, StatusRecebidos statusRecebidos, AlunosModel aluno) {
        this.id_Pagamento = id_Pagamento;
        this.data_de_pagamento = data_de_pagamento;
        this.data_pagamento_efetuado = data_pagamento_efetuado;
        this.validade_pagamento = validade_pagamento;
        this.tipoPagamento = tipoPagamento;
        this.statusPagamento = statusPagamento;
        this.valor_pago = valor_pago;
        this.statusRecebidos = statusRecebidos;
        this.aluno = aluno;
    }

    public PagamentosAlunosModel(@Valid DadosPagamentosAlunos dadosPagamentosAlunos) {
        if (dadosPagamentosAlunos.data_pagamento_efetuado() != null) { this.data_pagamento_efetuado = dadosPagamentosAlunos.data_pagamento_efetuado(); }
        if (dadosPagamentosAlunos.validade_pagamento() != null) { this.validade_pagamento = dadosPagamentosAlunos.validade_pagamento(); }
        if (dadosPagamentosAlunos.tipoPagamento() != null) { this.tipoPagamento = dadosPagamentosAlunos.tipoPagamento(); }
        if (dadosPagamentosAlunos.statusPagamento() != null) { this.statusPagamento = dadosPagamentosAlunos.statusPagamento(); }
        if (dadosPagamentosAlunos.valor_pago() != null) { this.valor_pago = dadosPagamentosAlunos.valor_pago(); }
        if (dadosPagamentosAlunos.statusRecebidos() != null) { this.statusRecebidos = dadosPagamentosAlunos.statusRecebidos(); }
    }

    //GETTERS E SETTERS

    public Integer getId_Pagamento() {
        return id_Pagamento;
    }

    public void setId_Pagamento(Integer id_Pagamento) {
        this.id_Pagamento = id_Pagamento;
    }

    public LocalDate getData_de_pagamento() {
        return data_de_pagamento;
    }

    public void setData_de_pagamento(LocalDate data_de_pagamento) {
        this.data_de_pagamento = data_de_pagamento;
    }

    public LocalDate getData_pagamento_efetuado() {
        return data_pagamento_efetuado;
    }

    public void setData_pagamento_efetuado(LocalDate data_pagamento_efetuado) {
        this.data_pagamento_efetuado = data_pagamento_efetuado;
    }

    public LocalDate getValidade_pagamento() {
        return validade_pagamento;
    }

    public void setValidade_pagamento(LocalDate validade_pagamento) {
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

    //Regista a data de pagamento efetuado
    @PrePersist
    public void registrarDataPagamentoEfetuado() {
        this.data_pagamento_efetuado = LocalDate.now();
    }

}