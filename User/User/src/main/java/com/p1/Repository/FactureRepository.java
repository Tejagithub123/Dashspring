package com.p1.Repository;

import com.p1.Model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByEtudiantId(Long etudiantId); // Find invoices by student ID
}