package ru.buzynnikov.spring_mvc_library.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionTestController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createBookExceptionTest() throws Exception {
        String createBookDto = """
                {
                    "name": "Во",
                    "authorId": ""
                }""";

        mockMvc.perform(post("/books")
                        .content(createBookDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail")
                        .value("ID автора не должен быть пустым, Название должно быть от 3 символов до 255 символов"))
                .andDo(print());
    }
    @Test
    public void updateBookExceptionTest() throws Exception {
        String updateBookDto = """
                {
                    "title": "Во",
                    "authorId": ""
                }""";

        mockMvc.perform(put("/books/1")
                        .content(updateBookDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail")
                        .value("ID автора не должен быть пустым, Название должно быть от 3 символов до 255 символов"))
                .andDo(print());
    }
    @Test
    public void getBookByIdExceptionTest() throws Exception {
        mockMvc.perform(get("/books/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail")
                        .value("Книга с id = 100 не найдена"))
                .andDo(print());
    }
    @Test
    public void getAllBooksExceptionTest() throws Exception {
        mockMvc.perform(get("/books")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("sort", "title,as"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void testAllExceptionsTest() throws Exception {
        mockMvc.perform(get("/test/all"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
    @Test
    public void testUncorrectedFormatJsonTest() throws Exception {
        String updateBookDto = """
                {
                    "title": "Во",
                    "authorId": "",
                }""";

        mockMvc.perform(put("/books/1")
                        .content(updateBookDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}
