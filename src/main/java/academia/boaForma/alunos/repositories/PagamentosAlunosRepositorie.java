package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PagamentosAlunosRepositorie extends JpaRepository<PagamentosAlunosModel, Integer> {

    @Query("SELECT p FROM PagamentosAlunosModel p WHERE p.id_Pagamento = :id")
    PagamentosAlunosModel pagamentosAlunosById(@Param("id") Integer id);

    @Query("SELECT p FROM PagamentosAlunosModel p WHERE p.id_Pagamento = :id AND p.data_pagamento_efetuado = :dataPagamentoEfetuado AND p.data_de_pagamento = :dataDePagamento")
    PagamentosAlunosModel consultarValidacaoPagamento(
            @Param("id") Integer id_Pagamento,
            @Param("dataPagamentoEfetuado") LocalDate dataPagamentoEfetuado,
            @Param("dataDePagamento") LocalDate data_de_pagamento
    );
}
