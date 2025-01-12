package com.p1.Service;

import com.p1.Model.Chambre;
import com.p1.Model.Facture;
import com.p1.Model.Reservation;
import com.p1.Repository.ChambreRepository;
import com.p1.Repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    public List<Facture> getFacturesByEtudiant(Long etudiantId) {
        return factureRepository.findByEtudiantId(etudiantId);
    }

    @Autowired
    private ChambreRepository chambreRepository;

    public Facture markAsPaid(Long factureId) {
        // Find the Facture by ID
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new IllegalArgumentException("Facture not found"));

        // Update the Facture status to PAYEE
        facture.setStatus(Facture.PaymentStatus.PAYEE);

        // Retrieve the associated Reservation
        Reservation reservation = facture.getReservation();
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found for this Facture");
        }

        // Retrieve the associated Chambre
        Chambre chambre = reservation.getChambre();
        if (chambre == null) {
            throw new IllegalArgumentException("Chambre not found for this Reservation");
        }

        // Update the Chambre's available status to false
        chambre.setAvailble(false);

        // Save the updated Chambre
        chambreRepository.save(chambre);

        // Save the updated Facture
        return factureRepository.save(facture);
    }
}