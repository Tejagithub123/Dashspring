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

            if (updatedChambre.getDescription() != null) {
                existingChambre.setDescription(updatedChambre.getDescription());
            }

            if (updatedChambre.getType() != null) {
                existingChambre.setType(updatedChambre.getType());
            }

            if (updatedChambre.isAvailble() != null) {
                existingChambre.setAvailble(updatedChambre.isAvailble());
            }

            if (updatedChambre.getPrice() != null) {
                existingChambre.setPrice(updatedChambre.getPrice());
            }

            if (updatedChambre.getFoyer() != null) {
                existingChambre.setFoyer(updatedChambre.getFoyer());
            }

            return chambreRepository.save(existingChambre);
        } else {
            throw new IllegalArgumentException("Chambre not found");
        }
    }

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
            dto.setType(chambre.getType().toString());
            dto.setAvailble(chambre.isAvailble());
            dto.setPrice(chambre.getPrice());
            dto.setenMaintenance(chambre.isEnMaintenance());

            if (chambre.getFoyer() != null) {
                dto.setFoyerId(chambre.getFoyer().getId());
                dto.setFoyerNom(chambre.getFoyer().getNom());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public Chambre getChambrebyId(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));
    }

    public String deleteChambre(Long id) {
        Chambre chambre = chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));
        chambreRepository.delete(chambre);
        return "Data Deleted Successfully";
    }

    public Chambre setChambreEnMaintenance(Long id, boolean enMaintenance) {
        Chambre chambre = chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre not found"));
        chambre.setEnMaintenance(enMaintenance);
        return chambreRepository.save(chambre);
    }

    public List<Chambre> getlisteChambre() {
        return chambreRepository.findAll();
    }
}
