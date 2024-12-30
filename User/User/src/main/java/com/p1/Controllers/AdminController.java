package com.p1.Controllers;

import com.p1.Model.Chambre;
import com.p1.Model.Etudiant;
import com.p1.Model.Personnel;
import com.p1.Service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p1.Model.Foyer;
import com.p1.Service.ChambreService;
import com.p1.Service.EtudiantService;
import com.p1.Service.FoyerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private ChambreService chambreService;
    @Autowired
    private EtudiantService etudiantService;

    // Ajouter un nouveau personnel et l'affecter à un foyer
    @PostMapping("/personnels")
    public ResponseEntity<Personnel> addPersonnel(@Valid @RequestBody Personnel personnel) {
        Personnel createdPersonnel = personnelService.addPersonnel(personnel);
        return ResponseEntity.ok(createdPersonnel);
    }

    // Récupérer tous les personnels
    @GetMapping("/personnels/Allpersonnels")
    public ResponseEntity<List<Personnel>> getAllPersonnels() {
        List<Personnel> personnels = personnelService.getAllPersonnels();
        return ResponseEntity.ok(personnels); // Retourne la liste de tous les personnels avec un code HTTP 200 OK
    }

    // Affecter un foyer à un personnel existant
    @PutMapping("/personnels/{personnelId}/foyer")
    public ResponseEntity<Personnel> assignFoyerToPersonnel(@PathVariable Long personnelId,
            @RequestParam Long foyerId) {
        // Vérifie que les IDs sont valides
        if (foyerId == null) {
            throw new IllegalArgumentException("L'ID du foyer doit être fourni.");
        }

        Personnel updatedPersonnel = personnelService.assignFoyerToPersonnel(personnelId, foyerId);
        return ResponseEntity.ok(updatedPersonnel);
    }

    // Récupérer un personnel par son ID
    @GetMapping("/personnels/{id}")
    public ResponseEntity<Personnel> getPersonnel(@PathVariable Long id) {
        Personnel personnel = personnelService.getPersonnel(id);
        if (personnel != null) {
            return ResponseEntity.ok(personnel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mettre à jour partiellement un personnel
    @PatchMapping("/personnels/{id}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable Long id, @RequestBody Personnel updatedPersonnel) {
        Personnel updated = personnelService.updatePersonnel(id, updatedPersonnel);
        return ResponseEntity.ok(updated);
    }

    // Supprimer un personnel
    @DeleteMapping("/personnels/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return ResponseEntity.noContent().build(); // Retourner une réponse 204 No Content
    }

    // Ajouter un nouveau foyer
    @PostMapping("/foyers")
    public ResponseEntity<Foyer> createFoyer(@RequestBody Foyer foyer) {
        Foyer createdFoyer = foyerService.saveFoyer(foyer);
        return ResponseEntity.ok(createdFoyer);
    }

    // liste de foyers
    @GetMapping("/foyers")
    public ResponseEntity<List<Foyer>> getAllFoyers() {
        List<Foyer> foyers = foyerService.getAllFoyers();
        return ResponseEntity.ok(foyers);
    }

    // Récupérer un foyer par ID
    @GetMapping("/foyers/{id}")
    public ResponseEntity<Foyer> getFoyer(@PathVariable Long id) {
        return foyerService.getFoyerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un foyer
    @PutMapping("/foyers/{id}")
    public ResponseEntity<Foyer> updateFoyer(@PathVariable Long id, @RequestBody Foyer foyerDetails) {
        Foyer updatedFoyer = foyerService.updateFoyer(id, foyerDetails);
        return ResponseEntity.ok(updatedFoyer);
    }

    // FoyerController.java

    @DeleteMapping("/foyers/{id}")
    public ResponseEntity<Void> deleteFoyer(@PathVariable Long id) {
        try {
            foyerService.deleteFoyer(id);
            return ResponseEntity.noContent().build(); // 204 No Content if deleted successfully
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN) // 403 Forbidden if deletion is not allowed
                    .body(null);
        }
    }

    @PostMapping("/etudiant")
    public ResponseEntity<Etudiant> addEtudiant(@Valid @RequestBody Etudiant etudiant) {
        Etudiant createdEtudiant = etudiantService.addEtudiant(etudiant);
        return ResponseEntity.ok(createdEtudiant);
    }

    @GetMapping("/etudinat/Alletudiants")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        return ResponseEntity.ok(etudiants); // Retourne la liste de tous les personnels avec un code HTTP 200 OK
    }

    @DeleteMapping("/etudiants/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build(); // Retourner une réponse 204 No Content
    }

}
