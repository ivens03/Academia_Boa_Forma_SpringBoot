package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentosAlunosRepositorie extends JpaRepository<PagamentosAlunosModel, Integer> {
}
