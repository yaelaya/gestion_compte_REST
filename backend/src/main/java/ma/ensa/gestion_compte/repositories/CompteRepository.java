package ma.ensa.gestion_compte.repositories;

import ma.ensa.gestion_compte.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    // manipuler les données fbdd bla manimplementiw manuellement les méthodes de base
}
