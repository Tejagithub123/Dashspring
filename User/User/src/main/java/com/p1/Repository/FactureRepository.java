package com.p1.Repository;

import com.p1.Model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByEtudiantId(Long etudiantId);

    @Query("SELECT f FROM Facture f WHERE f.reservation.chambre.foyer.id = :foyerId")
    List<Facture> findByReservationChambreFoyerId(@Param("foyerId") Long foyerId);

    List<Facture> findAll();
}