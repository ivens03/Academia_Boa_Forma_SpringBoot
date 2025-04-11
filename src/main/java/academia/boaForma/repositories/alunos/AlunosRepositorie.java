package academia.boaForma.repositories.alunos;

import academia.boaForma.models.alunos.informacoes.AlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunosRepositorie extends JpaRepository<AlunosModel, Integer> {
}
