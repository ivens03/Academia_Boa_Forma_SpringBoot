package academia.boaForma.professor.repositories;

import academia.boaForma.professor.models.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorRepositorie extends JpaRepository<Professor, Long> {

    @Query("SELECT p FROM Professor p WHERE p.acessoSistema = true AND p.ativo = true")
    Page<Professor> findAllAcessoSistema(Pageable paginacao);

    @Query("SELECT p FROM Professor p WHERE p.id = :id")
    Professor professorById(@Param("id")  Integer id);
}
