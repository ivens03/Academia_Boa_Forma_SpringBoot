package academia.boaForma.alunos.dtos.pagamentosDtos;

import java.time.LocalDate;

public record DadoCalcularValidadePagamento(
        Integer id_Pagamento,
        LocalDate data_de_pagamento,
        LocalDate data_pagamento_efetuado
) {
}
