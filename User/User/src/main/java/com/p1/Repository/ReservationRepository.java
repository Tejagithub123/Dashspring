package com.p1.Repository;

import java.util.List;
import java.util.Optional;

import com.p1.Model.Chambre;
import com.p1.Model.Etudiant;
import com.p1.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByChambreId(Long chambreId);

    List<Reservation> findByEtudiantId(Long etudiantId);

    Optional<Reservation> findByEtudiantAndChambre(Etudiant etudiant, Chambre chambre);

    List<Reservation> findByFoyerId(Long idFoyer);
}
