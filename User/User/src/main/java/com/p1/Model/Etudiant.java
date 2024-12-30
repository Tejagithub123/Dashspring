package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@DiscriminatorValue("ROLE_ETUDIANT")
@EqualsAndHashCode(callSuper = true)
public class Etudiant extends Utilisateur {
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reservation> reservations;
}
