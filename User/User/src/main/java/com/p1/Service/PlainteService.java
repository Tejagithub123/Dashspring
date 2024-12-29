package com.p1.Service;

import com.p1.Model.Plainte;
import com.p1.Repository.PlainteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlainteService {

    @Autowired
    private PlainteRepository plainteRepository;

    // Create a new Plainte
    public Plainte addPlainte(Plainte plainte) {
        // Set cloturee to false by default
        plainte.setCloturee(false);

        return plainteRepository.save(plainte);
    }

    // Get all Plaintes
    public List<Plainte> getAllPlaintes() {
        return plainteRepository.findAll();
    }

    // Get a Plainte by its ID
    public Optional<Plainte> getPlainteById(Long id) {
        return plainteRepository.findById(id);
    }

    // Update an existing Plainte
    public Plainte updatePlainte(Long id, Plainte updatedPlainte) {
        Plainte existingPlainte = getPlainteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plainte not found with ID: " + id));

        // Update fields if new data is provided
        if (updatedPlainte.getDate() != null) {
            existingPlainte.setDate(updatedPlainte.getDate());
        }
        existingPlainte.setCloturee(updatedPlainte.isCloturee());
        existingPlainte.setDescription(updatedPlainte.getDescription());

        // Optionally handle relationships or other fields
        // existingPlainte.setAgent(updatedPlainte.getAgent()); // Example if you want
        // to update the agent
        // existingPlainte.setChambre(updatedPlainte.getChambre()); // Example if you
        // want to update the chambre

        return plainteRepository.save(existingPlainte);
    }

    // Delete a Plainte by its ID
    public void deletePlainte(Long id) {
        Plainte plainte = getPlainteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plainte not found with ID: " + id));
        plainteRepository.delete(plainte);
    }
}