package com.p1;


import com.p1.Model.Personnel;
import com.p1.Model.Foyer;
import com.p1.Repository.PersonnelRepository;
import com.p1.Service.PersonnelService;
import com.p1.Repository.FoyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

public class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private PersonnelService personnelService;

    private Personnel personnel;
    private Foyer foyer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Créer des objets mock
        foyer = new Foyer();
        foyer.setId(1L);

        personnel = new Personnel();
        personnel.setId(1L);
        personnel.setNom("Dupont");
        personnel.setPrenom("Jean");
        personnel.setEmail("jean.dupont@example.com");
        personnel.setFoyer(foyer);
    }

    @Test
    public void testAddPersonnel_Success() {
        // Given
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel);

        // When
        Personnel createdPersonnel = personnelService.addPersonnel(personnel, 1L);

        // Then
        assertNotNull(createdPersonnel);
        assertEquals("Dupont", createdPersonnel.getNom());
        verify(personnelRepository, times(1)).save(personnel);
    }

    @Test
    public void testAddPersonnel_FoyerNotFound() {
        // Given
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            personnelService.addPersonnel(personnel, 1L);
        });

        assertEquals("Le foyer n'existe pas", thrown.getMessage());
        verify(personnelRepository, never()).save(any());
    }

    @Test
    public void testGetAllPersonnels() {
        // Given
        when(personnelRepository.findAll()).thenReturn(List.of(personnel));

        // When
        List<Personnel> personnels = personnelService.getAllPersonnels();

        // Then
        assertFalse(personnels.isEmpty());
        assertEquals(1, personnels.size());
    }

    @Test
    public void testAssignFoyerToPersonnel_Success() {
        // Given
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel));
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel);

        // When
        Personnel updatedPersonnel = personnelService.assignFoyerToPersonnel(1L, 1L);

        // Then
        assertNotNull(updatedPersonnel);
        assertEquals(foyer, updatedPersonnel.getFoyer());
        verify(personnelRepository, times(1)).save(updatedPersonnel);
    }

    @Test
    public void testAssignFoyerToPersonnel_FoyerNotFound() {
        // Given
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel));
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            personnelService.assignFoyerToPersonnel(1L, 1L);
        });

        assertEquals("Personnel ou foyer non trouvés", thrown.getMessage());
        verify(personnelRepository, never()).save(any());
    }

    @Test
    public void testUpdatePersonnel_Success() {
        // Given
        Personnel updatedPersonnel = new Personnel();
        updatedPersonnel.setNom("Doe");
        updatedPersonnel.setPrenom("John");

        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel));
        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel);

        // When
        Personnel updated = personnelService.updatePersonnel(1L, updatedPersonnel);

        // Then
        assertNotNull(updated);
        assertEquals("Doe", updated.getNom());
        assertEquals("John", updated.getPrenom());
    }

    @Test
    public void testUpdatePersonnel_PersonnelNotFound() {
        // Given
        Personnel updatedPersonnel = new Personnel();
        updatedPersonnel.setNom("Doe");

        when(personnelRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            personnelService.updatePersonnel(1L, updatedPersonnel);
        });

        assertEquals("Personnel non trouvé", thrown.getMessage());
    }

    @Test
    public void testDeletePersonnel_Success() {
        // Given
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel));

        // When
        personnelService.deletePersonnel(1L);

        // Then
        verify(personnelRepository, times(1)).delete(personnel);
    }

    @Test
    public void testDeletePersonnel_NotFound() {
        // Given
        when(personnelRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            personnelService.deletePersonnel(1L);
        });

        assertEquals("Personnel non trouvé", thrown.getMessage());
        verify(personnelRepository, never()).delete(any());
    }
}

