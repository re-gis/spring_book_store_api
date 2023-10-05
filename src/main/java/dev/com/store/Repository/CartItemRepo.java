package dev.com.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    
}
