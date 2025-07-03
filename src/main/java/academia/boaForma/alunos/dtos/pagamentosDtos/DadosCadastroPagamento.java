package academia.boaForma.alunos.dtos.pagamentosDtos;

import academia.boaForma.alunos.models.pagamentos.StatusPagamentoEnum;
import academia.boaForma.alunos.models.pagamentos.StatusRecebidos;
import academia.boaForma.alunos.models.pagamentos.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroPagamento(
     Long id_aluno,
     LocalDate dataPagamento,
     LocalDate data_pagamento_efetuado,
     LocalDate validade_pagamento,
     TipoPagamentoEnum tipoPagamento,
     StatusPagamentoEnum statusPagamento,
     BigDecimal valor_pago,
     StatusRecebidos statusRecebidos
) { }
