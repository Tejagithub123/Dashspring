package com.p1.Service;

import com.p1.Dto.ChambreDTO;
import com.p1.Model.Chambre;
import com.p1.Model.Foyer;
import com.p1.Repository.ChambreRepository;
import com.p1.Repository.FoyerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChambreService {

    @Autowired
    private ChambreRepository chambreRepository;
    @Autowired
    private FoyerRepository foyerRepository;

    //////////////////////////////// Done/////////////////////////////////////////
    public Chambre addChambre(Chambre chambre, Long foyerId) {
        Optional<Foyer> foyer = foyerRepository.findById(foyerId);
        if (foyer.isPresent()) {
            chambre.setFoyer(foyer.get());
            return chambreRepository.save(chambre);
        } else {
            throw new IllegalArgumentException("Le foyer n'existe pas");
        }
    }

    public Chambre updateChambre(Long id, Chambre updatedChambre) {
        Chambre existingChambre = getChambrebyId(id);

        if (existingChambre != null) {
            // Update only the fields that are provided in the updatedChambre object

            // Only update Description if it's provided in the request
            if (updatedChambre.getDescription() != null) {
                existingChambre.setDescription(updatedChambre.getDescription());
            }

            // Only update Type if it's provided in the request
            if (updatedChambre.getType() != null) {
                existingChambre.setType(updatedChambre.getType());
            }

            // Only update Availble if it's provided in the request
            if (updatedChambre.isAvailble() != null) {
                existingChambre.setAvailble(updatedChambre.isAvailble());
            }

            // Only update Price if it's provided in the request
            if (updatedChambre.getPrice() != null) {
                existingChambre.setPrice(updatedChambre.getPrice());
            }

            // Only update Foyer if it's provided in the request
            if (updatedChambre.getFoyer() != null) {
                existingChambre.setFoyer(updatedChambre.getFoyer());
            }

            // Save the partially updated chambre
            return chambreRepository.save(existingChambre);
        } else {
            throw new IllegalArgumentException("Chambre not found");
        }
    }

    /////////////////////////////////// Done//////////////////////////////////////////
    ///

    public List<Chambre> getChambresByFoyerId(Long foyerId) {
        return chambreRepository.findByFoyerId(foyerId);
    }

    public List<ChambreDTO> getAllChambres() {
        List<Chambre> chambres = chambreRepository.findAll();
        return chambres.stream().map(chambre -> {
            ChambreDTO dto = new ChambreDTO();
            dto.setId(chambre.getId());
            dto.setName(chambre.getName());
            dto.setDescription(chambre.getDescription());
            dto.setType(chambre.getType().toString()); // Convert enum to String
            dto.setAvailble(chambre.isAvailble());
            dto.setPrice(chambre.getPrice());
            // Check if foyer is present and set both foyerId and foyerNom
            if (chambre.getFoyer() != null) {
                dto.setFoyerId(chambre.getFoyer().getId());
                dto.setFoyerNom(chambre.getFoyer().getNom());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    /////////////////////////////////////// Done///////////////////////////////////////////////
    public Chambre getChambrebyId(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));
    }

    // -------------------------------------Done-------------------------------------------
    public String deleteChambre(Long id) {
        Chambre chambre = chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));
        chambreRepository.delete(chambre);
        return "Data Deleted Successfully";
    }
}
