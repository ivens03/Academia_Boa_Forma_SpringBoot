package academia.boaForma.usuarios.repositories;

import academia.boaForma.usuarios.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

    /*@Query("SELECT p FROM UsuarioModel p WHERE p.email = :email")*/
    Optional<UsuarioModel> findByEmail(String email);

}
