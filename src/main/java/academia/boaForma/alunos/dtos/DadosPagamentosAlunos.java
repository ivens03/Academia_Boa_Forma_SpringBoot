package academia.boaForma.alunos.dtos;

import academia.boaForma.alunos.models.pagamentos.StatusPagamentoEnum;
import academia.boaForma.alunos.models.pagamentos.StatusRecebidos;
import academia.boaForma.alunos.models.pagamentos.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosPagamentosAlunos(
        Integer id_Pagamento,
        LocalDate data_de_pagamento,
        LocalDate data_pagamento_efetuado,
        LocalDate validade_pagamento,
        TipoPagamentoEnum tipoPagamento,
        StatusPagamentoEnum statusPagamento,
        BigDecimal valor_pago,
        StatusRecebidos statusRecebidos,
        String nome
) { }
