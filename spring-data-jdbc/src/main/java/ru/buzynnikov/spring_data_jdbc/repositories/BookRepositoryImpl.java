package ru.buzynnikov.spring_data_jdbc.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.spring_data_jdbc.exceptions.BookNotFoundException;
import ru.buzynnikov.spring_data_jdbc.models.Book;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository{

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getInt("publication_year"));
        return book;
    };

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book save(Book book) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublicationYear());
            return ps;
        }, keyHolder);
        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(generatedId).orElseThrow(() -> new BookNotFoundException("Книга с id " + generatedId + " не найдена"));
    }


    @Override
    public Optional<Book> findById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM book WHERE id = ?",
                rs -> rs.next() ? Optional.ofNullable(rowMapper.mapRow(rs, 1)) : Optional.empty(),
                id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", rowMapper);
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE book SET title = ?, author = ?, publication_year = ? WHERE id = ?");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublicationYear());
            ps.setLong(4, book.getId());
            return ps;
        });
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    @Override
    public void existById(Long id) {
        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException("Книга с id " + id + " не найдена");
        }
    }
}
