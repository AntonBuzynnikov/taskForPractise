package ru.buzynnikov.spring_mvc_library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_mvc_library.dto.CreateBookDto;
import ru.buzynnikov.spring_mvc_library.dto.UpdateBookDto;
import ru.buzynnikov.spring_mvc_library.exceptions.BookNotFoundException;
import ru.buzynnikov.spring_mvc_library.models.Author;
import ru.buzynnikov.spring_mvc_library.models.Book;
import ru.buzynnikov.spring_mvc_library.repositories.BookRepository;

@Service
public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    public DefaultBookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Книга с id = " + id + " не найдена"));
    }

    @Override
    public Book createBook(CreateBookDto createBookDto) {
        Book book = new Book();
        Author author = authorService.getById(createBookDto.authorId());
        book.setTitle(createBookDto.name());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, UpdateBookDto updateBookDto) {
        Book book = getBookById(id);
        Author author = authorService.getById(updateBookDto.authorId());
        book.setAuthor(author);
        book.setTitle(updateBookDto.title());
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
