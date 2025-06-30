package academia.boaForma.gestor.repositories;

import academia.boaForma.gestor.models.GestorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestorRepositorie extends JpaRepository<GestorModel, Integer> {
}
