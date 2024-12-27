package com.p1.Controllers;

import com.p1.Model.Chambre;
import com.p1.Model.Personnel;
import com.p1.Service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p1.Model.Foyer;
import com.p1.Service.ChambreService;
import com.p1.Service.FoyerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/personnel")
@PreAuthorize("hasRole('PERSONNEL')")
public class PersonnelController {

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private ChambreService chambreService;

    // chambres
    @PostMapping("/chambre")
    public ResponseEntity<Chambre> addChambre(@Valid @RequestBody Chambre chambre, @RequestParam Long foyerId) {
        Chambre createdChambre = chambreService.addChambre(chambre, foyerId);
        return ResponseEntity.ok(createdChambre);
    }

    @GetMapping("/Allchambres")
    public ResponseEntity<List<Chambre>> getAllChambre() {
        List<Chambre> chambres = chambreService.getAllChambre();
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
}
