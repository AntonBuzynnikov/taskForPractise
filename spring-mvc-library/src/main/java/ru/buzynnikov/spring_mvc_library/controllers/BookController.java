package ru.buzynnikov.spring_mvc_library.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.spring_mvc_library.dto.CreateBookDto;
import ru.buzynnikov.spring_mvc_library.dto.UpdateBookDto;
import ru.buzynnikov.spring_mvc_library.models.Book;
import ru.buzynnikov.spring_mvc_library.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooks(pageable));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid CreateBookDto createBookDto, UriComponentsBuilder uriBuilder) {
        Book book = bookService.createBook(createBookDto);
        return ResponseEntity.created(uriBuilder.path("/books/{id}").build(book.getId())).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@RequestBody @Valid UpdateBookDto updateBookDto, @PathVariable Long id) {
        bookService.updateBook(id, updateBookDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}

