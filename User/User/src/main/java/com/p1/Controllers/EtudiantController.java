package com.p1.Controllers;

import com.p1.Dto.ChambreDTO;
import com.p1.Model.Chambre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p1.Service.ChambreService;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/etudiant")
@PreAuthorize("hasRole('ETUDIANT')")
public class EtudiantController {

    @Autowired
    private ChambreService chambreService;

    @GetMapping("/Allchambres")
    public ResponseEntity<List<ChambreDTO>> getAllChambre() {
        List<ChambreDTO> chambres = chambreService.getAllChambres();
        return ResponseEntity.ok(chambres); // Retourne la liste de tous les personnels avec un code HTTP 200 OK
    }

}
