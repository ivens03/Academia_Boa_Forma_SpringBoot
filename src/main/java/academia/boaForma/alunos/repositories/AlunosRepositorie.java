package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunosRepositorie extends JpaRepository<AlunosModel, Integer> {
    // Método para buscar todos os alunos sem paginação
    @Override
    @Query("SELECT a FROM AlunosModel a")
    List<AlunosModel> findAll();

    @Query("SELECT a FROM AlunosModel a WHERE a.acessoSistema = true")
    Page<AlunosModel> findAllAcessoSistema(Pageable paginacao);

    @Query("SELECT a FROM AlunosModel a WHERE LOWER(a.nome) = LOWER(:nome)")
    AlunosModel findByNome(@Param("nome") String nome);

    @Query("SELECT p FROM AlunosModel p WHERE p.id = :id")
    AlunosModel alunoById(@Param("id") Integer id);

    @Query("SELECT a FROM AlunosModel a WHERE a.acessoSistema = false")
    Page<AlunosModel> findAllNaoAcessoSistema(Pageable paginacao);
}
