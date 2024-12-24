package com.p1;

import com.p1.Model.Utilisateur;
import com.p1.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class UserApplication {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		System.out.println("run it now spring");
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {

			String adminEmail = "teja@example.com";
			if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {

				Utilisateur admin = new Utilisateur();
				admin.setCin("12345674");
				admin.setNom("teja");
				admin.setPrenom("Doe");
				admin.setEmail(adminEmail);
				admin.setDateNaissance(new Date(2000, 3, 1));

				admin.setMdp(passwordEncoder.encode("teja"));

				admin.setRole(Utilisateur.Role.ADMIN);
				utilisateurRepository.save(admin);
				System.out.println("Admin user created successfully!");
			} else {
				System.out.println("Admin user with email " + adminEmail + " already exists.");
			}
		};
	}
}
