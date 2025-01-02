package com.p1;


import com.p1.Model.Personnel;
import com.p1.Controllers.AdminController;
import com.p1.Model.Foyer;
import com.p1.Service.PersonnelService;
import com.p1.Service.FoyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonnelService personnelService;

    @Mock
    private FoyerService foyerService;

    @InjectMocks
    private AdminController adminController;

    private Personnel personnel;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        
        foyer = new Foyer();
        foyer.setId(1L);
        foyer.setNom("Foyer Test");

        personnel = new Personnel();
        personnel.setId(1L);
        personnel.setNom("John Doe");
    }

    @Test
    void testAddPersonnel() throws Exception {
        // Arrange
        when(personnelService.addPersonnel(any(Personnel.class), eq(1L))).thenReturn(personnel);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/personnels")
                .param("foyerId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\": \"John Doe\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("John Doe"));
    }

    @Test
    void testGetAllPersonnels() throws Exception {
        // Arrange
        when(personnelService.getAllPersonnels()).thenReturn(Arrays.asList(personnel));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/Allpersonnels"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("John Doe"));
    }

    @Test
    void testGetPersonnel() throws Exception {
        // Arrange
        when(personnelService.getPersonnel(1L)).thenReturn(personnel);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/personnels/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("John Doe"));
    }

    @Test
    void testAssignFoyerToPersonnel() throws Exception {
        // Arrange
        when(personnelService.assignFoyerToPersonnel(1L, 1L)).thenReturn(personnel);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/personnels/{personnelId}/foyer", 1L)
                .param("foyerId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("John Doe"));
    }

    @Test
    void testUpdatePersonnel() throws Exception {
        // Arrange
        Personnel updatedPersonnel = new Personnel();
        updatedPersonnel.setNom("John Updated");
        when(personnelService.updatePersonnel(1L, updatedPersonnel)).thenReturn(updatedPersonnel);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/personnels/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\": \"John Updated\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("John Updated"));
    }

    @Test
    void testDeletePersonnel() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/personnels/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(personnelService, times(1)).deletePersonnel(1L);
    }

    @Test
    void testAddFoyer() throws Exception {
        // Arrange
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/foyers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\": \"Foyer Test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Foyer Test"));
    }

    @Test
    void testGetAllFoyers() throws Exception {
        // Arrange
        when(foyerService.getAllFoyers()).thenReturn(Arrays.asList(foyer));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/foyers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Foyer Test"));
    }

    @Test
    void testGetFoyer() throws Exception {
        // Arrange
        when(foyerService.getFoyerById(1L)).thenReturn(Optional.of(foyer));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/foyers/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Foyer Test"));
    }

    @Test
    void testUpdateFoyer() throws Exception {
        // Arrange
        Foyer updatedFoyer = new Foyer();
        updatedFoyer.setNom("Updated Foyer");
        when(foyerService.updateFoyer(1L, updatedFoyer)).thenReturn(updatedFoyer);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/foyers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\": \"Updated Foyer\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Updated Foyer"));
    }

    @Test
    void testDeleteFoyer() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/foyers/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(foyerService, times(1)).deleteFoyer(1L);
    }
}
