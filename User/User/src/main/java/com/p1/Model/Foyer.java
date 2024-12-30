package com.p1.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    // Example: Store Etudiant IDs as a list
    @ElementCollection
    private List<Long> etudiantIds = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "foyer_id")
    @JsonManagedReference
    private List<Etudiant> etudiants;

    public void addEtudiantId(Long etudiantId) {
        etudiantIds.add(etudiantId);
    }

}