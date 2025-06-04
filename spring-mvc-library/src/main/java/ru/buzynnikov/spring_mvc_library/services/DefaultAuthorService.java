package ru.buzynnikov.spring_mvc_library.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.spring_mvc_library.exceptions.AuthorNotFoundException;
import ru.buzynnikov.spring_mvc_library.models.Author;
import ru.buzynnikov.spring_mvc_library.repositories.AuthorRepository;

@Service
public class DefaultAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    public DefaultAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findAuthorByIdWithoutBooks(id).orElseThrow(() -> new AuthorNotFoundException("Автор с id " + id + " не найден"));
    }
}
