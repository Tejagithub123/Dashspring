package com.p1;

import com.p1.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setName("John");
        user.setEmail("john.doe@example.com");

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
    
    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.name").value("John"))  
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));  }
    @Test
    public void testEditUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("John Updated");
        updatedUser.setEmail("john.updated@example.com");

        mockMvc.perform(post("/editsave")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.name").value("John Updated"))  
                .andExpect(jsonPath("$.email").value("john.updated@example.com")); 
    }
    @Test
    public void testDeleteUser() throws Exception {

        mockMvc.perform(get("/deleteuser/1"))
                .andExpect(status().isOk())  
                .andExpect(content().string("Data Deleted Succefully"));  
    }
    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$[0].name").value("John")) 
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com")); 
    }

}
