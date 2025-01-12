package com.p1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p1.Model.Reservation;
import com.p1.Model.Chambre;
import com.p1.Model.Etudiant;
import com.p1.Model.Facture;
import com.p1.Repository.ChambreRepository;
import com.p1.Repository.EtudiantRepository;
import com.p1.Repository.FactureRepository;
import com.p1.Repository.ReservationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private FactureRepository factureRepository;

    public Reservation addReservation(Long chambreId, Long etudiantId) {
        // Fetch chambre from the database
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));
        // Fetch etudiant from the database
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant not found"));

        // Check if the student already reserved the same chambre
        Optional<Reservation> existingReservation = reservationRepository.findByEtudiantAndChambre(etudiant, chambre);

        if (existingReservation.isPresent()) {
            throw new IllegalArgumentException("Student has already reserved this chambre.");
        }

        // Create a new reservation
        Reservation reservation = new Reservation();
        reservation.setChambre(chambre); // Set the chambre object
        reservation.setEtudiant(etudiant); // Set the etudiant object
        reservation.setReservationDate(new Date());
        reservation.setFoyerId();
        // Save the new reservation
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        existingReservation.setConfirmed(updatedReservation.getConfirmed());
        existingReservation.setConfirmed(updatedReservation.getConfirmed());
        // If the reservation is approved, create a new invoice
        if (updatedReservation.getConfirmed() == Reservation.ConfirmationStatus.VALID) {
            Facture facture = new Facture();
            facture.setPrix(Double.parseDouble(existingReservation.getChambre().getPrice())); // Set the room price
            facture.setEtudiant(existingReservation.getEtudiant()); // Set the student
            facture.setReservation(existingReservation); // Link to the reservation

            // Save the invoice
            factureRepository.save(facture);

            // Link the invoice to the reservation
            existingReservation.setFacture(facture);
        }

        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationsByChambre(Long chambreId) {
        return reservationRepository.findByChambreId(chambreId);
    }

    public List<Reservation> getReservationsByEtudiant(Long etudiantId) {
        return reservationRepository.findByEtudiantId(etudiantId);
    }

    // Add a method to find reservations by Foyer ID
    public List<Reservation> findByFoyerId(Long idFoyer) {
        return reservationRepository.findByFoyerId(idFoyer);
    }
}
