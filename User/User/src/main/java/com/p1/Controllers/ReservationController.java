package com.p1.Controllers;

import com.p1.Model.Reservation;

import com.p1.Service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/reservations")
@PreAuthorize("hasRole('ETUDIANT')")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // chambres
    @PostMapping("/All")
    public Reservation createReservation(@RequestParam Long etudiantId, @RequestParam Long chambreId) {
        return reservationService.createReservation(etudiantId, chambreId);
    }
}
