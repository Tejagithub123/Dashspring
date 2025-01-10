package com.p1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p1.Model.Reservation;
import com.p1.Model.Chambre;
import com.p1.Model.Etudiant;
import com.p1.Repository.ChambreRepository;
import com.p1.Repository.EtudiantRepository;
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

    public Reservation addReservation(Reservation reservation) {
        Chambre chambre = chambreRepository.findById(reservation.getChambre().getId())
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));

        Etudiant etudiant = etudiantRepository.findById(reservation.getEtudiant().getId())
                .orElseThrow(() -> new IllegalArgumentException("Etudiant not found"));

        // Check if the student already reserved the same chambre
        Optional<Reservation> existingReservation = reservationRepository.findByEtudiantAndChambre(
                etudiant, chambre);

        // If an existing reservation is found, throw an exception or return a response
        if (existingReservation.isPresent()) {
            throw new IllegalArgumentException("Student has already reserved this chambre.");
        }

        reservation.setChambre(chambre);
        reservation.setEtudiant(etudiant);
        reservation.setReservationDate(new Date());

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

        if (updatedReservation.isConfirmed()) {
            existingReservation.setConfirmed(true);
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
}
