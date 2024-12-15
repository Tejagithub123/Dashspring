package com.p1.Service;

import com.p1.Model.Utilisateur;
import com.p1.Model.Personnel;
import com.p1.Model.Foyer;
import com.p1.Repository.PersonnelRepository;
import com.p1.Repository.FoyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    // Méthode pour ajouter un personnel
    public Personnel addPersonnel(Personnel personnel, Long foyerId) {
        Optional<Foyer> foyer = foyerRepository.findById(foyerId);
        if (foyer.isPresent()) {
            personnel.setFoyer(foyer.get());
            personnel.setRole(Utilisateur.Role.PERSONNEL);

            return personnelRepository.save(personnel);
        } else {
            throw new IllegalArgumentException("Le foyer n'existe pas");
        }
    }

    // Récupérer tous les personnels
    public List<Personnel> getAllPersonnels() {
        return personnelRepository.findAll(); // Appelle la méthode findAll() du repository pour récupérer tous les
                                              // personnels
    }

    // Méthode pour récupérer un personnel par ID
    public Personnel getPersonnel(Long id) {
        return personnelRepository.findById(id).orElse(null);
    }

    // Méthode pour affecter un foyer à un personnel existant
    public Personnel assignFoyerToPersonnel(Long personnelId, Long foyerId) {
        Optional<Personnel> personnel = personnelRepository.findById(personnelId);
        Optional<Foyer> foyer = foyerRepository.findById(foyerId);

        if (personnel.isPresent() && foyer.isPresent()) {
            personnel.get().setFoyer(foyer.get());
            personnel.get().setRole(Utilisateur.Role.PERSONNEL);
            return personnelRepository.save(personnel.get());
        }
        throw new IllegalArgumentException("Personnel ou foyer non trouvés");
    }

    // Méthode pour mettre à jour partiellement un personnel
    public Personnel updatePersonnel(Long id, Personnel updatedPersonnel) {
        Optional<Personnel> existingPersonnel = personnelRepository.findById(id);

        if (existingPersonnel.isPresent()) {
            Personnel personnel = existingPersonnel.get();

            // Mettre à jour uniquement les champs non nuls de updatedPersonnel
            if (updatedPersonnel.getNom() != null) {
                personnel.setNom(updatedPersonnel.getNom());
            }
            if (updatedPersonnel.getPrenom() != null) {
                personnel.setPrenom(updatedPersonnel.getPrenom());
            }
            if (updatedPersonnel.getEmail() != null) {
                personnel.setEmail(updatedPersonnel.getEmail());
            }
            if (updatedPersonnel.getRole() != null) {
                personnel.setRole(updatedPersonnel.getRole());
            }
            if (updatedPersonnel.getDateNaissance() != null) {
                personnel.setDateNaissance(updatedPersonnel.getDateNaissance());
            }
            if (updatedPersonnel.getMdp() != null) {
                personnel.setMdp(updatedPersonnel.getMdp());
            }

            return personnelRepository.save(personnel); // Sauvegarder les modifications
        } else {
            throw new IllegalArgumentException("Personnel non trouvé");
        }
    }

    // Méthode pour supprimer un personnel
    public void deletePersonnel(Long id) {
        Optional<Personnel> personnel = personnelRepository.findById(id);

        if (personnel.isPresent()) {
            personnelRepository.delete(personnel.get()); // Supprimer le personnel
        } else {
            throw new IllegalArgumentException("Personnel non trouvé");
        }
    }
}
