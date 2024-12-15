package com.p1.Controllers;

import com.p1.config.JwtUtil;
import com.p1.Model.Utilisateur;
import com.p1.Service.CustomUserDetailsService;
import com.p1.Service.UtilisateurService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    private final UtilisateurService utilisateurService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService, UtilisateurService utilisateurService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email, @RequestParam String mdp) {
        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, mdp));

        // Retrieve user details from the repository
        Utilisateur utilisateur = (Utilisateur) userDetailsService.loadUserByUsername(email);

        // Generate the JWT token
        String token = jwtUtil.generateToken(utilisateur.getEmail(), utilisateur.getRole().name());

        // Return the token in a structured JSON response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @PostMapping("/register")
    public Utilisateur register(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.saveUtilisateur(utilisateur);
    }
}
