package ru.buzynnikov.spring_data_projections.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/employees/{0}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.fullName").exists())
                .andExpect(jsonPath("$.departmentName").isString())
                .andExpect(jsonPath("$.position").exists())
                .andDo(print());
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").doesNotExist())
                .andExpect(jsonPath("$.[0].fullName").exists())
                .andExpect(jsonPath("$.[0].departmentName").isString())
                .andExpect(jsonPath("$.[0].position").exists())
                .andDo(print());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        String request = """
                {
                  "firstName": "Иван",
                  "lastName": "Петров",
                  "position": "Разработчик",
                  "salary": 150000.00,
                  "departmentId": 1
                }""";

        mockMvc.perform(post("/employees")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(print());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        String request = """
                {
                  "firstName": "Иван",
                  "lastName": "Петров",
                  "position": "Разработчик",
                  "salary": 150000.00,
                  "departmentId": 1
                }""";

        mockMvc.perform(put("/employees/{0}", "1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        mockMvc.perform(get("/employees/{0}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Иван Петров"))
                .andDo(print());
    }

    @Test
    @Transactional
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{0}", "1"))
                .andExpect(status().isNoContent())
                .andDo(print());
        mockMvc.perform(get("/employees/{0}", "1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
