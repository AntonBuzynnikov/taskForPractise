package ru.buzynnikov.spring_object_mapper.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link ProductController}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].productId").value("1"))
                .andDo(print());
    }

    @Test
    public void getProductById() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("1"))
                .andDo(print());
    }

    @Test
    @Transactional
    public void addProduct() throws Exception {
        String json = """
                {
                    "name": "test",
                    "description": "test description",
                    "price": 100,
                    "quantityStock": 1
                }""";

        mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andDo(print());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        String json = """
                {
                    "name": "test",
                    "description": "test description",
                    "price": 100,
                    "quantityStock": 1
                }""";

        mockMvc.perform(put("/products/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }
}
