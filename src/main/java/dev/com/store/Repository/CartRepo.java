package dev.com.store.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Cart;
import dev.com.store.Entities.CartItem;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    void save(CartItem cartItem);


    Optional<Cart> findByUserId(Long id);
}