package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.pagamentos.TipoPagamentoEnum;

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
