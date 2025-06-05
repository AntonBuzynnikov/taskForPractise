package ru.buzynnikov.spring_object_mapper.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link OrderController}
 */
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void getOrderById() throws Exception {
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.orderId").exists()))
                .andDo(print());
    }

    @Test
    public void createOrder() throws Exception {
        String request = """
                {
                    "customerId": 1,
                    "shippingAddress": "ул. Примерная, д. 10, кв. 25, г. Москва, 123456",
                    "productIds": [1, 3]
                }""";

        mockMvc.perform(post("/orders")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.customer.customerId").value("1"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.shippingAddress").value("ул. Примерная, д. 10, кв. 25, г. Москва, 123456"))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0].productId").value("1"))
                .andExpect(jsonPath("$.products[1].productId").value("3"))
                .andExpect(jsonPath("$.totalPrice").exists())
                .andDo(print());
    }
}
