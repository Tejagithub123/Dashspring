package com.p1.Controllers;

import com.p1.Dto.ChambreDTO;
import com.p1.Model.Chambre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p1.Service.ChambreService;

import java.sql.Date;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import com.p1.Model.Reservation;
import com.p1.Service.ReservationService;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/etudiant")
@PreAuthorize("hasRole('ETUDIANT')")
public class EtudiantController {

    @Autowired
    private ChambreService chambreService;

    @GetMapping("/Allchambres")
    public ResponseEntity<List<ChambreDTO>> getAllChambre() {
        List<ChambreDTO> chambres = chambreService.getAllChambres();
        return ResponseEntity.ok(chambres);
    }

    @GetMapping("/listechambres")
    public ResponseEntity<List<Chambre>> getChambresliste() {
        List<Chambre> chambres = chambreService.getlisteChambre(); // Fetch all chambres
        return ResponseEntity.ok(chambres); // Return list of chambres in the response
    }

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> addReservation(
            @RequestParam Long chambreId,
            @RequestParam Long etudiantId) {
        try {
            Reservation reservation = reservationService.addReservation(chambreId, etudiantId);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("reservation/getone/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PutMapping("reservation/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
            @RequestBody Reservation updatedReservation) {
        Reservation updatedRes = reservationService.updateReservation(id, updatedReservation);
        return new ResponseEntity<>(updatedRes, HttpStatus.OK);
    }

    @GetMapping("reservation/{id}")
    public ResponseEntity<List<Reservation>> GetReservationsByEtudiant(@PathVariable Long id) {
        List<Reservation> reservations = reservationService.getReservationsByEtudiant(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("reservation/chambre/{chambreId}")
    public ResponseEntity<List<Reservation>> getReservationsByChambre(@PathVariable Long chambreId) {
        List<Reservation> reservations = reservationService.getReservationsByChambre(chambreId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("reservation/etudiant/{etudiantId}")
    public ResponseEntity<List<Reservation>> getReservationsByEtudiant(@PathVariable Long etudiantId) {
        List<Reservation> reservations = reservationService.getReservationsByEtudiant(etudiantId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}
