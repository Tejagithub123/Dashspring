package com.p1;

import com.p1.Model.Utilisateur;
import com.p1.Repository.UtilisateurRepository;
import com.p1.Service.UtilisateurService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setMdp("password123");
    }

    @Test
    void testSaveUtilisateur() {
        
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);

        
        assertNotNull(savedUtilisateur);
        assertEquals("encodedPassword", savedUtilisateur.getMdp());
        verify(passwordEncoder).encode("password123");
        verify(utilisateurRepository).save(utilisateur);
    }
    @Test
    void testFindByEmail() {
        
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        
        Optional<Utilisateur> result = utilisateurService.findByEmail("test@example.com");

       
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(utilisateurRepository).findByEmail("test@example.com");
    }

    @Test
    void testFindByEmailNotFound() {
       
        when(utilisateurRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        
        Optional<Utilisateur> result = utilisateurService.findByEmail("nonexistent@example.com");

        
        assertFalse(result.isPresent());
        verify(utilisateurRepository).findByEmail("nonexistent@example.com");
    }
    @Test
    void testSaveUtilisateurWithDuplicateEmail() {
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        Utilisateur newUtilisateur = new Utilisateur();
        newUtilisateur.setEmail("test@example.com");
        newUtilisateur.setMdp("newPassword");

        assertThrows(IllegalArgumentException.class, () -> utilisateurService.saveUtilisateur(newUtilisateur));
    }


}
