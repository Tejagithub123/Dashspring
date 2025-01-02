package com.p1.Model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
public class Chambre {

    public enum TYPE {
        SINGLE, DOUBLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TYPE type;

    private boolean available;

    private String price;

    @OneToOne
    @JoinColumn(name = "foyer_id", nullable = true)
    @JsonBackReference
    private Foyer foyer;

    @OneToMany(mappedBy = "chambre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("chambre_plainte")
    private List<Plainte> plaintes;
    // private Boolean available;

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
