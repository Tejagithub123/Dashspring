package com.p1.Service;

import com.p1.Model.AgentMaintenance;
import com.p1.Repository.AgentMaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.p1.Model.Utilisateur;
import java.util.List;

@Service
public class AgentMaintenanceService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AgentMaintenanceRepository agentMaintenanceRepository;

    public AgentMaintenance addAgent(AgentMaintenance agent) {

        String plainPassword = agent.getMdp();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        agent.setMdp(hashedPassword);

        agent.setRole(Utilisateur.Role.ROLE_AGENT);

        AgentMaintenance savedAgent = agentMaintenanceRepository.save(agent);

        String emailSubject = "Bienvenue sur GestionRésidence!";
        String emailBody = String.format(
                "Bonjour %s,\n\nVotre compte a été créé avec succès sur la plateforme GestionRésidence.\n\nVoici vos identifiants de connexion :\nEmail: %s\nMot de passe: %s\n\nMerci de vous connecter à la plateforme.\n\nCordialement,\nL'équipe GestionRésidence",
                agent.getNom(), agent.getEmail(), plainPassword);

        emailService.sendEmail(agent.getEmail(), emailSubject, emailBody);

        return savedAgent;
    }

    public List<AgentMaintenance> getAllAgents() {
        return agentMaintenanceRepository.findAll();
    }

    public AgentMaintenance getAgentById(Long id) {
        return agentMaintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agent not found with ID: " + id));
    }

    public AgentMaintenance updateAgent(Long id, AgentMaintenance updatedAgent) {
        AgentMaintenance agent = getAgentById(id);
        if (updatedAgent.getNom() != null)
            agent.setNom(updatedAgent.getNom());
        if (updatedAgent.getPrenom() != null)
            agent.setPrenom(updatedAgent.getPrenom());
        if (updatedAgent.getSpecialite() != null)
            agent.setSpecialite(updatedAgent.getSpecialite());
        if (updatedAgent.getCin() != null)
            agent.setCin(updatedAgent.getCin());

        if (updatedAgent.getEmail() != null)
            agent.setEmail(updatedAgent.getEmail());

        if (updatedAgent.getDateNaissance() != null)
            agent.setDateNaissance(updatedAgent.getDateNaissance());

        return agentMaintenanceRepository.save(agent);
    }

    public void deleteAgent(Long id) {
        agentMaintenanceRepository.deleteById(id);
    }
}
