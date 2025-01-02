package com.p1;

import com.p1.Model.Utilisateur;
import com.p1.Repository.UtilisateurRepository;
import com.p1.Service.CustomUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

public class CustomUserDetailsServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private Utilisateur utilisateur;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Créer un utilisateur mock
        utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setMdp("password");
    }

    @Test
    public void testLoadUserByUsername_Success() {
        // Given
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        // When
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

        // Then
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Given
        when(utilisateurRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonexistent@example.com");
        });

        assertEquals("User not found with email: nonexistent@example.com", thrown.getMessage());
    }
}
