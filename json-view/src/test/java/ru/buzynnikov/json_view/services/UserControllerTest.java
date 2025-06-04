package ru.buzynnikov.json_view.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllUsersWithoutOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].orders").doesNotHaveJsonPath());
    }
    @Test
    void testGetUserByIdWithAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userOrders").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userOrders[0].items").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userOrders[0].totalPrice").value(35000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userOrders[0].status").value("OPEN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userOrders[0].items[0].product.name").value("Телефон"));
    }

    @Test
    @Transactional
    void createNewUser() throws Exception {

        String requestBody = """
                                {
                                    "name": "Alex",
                                    "email": "alex@example.com"
                                }
                             """;

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alex"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("alex@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userOrders").doesNotExist());
    }
    @Test
    @Transactional
    void updateUser() throws Exception {
        String requestBody = """
                                {
                                    "name": "Alex",
                                    "email": "alex@example.com"
                                }
                             """;
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alex"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("alex@example.com"));
    }
    @Test
    @Transactional
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
