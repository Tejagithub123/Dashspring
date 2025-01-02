package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@DiscriminatorValue("ROLE_ETUDIANT")
@EqualsAndHashCode(callSuper = true)
public class Etudiant extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "foyer_id", nullable = true)
    @JsonBackReference
    private Foyer foyer;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reservation> reservations;
}
