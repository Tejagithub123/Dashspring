package com.p1;

import com.p1.Model.Personnel;
import com.p1.Model.Foyer;
import com.p1.Repository.PersonnelRepository;
import com.p1.Repository.FoyerRepository;
import com.p1.Service.PersonnelService;
import com.p1.Service.FoyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonnelIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private FoyerService foyerService;

    private Long foyerId;

    @BeforeEach
    public void setup() {

        Foyer foyer = new Foyer();
        foyer.setNom("Test Foyer");
        foyerId = foyerRepository.save(foyer).getId();
    }

    @Test
    public void testAddPersonnel() throws Exception {
        Personnel personnel = new Personnel();
        personnel.setNom("John");
        personnel.setPrenom("Doe");
        personnel.setEmail("john.doe@example.com");

        mockMvc.perform(post("/admin/personnels")
                        .param("foyerId", foyerId.toString())
                        .contentType("application/json")
                        .content("{\"nom\": \"John\", \"prenom\": \"Doe\", \"email\": \"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetAllPersonnels() throws Exception {
        mockMvc.perform(get("/admin/Allpersonnels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").exists())
                .andExpect(jsonPath("$[0].prenom").exists());
    }

    @Test
    public void testGetPersonnel() throws Exception {

        Personnel personnel = new Personnel();
        personnel.setNom("Jane");
        personnel.setPrenom("Smith");
        personnel.setEmail("jane.smith@example.com");
        personnel = personnelService.addPersonnel(personnel, foyerId);

        mockMvc.perform(get("/admin/personnels/{id}", personnel.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Jane"))
                .andExpect(jsonPath("$.prenom").value("Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
    }

    @Test
    public void testDeletePersonnel() throws Exception {

        Personnel personnel = new Personnel();
        personnel.setNom("Mark");
        personnel.setPrenom("Twain");
        personnel.setEmail("mark.twain@example.com");
        personnel = personnelService.addPersonnel(personnel, foyerId);

        mockMvc.perform(delete("/admin/personnels/{id}", personnel.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAssignFoyerToPersonnel() throws Exception {
       
        Personnel personnel = new Personnel();
        personnel.setNom("Albert");
        personnel.setPrenom("Einstein");
        personnel.setEmail("albert.einstein@example.com");
        personnel = personnelService.addPersonnel(personnel, foyerId);

        Foyer newFoyer = new Foyer();
        newFoyer.setNom("New Foyer");
        foyerRepository.save(newFoyer);

        mockMvc.perform(put("/admin/personnels/{personnelId}/foyer", personnel.getId())
                        .param("foyerId", newFoyer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foyer.name").value("New Foyer"));
    }
}
