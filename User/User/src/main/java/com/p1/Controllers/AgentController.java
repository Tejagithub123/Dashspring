package com.p1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p1.Model.Chambre;
import com.p1.Model.Plainte;
import com.p1.Service.ChambreService;
import com.p1.Service.PlainteService;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/agent")
@PreAuthorize("hasRole('AGENT')")
public class AgentController {

    @Autowired
    private PlainteService plainteService;

    @PatchMapping("/plainte/{id}/cloturer")
    public ResponseEntity<Plainte> cloturerPlainte(@PathVariable Long id) {
        Plainte updatedPlainte = plainteService.cloturerPlainte(id);
        return ResponseEntity.ok(updatedPlainte);
    }

    @GetMapping("plainte/Allplaintes")
    public ResponseEntity<List<Plainte>> getAllPlaintes() {
        List<Plainte> plaintes = plainteService.getAllPlaintes();
        return new ResponseEntity<>(plaintes, HttpStatus.OK);
    }

}