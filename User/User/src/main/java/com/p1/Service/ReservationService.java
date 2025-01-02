package com.p1.Service;

import com.p1.Model.Chambre;
import com.p1.Model.Etudiant;
import com.p1.Model.Foyer;
import com.p1.Model.Reservation;
import com.p1.Repository.ChambreRepository;
import com.p1.Repository.EtudiantRepository;
import com.p1.Repository.FoyerRepository;
import com.p1.Repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, ChambreRepository chambreRepository) {
        this.reservationRepository = reservationRepository;
        this.chambreRepository = chambreRepository;
    }

    public Reservation createReservation(Long etudiantId, Long chambreId) {
        // Fetch Etudiant and Chambre by IDs
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant not found"));
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        // Create and save reservation
        Reservation reservation = new Reservation();
        reservation.setEtudiant(etudiant);
        reservation.setChambre(chambre);
        reservation.setStatus(Reservation.Status.PENDING); // Assuming you have an enum for Status
        reservationRepository.save(reservation);

        return reservation;
    }

    @Autowired
    private FoyerRepository foyerRepository;

    public void approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        // Check if the reservation has already been approved
        if (reservation.getStatus() == Reservation.Status.ACCEPTED) {
            throw new IllegalStateException("This reservation has already been approved.");
        }

        // Update reservation status
        reservation.setStatus(Reservation.Status.ACCEPTED);

        // Get the chambre associated with the reservation
        Chambre chambre = reservation.getChambre();

        // Check if the chambre is available
        if (!chambre.isAvailable()) {
            throw new IllegalStateException("The chambre is already reserved and unavailable.");
        }

        // Set chambre as unavailable
        chambre.setAvailable(false);
        chambreRepository.save(chambre);

        // Assign the Etudiant's ID to the corresponding Foyer
        Etudiant etudiant = reservation.getEtudiant();
        Foyer foyer = chambre.getFoyer();

        if (foyer != null) {
            // Perform the association, assuming `Foyer` has a method to store Etudiant IDs
            // Example: Add the student to a list or set a reference
            // Assuming Foyer has a method like `addEtudiantId` or similar
            foyer.addEtudiantById(etudiant.getId()); // Ensure such a method exists in Foyer
            foyerRepository.save(foyer);
        }

        // Save the updated reservation
        reservationRepository.save(reservation);
    }

    public void rejectReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));
        reservation.setStatus(Reservation.Status.REJECTED);
        reservationRepository.save(reservation);
    }
}
