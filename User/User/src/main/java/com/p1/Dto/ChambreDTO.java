package com.p1.Dto;

public class ChambreDTO {

    private Long id;
    private String name;
    private String description;
    private String type;
    private boolean availble;
    private String price;
    private Long foyerId; // ID of the associated Foyer
    private String foyerNom; // Name of the associated Foyer

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailble() {
        return availble;
    }

    public void setAvailble(boolean available) {
        this.availble = available;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getFoyerId() {
        return foyerId;
    }

    public void setFoyerId(Long foyerId) {
        this.foyerId = foyerId;
    }

    public String getFoyerNom() {
        return foyerNom;
    }

    public void setFoyerNom(String foyerNom) {
        this.foyerNom = foyerNom;
    }
}
