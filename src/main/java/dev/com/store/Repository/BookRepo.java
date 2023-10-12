package dev.com.store.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Book;
import dev.com.store.dtos.CreateBookDto;
import jakarta.validation.constraints.NotNull;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Book save(@NotNull CreateBookDto dto);

    Book findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    Book findByIsbnContainingIgnoreCase(String isbn);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findAllByAuthor(String author);

}
