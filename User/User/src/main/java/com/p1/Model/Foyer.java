package com.p1.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
public class Foyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToOne(mappedBy = "foyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Chambre chambre;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "foyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Etudiant> etudiants = new ArrayList<>();

    // Method to get IDs of all Etudiant objects
    public List<Long> getEtudiantIds() {
        return etudiants.stream()
                .map(Etudiant::getId)
                .collect(Collectors.toList());
    }

    // Method to add an Etudiant by its id
    public void addEtudiantById(Long etudiantId) {
        // Assuming you have a way to fetch the Etudiant by its id, e.g., using a
        // repository
        Etudiant etudiant = fetchEtudiantById(etudiantId); // This method would need to be implemented in your service
                                                           // or repository layer
        if (etudiant != null) {
            addEtudiant(etudiant); // Use the existing method to add Etudiant
        }
    }

    // Add Etudiant to the Foyer
    public void addEtudiant(Etudiant etudiant) {
        etudiants.add(etudiant);
        etudiant.setFoyer(this);
    }

    public void removeEtudiant(Etudiant etudiant) {
        etudiants.remove(etudiant);
        etudiant.setFoyer(null);
    }

    // Method to fetch Etudiant by ID (this can be done through your
    // service/repository layer)
    private Etudiant fetchEtudiantById(Long etudiantId) {
        return null; // Example placeholder, replace with actual logic
    }
}
