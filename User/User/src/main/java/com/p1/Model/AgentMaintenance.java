package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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

}