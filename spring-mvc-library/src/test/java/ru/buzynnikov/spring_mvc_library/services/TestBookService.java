package ru.buzynnikov.spring_mvc_library.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import ru.buzynnikov.spring_mvc_library.dto.CreateBookDto;
import ru.buzynnikov.spring_mvc_library.dto.UpdateBookDto;
import ru.buzynnikov.spring_mvc_library.models.Author;
import ru.buzynnikov.spring_mvc_library.models.Book;
import ru.buzynnikov.spring_mvc_library.repositories.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestBookService {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private DefaultBookService defaultBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        CreateBookDto createDto = new CreateBookDto("Война миров", 1L);
        Author author = new Author();
        author.setId(1L);
        author.setFullName("Герберт Уэллс");

        Book book = new Book();
        book.setTitle("Война миров");
        book.setAuthor(author);
        book.setId(1L);


        when(authorService.getById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = defaultBookService.createBook(createDto);
        assertNotNull(createdBook);
        assertEquals(book, createdBook);
    }
    @Test
    void testGetAllBooks() {
        Author author = new Author();
        author.setId(1L);
        author.setFullName("Герберт Уэллс");
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Война миров");
        book1.setAuthor(author);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Машина времени");
        book2.setAuthor(author);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        List<Book> mockBooks = Arrays.asList(book1, book2);
        Page<Book> mockPage = new PageImpl<>(mockBooks, pageable, mockBooks.size());

        when(bookRepository.findAll(pageable)).thenReturn(mockPage);


        Page<Book> result = defaultBookService.getBooks(pageable);


        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("Война миров", result.getContent().get(0).getTitle());
    }
    @Test
    void testUpdateById() {
        Long bookId = 1L;
        Long authorId = 2L;
        UpdateBookDto updateDto = new UpdateBookDto("New Title", authorId);

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Title");
        existingBook.setAuthor(null);
        Author author = new Author();
        author.setId(authorId);
        author.setFullName("Author Name");

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(existingBook));
        when(authorService.getById(authorId)).thenReturn(author);
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        defaultBookService.updateBook(bookId, updateDto);

        assertEquals("New Title", existingBook.getTitle());
        assertEquals(author, existingBook.getAuthor());

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorService, times(1)).getById(authorId);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testGetById() {

        Author expectedAuthor = new Author();
        expectedAuthor.setId(1L);
        expectedAuthor.setFullName("Author Name");
        Long bookId = 1L;
        Book expectedBook = new Book();
        expectedBook.setId(bookId);
        expectedBook.setTitle("Test Title");
        expectedBook.setAuthor(expectedAuthor);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        Book result = defaultBookService.getBookById(bookId);

        
        assertNotNull(result);
        assertEquals(expectedBook, result);
        verify(bookRepository, times(1)).findById(bookId);
    }

}
