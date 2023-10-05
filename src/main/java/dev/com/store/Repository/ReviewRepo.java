package dev.com.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    
}
