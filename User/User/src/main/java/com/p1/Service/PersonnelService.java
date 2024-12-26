package com.p1.Service;

import com.p1.Model.Utilisateur;
import com.p1.Model.Personnel;
import com.p1.Model.Foyer;
import com.p1.Repository.PersonnelRepository;
import com.p1.Repository.FoyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private FoyerRepository foyerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Personnel addPersonnel(Personnel personnel, Long foyerId) {
        Optional<Foyer> foyer = foyerRepository.findById(foyerId);

        if (foyer.isPresent()) {

            String hashedPassword = passwordEncoder.encode(personnel.getMdp());
            personnel.setMdp(hashedPassword);

            personnel.setFoyer(foyer.get());
            personnel.setRole(Utilisateur.Role.ROLE_PERSONNEL);

            return personnelRepository.save(personnel);
        } else {
            throw new IllegalArgumentException("Le foyer n'existe pas");
        }
    }

    public List<Personnel> getAllPersonnels() {
        return personnelRepository.findAll();

    }

    public Personnel getPersonnel(Long id) {
        return personnelRepository.findById(id).orElse(null);
    }

    public Personnel assignFoyerToPersonnel(Long personnelId, Long foyerId) {
        Optional<Personnel> personnel = personnelRepository.findById(personnelId);
        Optional<Foyer> foyer = foyerRepository.findById(foyerId);

        if (personnel.isPresent() && foyer.isPresent()) {
            personnel.get().setFoyer(foyer.get());
            personnel.get().setRole(Utilisateur.Role.ROLE_PERSONNEL);
            return personnelRepository.save(personnel.get());
        }
        throw new IllegalArgumentException("Personnel ou foyer non trouvés");
    }

    public Personnel updatePersonnel(Long id, Personnel updatedPersonnel) {
        Optional<Personnel> existingPersonnel = personnelRepository.findById(id);

        if (existingPersonnel.isPresent()) {
            Personnel personnel = existingPersonnel.get();

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

                String hashedPassword = passwordEncoder.encode(updatedPersonnel.getMdp());
                personnel.setMdp(hashedPassword);
            }

            return personnelRepository.save(personnel);
        } else {
            throw new IllegalArgumentException("Personnel non trouvé");
        }
    }

    public void deletePersonnel(Long id) {
        Optional<Personnel> personnel = personnelRepository.findById(id);

        if (personnel.isPresent()) {
            personnelRepository.delete(personnel.get());
        } else {
            throw new IllegalArgumentException("Personnel non trouvé");
        }
    }
}
