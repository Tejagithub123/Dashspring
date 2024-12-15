package com.p1.Controllers;

import com.p1.Model.Personnel;
import com.p1.Service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p1.Model.Foyer;
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

    // Ajouter un nouveau personnel et l'affecter à un foyer
    @PostMapping("/personnels")
    public ResponseEntity<Personnel> addPersonnel(@Valid @RequestBody Personnel personnel, @RequestParam Long foyerId) {
        Personnel createdPersonnel = personnelService.addPersonnel(personnel, foyerId);
        return ResponseEntity.ok(createdPersonnel);
    }

    // Récupérer tous les personnels
    @GetMapping("/Allpersonnels")
    public ResponseEntity<List<Personnel>> getAllPersonnels() {
        List<Personnel> personnels = personnelService.getAllPersonnels();
        return ResponseEntity.ok(personnels); // Retourne la liste de tous les personnels avec un code HTTP 200 OK
    }

    // Affecter un foyer à un personnel existant
    @PutMapping("/personnels/{personnelId}/foyer")
    public ResponseEntity<Personnel> assignFoyerToPersonnel(@PathVariable Long personnelId,
            @RequestParam Long foyerId) {
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
    public ResponseEntity<Foyer> addFoyer(@RequestBody Foyer foyer) {
        Foyer createdFoyer = foyerService.addFoyer(foyer);
        return ResponseEntity.ok(createdFoyer);
    }

    @CrossOrigin(origins = "http://localhost:4300")
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

    // Supprimer un foyer
    @DeleteMapping("/foyers/{id}")
    public ResponseEntity<Void> deleteFoyer(@PathVariable Long id) {
        foyerService.deleteFoyer(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}