package academia.boaForma.alunos.models.pagamentos;

import academia.boaForma.alunos.models.informacoes.Alunos;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "pagamentos_alunos")
public class PagamentosAlunosModel implements Serializable {

    //COLUNAS DAS TABELAS

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_Pagamento;

    @Column(nullable = false)
    private Date dia_para_pagar;

    @Column(nullable = false)
    private Date dia_pagamento;

    @Column(nullable = false)
    private Date validade_pagamento;

    @Embedded
    private TipoPagamentoEnum tipoPagamento;

    @Column(nullable = false)
    private BigDecimal valor_pago;

    @Column(nullable = false)
    private LocalDate registro_pagamento;

    // Relacionamento com alunos

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Alunos id_aluno;

    //CONSTRUTOR

    public PagamentosAlunosModel() {}
}



//GETTERS E SETTERS


