package com.p1;


import com.p1.Model.Foyer;
import com.p1.Repository.FoyerRepository;
import com.p1.Service.FoyerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FoyerServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerService foyerService;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setId(1L);
        foyer.setNom("Foyer Test");
    }

    @Test
    void testAddFoyer() {
        // Arrange
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer Test", result.getNom());
        verify(foyerRepository, times(1)).save(any(Foyer.class));
    }

    @Test
    void testGetAllFoyers() {
        // Arrange
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));

        // Act
        List<Foyer> foyers = foyerService.getAllFoyers();

        // Assert
        assertNotNull(foyers);
        assertFalse(foyers.isEmpty());
        assertEquals(1, foyers.size());
    }

    @Test
    void testGetFoyerById_Found() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Optional<Foyer> result = foyerService.getFoyerById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Foyer Test", result.get().getNom());
    }

    @Test
    void testGetFoyerById_NotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Foyer> result = foyerService.getFoyerById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateFoyer() {
        // Arrange
        Foyer updatedFoyer = new Foyer();
        updatedFoyer.setNom("Updated Foyer");
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(foyerRepository.save(any(Foyer.class))).thenReturn(updatedFoyer);

        // Act
        Foyer result = foyerService.updateFoyer(1L, updatedFoyer);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Foyer", result.getNom());
    }

    @Test
    void testUpdateFoyer_NotFound() {
        // Arrange
        Foyer updatedFoyer = new Foyer();
        updatedFoyer.setNom("Updated Foyer");
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> foyerService.updateFoyer(1L, updatedFoyer));
    }

    @Test
    void testDeleteFoyer() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        foyerService.deleteFoyer(1L);

        // Assert
        verify(foyerRepository, times(1)).delete(foyer);
    }

    @Test
    void testDeleteFoyer_NotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> foyerService.deleteFoyer(1L));
    }
}
