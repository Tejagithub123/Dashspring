package com.p1.Controllers;

import com.p1.Model.AgentMaintenance;
import com.p1.Model.Chambre;
import com.p1.Model.Etudiant;
import com.p1.Model.Personnel;
import com.p1.Model.Plainte;
import com.p1.Repository.ChambreRepository;
import com.p1.Service.PersonnelService;

import com.p1.Service.PlainteService;

import com.p1.Service.EtudiantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p1.Model.Foyer;
import com.p1.Service.AgentMaintenanceService;
import com.p1.Service.ChambreService;
import com.p1.Service.FoyerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Optional;
import com.p1.Service.AgentMaintenanceService;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/personnel")
@PreAuthorize("hasRole('PERSONNEL')")
public class PersonnelController {

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private ChambreService chambreService;
    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    ChambreRepository chambreRepository;

    // chambres
    @PostMapping("/chambre")
    public ResponseEntity<Chambre> addChambre(@Valid @RequestBody Chambre chambre, @RequestParam Long foyerId) {
        Chambre createdChambre = chambreService.addChambre(chambre, foyerId);
        return ResponseEntity.ok(createdChambre);
    }

    @GetMapping("/Allchambres/{id}")
    public ResponseEntity<List<Chambre>> getAllChambre(@PathVariable Long id) {
        List<Chambre> chambres = chambreService.getChambresByFoyerId(id);
        return ResponseEntity.ok(chambres); // Retourne la liste de tous les personnels avec un code HTTP 200 OK
    }

    @PatchMapping("/chambres/{id}")
    public ResponseEntity<Chambre> updateChambre(@PathVariable Long id, @RequestBody Chambre updatedChambre) {
        Chambre updated = chambreService.updateChambre(id, updatedChambre);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/Onechambre/{id}")
    public ResponseEntity<Chambre> getChambrebyId(@PathVariable Long id) {
        Chambre updated = chambreService.getChambrebyId(id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteChambre/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
        return ResponseEntity.noContent().build(); // Retourner une réponse 204 No Content
    }

    @GetMapping("/listechambres")
    public ResponseEntity<List<Chambre>> getChambresliste() {
        List<Chambre> chambres = chambreService.getlisteChambre(); // Fetch all chambres
        return ResponseEntity.ok(chambres); // Return list of chambres in the response
    }

    @Autowired
    private PersonnelService personnelService; // Instance of PersonnelService

    @GetMapping("/{id}")
    public ResponseEntity<Long> getPersonnel(@PathVariable Long id) {
        Personnel personnel = personnelService.getPersonnel(id); // Use the instance, not a static reference
        if (personnel != null) {
            return ResponseEntity.ok(personnel.getFoyer().getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    private PlainteService plainteService;

    // Create a new Plainte
    @PostMapping("plainte")
    public ResponseEntity<Plainte> createPlainte(@RequestBody Plainte plainte) {
        Plainte savedPlainte = plainteService.addPlainte(plainte);
        return new ResponseEntity<>(savedPlainte, HttpStatus.CREATED);
    }

    // Get all Plaintes
    @GetMapping("plainte/Allplaintes")
    public ResponseEntity<List<Plainte>> getAllPlaintes() {
        List<Plainte> plaintes = plainteService.getAllPlaintes();
        return new ResponseEntity<>(plaintes, HttpStatus.OK);
    }

    // Get a Plainte by its ID
    @GetMapping("plainte/{id}")
    public ResponseEntity<Plainte> getPlainteById(@PathVariable Long id) {
        Optional<Plainte> plainte = plainteService.getPlainteById(id);
        if (plainte.isPresent()) {
            return new ResponseEntity<>(plainte.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing Plainte
    @PutMapping("plainte/{id}")
    public ResponseEntity<Plainte> updatePlainte(@PathVariable Long id, @RequestBody Plainte updatedPlainte) {
        try {
            Plainte updated = plainteService.updatePlainte(id, updatedPlainte);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Plainte by its ID
    @DeleteMapping("plainte/{id}")
    public ResponseEntity<Void> deletePlainte(@PathVariable Long id) {
        try {
            plainteService.deletePlainte(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @PatchMapping("/{id}/maintenance")
    public ResponseEntity<Chambre> updateChambreMaintenance(@PathVariable Long id,
            @RequestParam boolean enMaintenance) {
        Chambre updatedChambre = chambreService.setChambreEnMaintenance(id, enMaintenance);
        return ResponseEntity.ok(updatedChambre);
    }

    @DeleteMapping("/etudiants/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build(); // Retourner une réponse 204 No Content
    }

    @Autowired
    private AgentMaintenanceService agentMaintenanceService;

    @GetMapping("/agent/agentAll")
    public ResponseEntity<List<AgentMaintenance>> getAllAgents() {
        return ResponseEntity.ok(agentMaintenanceService.getAllAgents());
    }

}
