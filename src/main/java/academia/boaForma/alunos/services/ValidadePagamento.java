/*
package academia.boaForma.alunos.services;

import academia.boaForma.alunos.dtos.pagamentosDtos.DadoCalcularValidadePagamento;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValidadePagamento {

    private final PagamentosAlunosRepositorie pagamentosAlunosRepositorie;

    public ValidadePagamento(PagamentosAlunosRepositorie pagamentosAlunosRepositorie) {
        this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie;
    }

    @Transactional
    public PagamentosAlunosModel logicaValidadePagamento(DadoCalcularValidadePagamento validadePagamento) {

        PagamentosAlunosModel pagamentosAlunosModel = pagamentosAlunosRepositorie.consultarValidacaoPagamento(
                validadePagamento.id_Pagamento(),
                validadePagamento.data_pagamento_efetuado(),
                validadePagamento.data_de_pagamento()
        );

        LocalDate dataPagamentoEfetuado = validadePagamento.data_pagamento_efetuado();
        LocalDate dataPagamento = validadePagamento.data_de_pagamento();
        LocalDate dataVencimentoMenoMes = dataPagamento.minusMonths(1);

        if (dataPagamentoEfetuado.isEqual(dataPagamento)) {

        }



    }

}
*/
