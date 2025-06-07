package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.models.pagamentos.StatusPagamentoEnum;
import academia.boaForma.alunos.models.pagamentos.StatusRecebidos;
import academia.boaForma.alunos.models.pagamentos.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoPagamentosAlunos(
        LocalDate data_de_pagamento,
        LocalDate data_pagamento_efetuado,
        LocalDate validade_pagamento,
        TipoPagamentoEnum tipoPagamento,
        StatusPagamentoEnum statusPagamento,
        BigDecimal valor_pago,
        StatusRecebidos statusRecebidos
) {

    public DadosDetalhamentoPagamentosAlunos(PagamentosAlunosModel pagamentosAlunos) {
        this(
                pagamentosAlunos.getData_de_pagamento(),
                pagamentosAlunos.getData_pagamento_efetuado(),
                pagamentosAlunos.getValidade_pagamento(),
                pagamentosAlunos.getTipoPagamento(),
                pagamentosAlunos.getStatusPagamento(),
                pagamentosAlunos.getValor_pago(),
                pagamentosAlunos.getStatusRecebidos()
        );
    }
}
