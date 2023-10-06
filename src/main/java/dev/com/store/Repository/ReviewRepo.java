package dev.com.store.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Review;
import dev.com.store.Entities.User;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    Optional<Review> findByBookAndUser(Book book, User user);
    
}
