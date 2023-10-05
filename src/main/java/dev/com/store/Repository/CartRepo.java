package dev.com.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
}