package ru.buzynnikov.spring_data_jdbc.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.buzynnikov.spring_data_jdbc.exceptions.BadRequestException;
import ru.buzynnikov.spring_data_jdbc.exceptions.BookNotFoundException;
import ru.buzynnikov.spring_data_jdbc.models.Book;
import ru.buzynnikov.spring_data_jdbc.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void save_ValidBook_ReturnsSavedBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("Valid Title");
        book.setAuthor("Valid Author");
        book.setPublicationYear(2000);

        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book savedBook = bookService.save(book);

        // Assert
        assertNotNull(savedBook);
        assertEquals(book.getTitle(), savedBook.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void save_InvalidBook_ThrowsBadRequestException() {
        // Arrange
        Book invalidBook = new Book();
        invalidBook.setTitle("A"); // Too short title

        // Act & Assert
        assertThrows(BadRequestException.class, () -> bookService.save(invalidBook));
        verifyNoInteractions(bookRepository);
    }

    @Test
    void findById_ExistingId_ReturnsBook() {
        // Arrange
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        Book foundBook = bookService.findById(id);

        // Assert
        assertNotNull(foundBook);
        assertEquals(id, foundBook.getId());
    }

    @Test
    void findById_NonExistingId_ThrowsBookNotFoundException() {
        // Arrange
        Long id = 999L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.findById(id));
    }

    @Test
    void findAll_ReturnsAllBooks() {
        // Arrange
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        // Act
        List<Book> books = bookService.findAll();

        // Assert
        assertEquals(2, books.size());
    }

    @Test
    void update_ValidBook_UpdatesBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");
        book.setPublicationYear(2020);


        // Act
        bookService.update(book);

        // Assert
        verify(bookRepository).update(book);
    }


    @Test
    void deleteById_ExistingId_DeletesBook() {
        // Arrange
        Long id = 1L;

        // Act
        bookService.deleteById(id);

        // Assert
        verify(bookRepository).deleteById(id);
    }


    @Test
    void validate_BookWithFuturePublicationYear_ThrowsBadRequestException() {
        // Arrange
        Book book = new Book();
        book.setTitle("Valid Title");
        book.setAuthor("Valid Author");
        book.setPublicationYear(LocalDate.now().getYear() + 1); // Будущий год

        // Act & Assert
        assertThrows(BadRequestException.class, () -> bookService.save(book));
    }
}