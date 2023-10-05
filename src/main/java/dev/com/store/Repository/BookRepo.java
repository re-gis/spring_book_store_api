package dev.com.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    
}
