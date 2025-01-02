package com.p1;



import com.p1.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddUser() throws Exception {
        // Create a sample user to add
        User user = new User("John Doe", "john@example.com");

      
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

       
        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO user(name, email) VALUES(?, ?)"), eq("John Doe"), eq("john@example.com"));
    }

    

    @Test
    public void testDeleteUser() throws Exception {

        mockMvc.perform(get("/deleteuser/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Data Deleted Successfully"));

        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM user WHERE id = ?"), eq(1L));
    }
}
