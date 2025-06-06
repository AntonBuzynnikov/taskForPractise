package ru.buzynnikov.spring_data_jdbc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link BookController}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void getBookById() throws Exception {
        mockMvc.perform(get("/book/{0}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andDo(print());
    }

    @Test
    public void getAllBooks() throws Exception {
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andDo(print());
    }

    @Test
    public void createBook() throws Exception {
        String book = """
                {
                  "title": "The Lord of the Rings",
                  "author": "J.R.R. Tolkien",
                  "publicationYear": 1954
                }""";

        mockMvc.perform(post("/book")
                        .content(book)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.publicationYear").value(1954))
                .andDo(print());
    }

    @Test
    public void deleteBook() throws Exception {
        mockMvc.perform(delete("/book/{0}", "15"))
                .andExpect(status().isNoContent())
                .andDo(print());
        mockMvc.perform(get("/book/{0}", "15"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBook() throws Exception {
        String book = """
                {
                  "id": 10,
                  "title": "Название книги",
                  "author": "Автор книги",
                  "publicationYear": 2023
                }""";

        mockMvc.perform(put("/book")
                        .content(book)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        mockMvc.perform(get("/book/{0}", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("10"))
                .andExpect(jsonPath("$.title").value("Название книги"))
                .andExpect(jsonPath("$.author").value("Автор книги"))
                .andExpect(jsonPath("$.publicationYear").value(2023));
    }
}
