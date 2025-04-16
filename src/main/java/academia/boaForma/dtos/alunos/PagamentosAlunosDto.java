package academia.boaForma.dtos.alunos;

import academia.boaForma.models.alunos.pagamentos.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.util.Date;

public record PagamentosAlunosDto(
        Integer id_Pagamento,
        Date dia_pagamento,
        Date dia_pago,
        TipoPagamentoEnum tipoPagamento,
        BigDecimal valor_pago
    ) {
}
