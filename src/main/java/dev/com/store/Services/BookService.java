package dev.com.store.Services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Review;
import dev.com.store.Entities.User;
import dev.com.store.dtos.CreateBookDto;

public interface BookService {
    List<Book> findAllBooks() throws NotFoundException;

    Book findBookById(Long id) throws NotFoundException;

    Book saveBook(CreateBookDto book);

    String deleteBook(Long id) throws NotFoundException;

    List<Book> findBooksByAuthor(String author) throws NotFoundException;

    Book findBookByIsbn(String isbn) throws NotFoundException;

    Book findBookByTitle(String title) throws NotFoundException;

    String updateBookStock(Long bookId, Long newStock) throws NotFoundException;

    Long getBookStock(Long bookId) throws NotFoundException;

    Review addRating(Long bookId, int rating, String content, User user) throws NotFoundException;
}
