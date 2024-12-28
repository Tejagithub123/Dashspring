package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("ROLE_AGENT")
public class AgentMaintenance extends Utilisateur {

    public enum Specialite {
        MENUISERIE, CHAUD, FROID, ELECTRICITE
    }

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    // Relation One-to-Many via Plainte (un agent peut avoir plusieurs plaintes)
    @OneToMany(mappedBy = "agent")
    @JsonManagedReference("agent_plainte") // Propriétaire de la relation, sérialisé
    private List<Plainte> plaintes;
}
