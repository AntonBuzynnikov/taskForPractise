package ru.buzynnikov.spring_data_jdbc.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.spring_data_jdbc.models.Book;
import ru.buzynnikov.spring_data_jdbc.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.findAll());
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book, UriComponentsBuilder builder) {
        Book createdBook = bookService.save(book);
        return ResponseEntity.created(builder.path("/book/{id}").buildAndExpand(createdBook.getId()).toUri()).body(createdBook);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        bookService.update(book);
        return ResponseEntity.noContent().build();
    }
}
