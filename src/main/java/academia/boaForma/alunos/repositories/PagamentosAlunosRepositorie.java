package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagamentosAlunosRepositorie extends JpaRepository<PagamentosAlunosModel, Integer> {

    @Query("SELECT p FROM PagamentosAlunosModel p WHERE p.id_Pagamento = :id")
    PagamentosAlunosModel pagamentosAlunosById(@Param("id") Integer id);
}
