package academia.boaForma.dtos.alunos;

import java.util.Date;

public record PagamentosAlunosDto(
        Integer idAluno,
        String nomeAluno,
        Date diaPagamento,
        Date diaPago) {
}
