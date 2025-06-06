package ru.buzynnikov.spring_data_jdbc.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.spring_data_jdbc.models.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BookRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private BookRepositoryImpl bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepositoryImpl(jdbcTemplate);
        // Инициализация тестовой БД
        jdbcTemplate.execute("DROP TABLE IF EXISTS book");
        jdbcTemplate.execute("CREATE TABLE book (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255), " +
                "publication_year INT)");
    }

    @Test
    @Transactional
    void save_ShouldSaveBookAndReturnWithGeneratedId() {
        // Arrange
        Book newBook = new Book();
        newBook.setTitle("Test Book");
        newBook.setAuthor("Test Author");
        newBook.setPublicationYear(2023);

        // Act
        Book savedBook = bookRepository.save(newBook);

        // Assert
        assertNotNull(savedBook.getId());
        assertEquals(newBook.getTitle(), savedBook.getTitle());
        assertEquals(newBook.getAuthor(), savedBook.getAuthor());
        assertEquals(newBook.getPublicationYear(), savedBook.getPublicationYear());

        // Проверка, что книга действительно сохранена в БД
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals(savedBook.getId(), foundBook.get().getId());
    }

    @Test
    void findById_ShouldReturnBookWhenExists() {
        // Arrange
        jdbcTemplate.update(
                "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                "Existing Book", "Existing Author", 2020);

        // Act
        Optional<Book> foundBook = bookRepository.findById(1L);

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals(1L, foundBook.get().getId());
        assertEquals("Existing Book", foundBook.get().getTitle());
    }

    @Test
    void findById_ShouldReturnEmptyOptionalWhenNotExists() {
        // Act
        Optional<Book> foundBook = bookRepository.findById(999L);

        // Assert
        assertFalse(foundBook.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        // Arrange
        jdbcTemplate.update(
                "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                "Book 1", "Author 1", 2000);
        jdbcTemplate.update(
                "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                "Book 2", "Author 2", 2001);

        // Act
        List<Book> books = bookRepository.findAll();

        // Assert
        assertEquals(2, books.size());
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Book 1", "Book 2");
    }

    @Test
    @Transactional
    void update_ShouldUpdateExistingBook() {
        // Arrange
        jdbcTemplate.update(
                "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                "Old Title", "Old Author", 2000);

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("New Title");
        updatedBook.setAuthor("New Author");
        updatedBook.setPublicationYear(2023);

        // Act
        bookRepository.update(updatedBook);

        // Assert
        Optional<Book> bookAfterUpdate = bookRepository.findById(1L);
        assertTrue(bookAfterUpdate.isPresent());
        assertEquals("New Title", bookAfterUpdate.get().getTitle());
        assertEquals("New Author", bookAfterUpdate.get().getAuthor());
        assertEquals(2023, bookAfterUpdate.get().getPublicationYear());
    }

    @Test
    @Transactional
    void deleteById_ShouldRemoveBook() {
        // Arrange
        jdbcTemplate.update(
                "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                "To Delete", "Author", 2000);

        // Act
        bookRepository.deleteById(1L);

        // Assert
        Optional<Book> deletedBook = bookRepository.findById(1L);
        assertFalse(deletedBook.isPresent());
    }

    @Test
    void existById_ShouldNotThrowWhenBookExists() {
        // Arrange
        jdbcTemplate.update(
                "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                "Existing", "Author", 2000);

        // Act & Assert
        assertDoesNotThrow(() -> bookRepository.existById(1L));
    }

    @Test
    void existById_ShouldThrowWhenBookNotExists() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookRepository.existById(999L),
                "Expected existById() to throw, but it didn't");
    }

}