package academia.boaForma.alunos.dtos.pagamentosDtos;

import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.models.pagamentos.StatusPagamentoEnum;
import academia.boaForma.alunos.models.pagamentos.StatusRecebidos;
import academia.boaForma.alunos.models.pagamentos.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoPagamento(
        LocalDate dataPagamento,
        LocalDate data_pagamento_efetuado,
        LocalDate validade_pagamento,
        TipoPagamentoEnum tipoPagamento,
        StatusPagamentoEnum statusPagamento,
        BigDecimal valor_pago,
        StatusRecebidos statusRecebidos
    ) {
    public DadosDetalhamentoPagamento(PagamentosAlunosModel pagamento) {
        this(
            pagamento.getData_de_pagamento(),
            pagamento.getData_pagamento_efetuado(),
            pagamento.getValidade_pagamento(),
            pagamento.getTipoPagamento(),
            pagamento.getStatusPagamento(),
            pagamento.getValor_pago(),
            pagamento.getStatusRecebidos()
        );
    }
}
