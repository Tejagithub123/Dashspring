package com.p1;

import com.p1.Service.CustomUserDetailsService;
import com.p1.Service.UtilisateurService;
import com.p1.config.JwtUtil;
import com.p1.Controllers.AuthController;
import com.p1.Model.Utilisateur;
import com.p1.Repository.UtilisateurRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class AuthControllerTest {

	@Mock
    private PasswordEncoder passwordEncoder;  // Mock PasswordEncoder

    @Mock
    private UtilisateurRepository utilisateurRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UtilisateurService utilisateurService;

    @InjectMocks
    private AuthController authController;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setRole(Utilisateur.Role.ETUDIANT);
    }

    @Test
    void testLogin() {

        when(authenticationManager.authenticate(any())).thenReturn(null); 
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(utilisateur);
        when(jwtUtil.generateToken(any(), any())).thenReturn("mockJwtToken");

  
        Map<String, String> response = authController.login("test@example.com", "password");


        assertNotNull(response);
        assertEquals("mockJwtToken", response.get("token"));
        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername("test@example.com");
        verify(jwtUtil).generateToken("test@example.com", "ETUDIANT");
    }
    @Test
    void testRegister() {
    
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("newuser@example.com");
        utilisateur.setMdp("newPassword123");


        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedPassword123");
        when(utilisateurRepository.save(utilisateur)).thenReturn(utilisateur);


        Utilisateur registeredUtilisateur = authController.register(utilisateur);

        
        assertNotNull(registeredUtilisateur);
        assertEquals("encodedPassword123", registeredUtilisateur.getMdp());
        verify(passwordEncoder).encode("newPassword123");
        verify(utilisateurRepository).save(utilisateur);
    }

}
