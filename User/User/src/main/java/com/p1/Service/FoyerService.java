package com.p1.Service;

import com.p1.Model.Foyer;
import com.p1.Repository.FoyerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class FoyerService {

    @Autowired
    private FoyerRepository foyerRepository;

    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    public Optional<Foyer> getFoyerById(Long id) {
        return foyerRepository.findById(id);
    }

    public Foyer updateFoyer(Long id, Foyer foyerDetails) {
        System.out.println("updating");

        // Find the foyer by ID, throw an exception if not found
        Foyer foyer = foyerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Foyer non trouvé"));

        // Only update 'nom' if a new value is provided
        if (foyerDetails.getNom() != null && !foyerDetails.getNom().isEmpty()) {
            foyer.setNom(foyerDetails.getNom());
        }

        // Only update latitude if a new value is provided
        if (foyerDetails.getLatitude() != 0.0) {
            foyer.setLatitude(foyerDetails.getLatitude());
        }

        // Only update longitude if a new value is provided and it's not 0.0
        if (foyerDetails.getLongitude() != 0.0 && foyerDetails.getLongitude() != 0.0) {
            foyer.setLongitude(foyerDetails.getLongitude());
        }

        // Save the updated foyer object
        return foyerRepository.save(foyer);
    }

    public void deleteFoyer(Long id) {
        Foyer foyer = foyerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Foyer non trouvé"));
        foyerRepository.delete(foyer);
    }
}
