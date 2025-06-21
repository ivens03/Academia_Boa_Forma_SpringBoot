package academia.boaForma.professor.repositories;

import academia.boaForma.professor.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepositorie extends JpaRepository<Professor, Long> {
}
