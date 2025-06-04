package ru.buzynnikov.json_view.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUserNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/100"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("User not found by id: 100"));
    }
    @Test
    void testNotValidNameAndEmailUserCreateException() throws Exception {

        String requestBody = """
                             {
                                "name": "A",
                                "email": "A"
                             }
                             """;

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.detail")
                                .value("Длина имени пользователя должна быть от 3 до 255 символов, Некорректный формат email"));
    }

}
