package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EqualsAndHashCode
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cin;

    private String nom;
    private String prenom;

    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private String mdp;

    private String role;

    @Override
    public String toString() {
        return "Utilisateur [cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email
                + ", dateNaissance=" + dateNaissance + ", role=" + role + "]";
    }
}
