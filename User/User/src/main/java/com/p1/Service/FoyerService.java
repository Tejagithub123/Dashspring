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

    // Méthode pour ajouter un foyer
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    // Récupérer tous les foyers
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    // Méthode pour récupérer un foyer par ID
    public Optional<Foyer> getFoyerById(Long id) {
        return foyerRepository.findById(id);
    }

    // Méthode pour mettre à jour un foyer
    public Foyer updateFoyer(Long id, Foyer foyerDetails) {
        Foyer foyer = foyerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Foyer non trouvé"));
        foyer.setNom(foyerDetails.getNom());
        return foyerRepository.save(foyer);
    }

    // Méthode pour supprimer un foyer
    public void deleteFoyer(Long id) {
        Foyer foyer = foyerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Foyer non trouvé"));
        foyerRepository.delete(foyer);
    }
}
