
package com.p1.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import java.util.List;
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
    private String Description;
    @Enumerated(EnumType.STRING)
    private TYPE type;
    private boolean availble;
    private String price;
    private boolean enMaintenance = false;

    @OneToOne
    @ManyToOne
    @JoinColumn(name = "foyer_id", nullable = true)
    @JsonBackReference
    private Foyer foyer;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setAvailble(Boolean availble) {
        this.availble = availble;
    }

    public Boolean getAvailble(Boolean availble) {
        return this.availble;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isAvailble() {
        return this.availble;
    }

    public void setEnMaintenance(boolean enMaintenance) {
        this.enMaintenance = enMaintenance;
    }

    public boolean isEnMaintenance() {
        return this.enMaintenance;
    }

    @OneToMany(mappedBy = "chambre")
    @JsonManagedReference("chambre_plainte")
    private List<Plainte> plaintes;

}
