package com.p1.Model;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
public class Foyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToOne(mappedBy = "foyer")
    @JsonManagedReference
    private Personnel personnel;

    // Ajout des attributs latitude et longitude
    @Column(nullable = false) // Vous pouvez rendre ces champs optionnels en supprimant nullable = false
    private double latitude;

    @Column(nullable = false)
    private double longitude;

}