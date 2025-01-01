package com.p1.Service;

import com.p1.Model.Chambre;
import com.p1.Model.Plainte;
import com.p1.Repository.ChambreRepository;
import com.p1.Repository.PlainteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlainteService {

    @Autowired
    private PlainteRepository plainteRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    public Plainte addPlainte(Plainte plainte) {

        plainte.setCloturee(false);

        if (plainte.getChambre() != null) {

            Chambre chambre = chambreRepository.findById(plainte.getChambre().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Chambre not found with ID: " + plainte.getChambre().getId()));

            chambre.setEnMaintenance(true);

            chambreRepository.save(chambre);
        }

        return plainteRepository.save(plainte);
    }

    public List<Plainte> getAllPlaintes() {
        return plainteRepository.findAll();
    }

    public Optional<Plainte> getPlainteById(Long id) {
        return plainteRepository.findById(id);
    }

    public Plainte updatePlainte(Long id, Plainte updatedPlainte) {
        Plainte existingPlainte = getPlainteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plainte not found with ID: " + id));

        if (updatedPlainte.getDate() != null) {
            existingPlainte.setDate(updatedPlainte.getDate());
        }
        existingPlainte.setCloturee(updatedPlainte.isCloturee());
        existingPlainte.setDescription(updatedPlainte.getDescription());

        return plainteRepository.save(existingPlainte);
    }

    public void deletePlainte(Long id) {

        Plainte plainte = plainteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plainte not found with ID: " + id));

        Chambre chambre = plainte.getChambre();

        plainteRepository.delete(plainte);

        if (chambre != null) {
            boolean hasOtherPlaintes = plainteRepository.existsByChambre(chambre);

            if (!hasOtherPlaintes) {
                chambre.setEnMaintenance(false);
                chambreRepository.save(chambre);
            }
        }
    }

    public Plainte cloturerPlainte(Long id) {
        Plainte existingPlainte = getPlainteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plainte not found with ID: " + id));

        existingPlainte.setCloturee(true);

        return plainteRepository.save(existingPlainte);
    }
}