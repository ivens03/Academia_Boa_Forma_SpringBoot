package academia.boaForma.alunos.repositories;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunosRepositorie extends JpaRepository<AlunosModel, Integer> {

    boolean existsByNome(String nome);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByTelefoneEmergencia(String telefoneEmergencia);

    @Query("SELECT a FROM AlunosModel a WHERE a.acessoSistema = true")
    Page<AlunosModel> findAllAcessoSistema(Pageable paginacao);

    @Query("SELECT a FROM AlunosModel a WHERE LOWER(a.nome) = LOWER(:nome)")
    AlunosModel findByNome(@Param("nome") String nome);
}
