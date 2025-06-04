package ru.buzynnikov.spring_mvc_library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.buzynnikov.spring_mvc_library.models.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.id = :id")
    Optional<Author> findAuthorByIdWithoutBooks(Long id);
}