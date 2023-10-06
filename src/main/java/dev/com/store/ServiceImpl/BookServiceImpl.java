package dev.com.store.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Review;
import dev.com.store.Entities.User;
import dev.com.store.Repository.BookRepo;
import dev.com.store.Repository.ReviewRepo;
import dev.com.store.Services.BookService;
import dev.com.store.dtos.CreateBookDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ReviewRepo reviewRepo;

    @Override
    public Book saveBook(@NotNull CreateBookDto dto) {
        // check if book exists
        Optional<Book> eBook = bookRepo.findByIsbn(dto.getIsbn());
        if (eBook.isPresent()) {
            throw new IllegalArgumentException("Book already saved...");
        }
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setStock(dto.getStock());
        return bookRepo.save(book);
    }

    @Override
    public List<Book> findAllBooks() throws NotFoundException {
        return bookRepo.findAll();
    }

    @Override
    public Book findBookById(Long bookId) throws NotFoundException {
        return bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public String deleteBook(Long id) throws NotFoundException {
        var book = bookRepo.findById(id).orElseThrow(() -> new NotFoundException());
        bookRepo.delete(book);
        return String.format("Book deleted successfully!");
    }

    @Override
    public List<Book> findBooksByAuthor(String author) throws NotFoundException {
        List<Book> books = bookRepo.findByAuthorContainingIgnoreCase(author);
        if(books.isEmpty()) {
            throw new NotFoundException();
        }
        return books;
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepo.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Book findBookByIsbn(String isbn) throws NotFoundException {
        return bookRepo.findByIsbnContainingIgnoreCase(isbn);
    }

    @Override
    public String updateBookStock(Long bookId, Long newStock) throws NotFoundException {
        var book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException());
        book.setStock(newStock);
        bookRepo.save(book);
        return "Book updated successfully!";
    }

    @Override
    public Long getBookStock(Long bookId) throws NotFoundException {
        var book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException());
        return book.getStock();
    }

    @Override
    public Review addRating(Long bookId, int rating, String content, User user) throws NotFoundException {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException());

        Optional<Review> existingReview = reviewRepo.findByBookAndUser(book, user);
        if (existingReview.isPresent()) {
            throw new IllegalArgumentException("User has already reviewed this book");
        }

        // create a review
        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(rating);
        review.setContent(content);
        return reviewRepo.save(review);
    }

}
