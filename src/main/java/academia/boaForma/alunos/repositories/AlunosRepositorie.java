package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunosRepositorie extends JpaRepository<AlunosModel, Integer> {
}
