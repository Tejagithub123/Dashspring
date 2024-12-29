package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("ROLE_ETUDIANT")
@EqualsAndHashCode(callSuper = true)

public class Etudiant extends Utilisateur {
}