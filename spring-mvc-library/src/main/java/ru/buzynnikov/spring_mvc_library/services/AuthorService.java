package ru.buzynnikov.spring_mvc_library.services;

import ru.buzynnikov.spring_mvc_library.models.Author;

public interface AuthorService {
    Author getById(Long id);
}
