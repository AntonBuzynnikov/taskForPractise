package ru.buzynnikov.spring_data_jdbc.repositories;

import ru.buzynnikov.spring_data_jdbc.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    void update(Book book);
    void deleteById(Long id);
    void existById(Long id);
}
