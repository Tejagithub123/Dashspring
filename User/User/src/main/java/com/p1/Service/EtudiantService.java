package com.p1.Service;

import com.p1.Model.Utilisateur;
import com.p1.Model.Etudiant;
import com.p1.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public Etudiant addEtudiant(Etudiant etudiant) {

        String plainPassword = etudiant.getMdp();

        String hashedPassword = passwordEncoder.encode(plainPassword);
        etudiant.setMdp(hashedPassword);

        etudiant.setRole(Utilisateur.Role.ROLE_ETUDIANT);
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        String emailSubject = "Bienvenue sur GestionRésidence!";
        String emailBody = String.format(
                "Bonjour %s,\n\nVotre compte a été créé avec succès sur la plateforme GestionRésidence.\n\nVoici vos identifiants de connexion :\nEmail: %s\nMot de passe: %s\n\nMerci de vous connecter à la plateforme.\n\nCordialement,\nL'équipe GestionRésidence",
                etudiant.getNom(), etudiant.getEmail(), plainPassword);

        emailService.sendEmail(etudiant.getEmail(), emailSubject, emailBody);

        return savedEtudiant;
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();

    }

    public Etudiant getEtudiant(Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    public Etudiant updateEtudiant(Long id, Etudiant updatedEtudiant) {
        Optional<Etudiant> existingEtudiant = etudiantRepository.findById(id);

        if (existingEtudiant.isPresent()) {
            Etudiant etudiant = existingEtudiant.get();

            if (updatedEtudiant.getNom() != null) {
                etudiant.setNom(updatedEtudiant.getNom());
            }
            if (updatedEtudiant.getPrenom() != null) {
                etudiant.setPrenom(updatedEtudiant.getPrenom());
            }
            if (updatedEtudiant.getEmail() != null) {
                etudiant.setEmail(updatedEtudiant.getEmail());
            }

            if (updatedEtudiant.getDateNaissance() != null) {
                etudiant.setDateNaissance(updatedEtudiant.getDateNaissance());
            }
            if (updatedEtudiant.getMdp() != null) {
                String hashedPassword = passwordEncoder.encode(updatedEtudiant.getMdp());
                etudiant.setMdp(hashedPassword);
            }

            if (updatedEtudiant.getCin() != null) {
                etudiant.setCin(updatedEtudiant.getCin());
            }

            return etudiantRepository.save(etudiant);
        } else {
            throw new IllegalArgumentException("Etudiant not found with ID: " + id);
        }
    }

    public void deleteEtudiant(Long id) {
        Optional<Etudiant> etudiant = etudiantRepository.findById(id);

        if (etudiant.isPresent()) {
            etudiantRepository.delete(etudiant.get());
        } else {
            throw new IllegalArgumentException("Etudiant non trouvé");
        }
    }

}
