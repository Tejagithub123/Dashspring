package com.p1.Service;

import com.p1.Model.Utilisateur;
import com.p1.Model.Personnel;
import com.p1.Model.Foyer;
import com.p1.Repository.PersonnelRepository;
import com.p1.Repository.FoyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.p1.Service.EmailService;
import java.util.Optional;

import javax.transaction.Transactional;

import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private FoyerRepository foyerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private EmailService emailService;

    public Personnel addPersonnel(Personnel personnel) {

        String plainPassword = personnel.getMdp();

        String hashedPassword = passwordEncoder.encode(plainPassword);
        personnel.setMdp(hashedPassword);

        personnel.setRole(Utilisateur.Role.ROLE_PERSONNEL);

        if (personnel.getFoyer() != null && personnel.getFoyer().getId() != null) {

            Foyer foyer = foyerService.getFoyerById(personnel.getFoyer().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Foyer not found with ID: " + personnel.getFoyer().getId()));
            personnel.setFoyer(foyer);
        }

        Personnel savedPersonnel = personnelRepository.save(personnel);

        String emailSubject = "Bienvenue sur GestionRésidence!";
        String emailBody = String.format(
                "Bonjour %s,\n\nVotre compte a été créé avec succès sur la plateforme GestionRésidence.\n\nVoici vos identifiants de connexion :\nEmail: %s\nMot de passe: %s\n\nMerci de vous connecter à la plateforme.\n\nCordialement,\nL'équipe GestionRésidence",
                personnel.getNom(), personnel.getEmail(), plainPassword);

        emailService.sendEmail(personnel.getEmail(), emailSubject, emailBody);

        return savedPersonnel;
    }

    public List<Personnel> getAllPersonnels() {
        return personnelRepository.findAll();

    }

    public Personnel getPersonnel(Long id) {
        return personnelRepository.findById(id).orElse(null);
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

            if (updatedPersonnel.getCin() != null) {
                personnel.setCin(updatedPersonnel.getCin());
            }

            if (updatedPersonnel.getFoyer() != null && updatedPersonnel.getFoyer().getId() != null) {
                Foyer foyer = foyerService.getFoyerById(updatedPersonnel.getFoyer().getId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Foyer not found with ID: " + updatedPersonnel.getFoyer().getId()));
                personnel.setFoyer(foyer);
            } else if (updatedPersonnel.getFoyer() == null) {

                personnel.setFoyer(null);
            }

            return personnelRepository.save(personnel);
        } else {
            throw new IllegalArgumentException("Personnel not found with ID: " + id);
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

    public Personnel assignFoyerToPersonnel(Long personnelId, Long foyerId) {
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new IllegalArgumentException("Personnel non trouvé"));
        Foyer foyer = foyerRepository.findById(foyerId)
                .orElseThrow(() -> new IllegalArgumentException("Foyer non trouvé"));

        if (personnel.getFoyer() != null) {
            throw new IllegalStateException("Ce personnel est déjà affecté à un foyer");
        }

        personnel.setFoyer(foyer);
        foyer.setPersonnel(personnel);

        foyerRepository.save(foyer);
        return personnelRepository.save(personnel);
    }

}
