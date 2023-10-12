package dev.com.store.Controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Review;
import dev.com.store.ServiceImpl.BookServiceImpl;
import dev.com.store.dtos.AddRatingDto;
import dev.com.store.dtos.CreateBookDto;
import dev.com.store.dtos.NewStockDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<?> saveBook(@RequestBody CreateBookDto dto) {
        return ResponseEntity
                .ok(bookServiceImpl.saveBook(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable("id") Long id) throws Exception {
        Book book = bookServiceImpl.findBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAllBooks() throws NotFoundException {
        List<Book> books = bookServiceImpl.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseEntity.ok(bookServiceImpl.deleteBook(id));
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestBody String author)
            throws NotFoundException {
        List<Book> books = bookServiceImpl.findBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<Long> getBookStock(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseEntity.ok(bookServiceImpl.getBookStock(id));
    }

    @PutMapping("/stock/update/{id}")
    public ResponseEntity<String> updateBookStock(@PathVariable("id") Long id, @RequestBody NewStockDto newStockDto)
            throws NotFoundException {
        return ResponseEntity.ok(bookServiceImpl.updateBookStock(id, newStockDto.getNewStock()));
    }

    @PostMapping("/rating/add")
    public ResponseEntity<Review> addRating(@RequestBody AddRatingDto dto) throws NotFoundException, Exception {
        return ResponseEntity
                .ok(bookServiceImpl.addRating(dto.getBookId(), dto.getRating(), dto.getContent()));
    }

}
