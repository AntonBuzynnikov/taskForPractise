package ru.buzynnikov.spring_data_jdbc.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_data_jdbc.exceptions.BadRequestException;
import ru.buzynnikov.spring_data_jdbc.exceptions.BookNotFoundException;
import ru.buzynnikov.spring_data_jdbc.models.Book;
import ru.buzynnikov.spring_data_jdbc.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        validate(book);
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Книга не найдена"));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void update(Book book) {
        bookRepository.existById(book.getId());
        validate(book);
        bookRepository.update(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private void validate(Book book) {
        if (book == null) {
            throw new BadRequestException("Книга не может быть пустой");
        }
        if (book.getAuthor() == null) {
            throw new BadRequestException("Имя автора не может быть пустым");
        }
        if (book.getAuthor().length() < 3 || book.getAuthor().length() > 255) {
            throw new BadRequestException("Имя автора должно быть от 3 до 255 символов");
        }
        if (book.getTitle() == null) {
            throw new BadRequestException("Название книги не может быть пустым");
        }
        if (book.getTitle().length() < 3 || book.getTitle().length() > 255) {
            throw new BadRequestException("Название книги должно быть от 3 до 255 символов");
        }
        if (LocalDate.now().isBefore(LocalDate.of(book.getPublicationYear(), 1, 1))) {
            throw new BadRequestException("Год издания не может быть в будущем");
        }
    }
}
