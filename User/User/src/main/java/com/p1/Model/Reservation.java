package com.p1.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.p1.Dto.ChambreDTO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Reservation {
    public enum ConfirmationStatus {
        PENDING,
        REJECTED,
        VALID
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reservationDate;
    private Long foyerId;
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    @JsonBackReference("chambre_reservation")

    private Chambre chambre;

    private ConfirmationStatus confirmed = ConfirmationStatus.PENDING; // Default is PENDING

    public ConfirmationStatus getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(ConfirmationStatus confirmed) {
        if (confirmed == null) {
            throw new IllegalArgumentException("Confirmation status cannot be null");
        }
        this.confirmed = confirmed;
    }

    public void setFoyerId() {
        this.foyerId = chambre.getFoyer().getId();
    }

    public Long getFoyerId() {
        return foyerId;
    }

}
