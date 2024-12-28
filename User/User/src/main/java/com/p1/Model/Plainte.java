package com.p1.Model;

import lombok.Data;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;

@Data
@Entity
public class Plainte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private boolean cloturee;
    private String description;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    @JsonBackReference("agent_plainte")
    private AgentMaintenance agent;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    @JsonBackReference("chambre_plainte")
    private Chambre chambre;
}
