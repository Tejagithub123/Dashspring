package com.p1.Model;

import lombok.Data;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Data
@Entity
public class Facture {

    public enum PaymentStatus {
        PAYEE, // Paid
        IMPAYEE // Unpaid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prix; // Room price

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFacture; // Invoice date

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.IMPAYEE; // Default is unpaid

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference
    private Etudiant etudiant; // Link to the student

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    @JsonBackReference
    private Reservation reservation; // Link to the reservation

    // Constructor
    public Facture() {
        this.dateFacture = new Date(); // Set the invoice date to the current date
    }
}