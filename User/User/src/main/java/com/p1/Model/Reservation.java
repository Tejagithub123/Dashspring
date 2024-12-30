package com.p1.Model;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {

    public enum Status {
        PENDING, REJECTED, ACCEPTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference // Prevent recursion when serializing Etudiant
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    private Chambre chambre;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime dateCreated = LocalDateTime.now();
}
