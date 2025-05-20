package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunosRepositorie extends JpaRepository<AlunosModel, Integer> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AlunosModel a WHERE a.nome_aluno = :nome_aluno")
    boolean existsByNome_aluno(@Param("nome_aluno") String nome_aluno);
}
