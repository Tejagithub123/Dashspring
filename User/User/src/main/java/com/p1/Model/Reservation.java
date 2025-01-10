package com.p1.Model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reservationDate;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    @JsonBackReference("chambre_reservation")
    private Chambre chambre;

    private boolean confirmed = false; // Default is false

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

}
