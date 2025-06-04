package ru.buzynnikov.spring_mvc_library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.buzynnikov.spring_mvc_library.dto.CreateBookDto;
import ru.buzynnikov.spring_mvc_library.dto.UpdateBookDto;
import ru.buzynnikov.spring_mvc_library.models.Book;

public interface BookService {

    Page<Book> getBooks(Pageable pageable);

    Book getBookById(Long id);

    Book createBook(CreateBookDto createBookDto);

    void updateBook(Long id, UpdateBookDto updateBookDto);

    void deleteBookById(Long id);
}
