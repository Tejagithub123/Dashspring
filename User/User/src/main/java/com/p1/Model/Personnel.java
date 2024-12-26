package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@DiscriminatorValue("ROLE_PERSONNEL")
@EqualsAndHashCode(callSuper = true)

public class Personnel extends Utilisateur {

    @OneToOne
    @JoinColumn(name = "foyer_id", nullable = true)
    @JsonBackReference
    private Foyer foyer;

}