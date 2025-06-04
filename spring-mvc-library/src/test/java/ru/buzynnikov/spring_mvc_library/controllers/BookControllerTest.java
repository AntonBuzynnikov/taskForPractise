package ru.buzynnikov.spring_mvc_library.controllers;

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



    @Test
    public void getAllBooks() throws Exception {
        mockMvc.perform(get("/books")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("sort", "title,asc"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createBook() throws Exception {
        String createBookDto = """
                {
                    "name": "Война миров",
                    "authorId": 1
                }""";

        mockMvc.perform(post("/books")
                        .content(createBookDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void updateBook() throws Exception {
        String updateBookDto = """
                {
                    "title": "Война",
                    "authorId": 1
                }""";

        mockMvc.perform(put("/books/1")
                        .content(updateBookDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void deleteBook() throws Exception {
        mockMvc.perform(delete("/books/10"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void getBookById() throws Exception {
        mockMvc.perform(get("/books/9"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
