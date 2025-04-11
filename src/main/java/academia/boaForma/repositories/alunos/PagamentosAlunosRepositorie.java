package academia.boaForma.repositories.alunos;

import academia.boaForma.models.alunos.pagamentos.PagamentosAlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentosAlunosRepositorie extends JpaRepository<PagamentosAlunosModel, Integer> {
}
