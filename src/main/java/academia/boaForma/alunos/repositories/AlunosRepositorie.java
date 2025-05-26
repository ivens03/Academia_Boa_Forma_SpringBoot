package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.Alunos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunosRepositorie extends JpaRepository<Alunos, Integer> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Alunos a WHERE a.nome = :nome")
    boolean existsByNome_aluno(@Param("nome") String nome);
}
