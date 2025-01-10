package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("ROLE_ETUDIANT")
@EqualsAndHashCode(callSuper = true)

public class Etudiant extends Utilisateur {
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Link to reservations
    private List<Reservation> reservations;
}