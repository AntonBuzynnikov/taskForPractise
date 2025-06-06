package ru.buzynnikov.spring_data_jdbc.services;

import ru.buzynnikov.spring_data_jdbc.models.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    Book findById(Long id);
    List<Book> findAll();
    void update(Book book);
    void deleteById(Long id);
}
