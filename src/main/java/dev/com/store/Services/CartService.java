package dev.com.store.Services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import dev.com.store.Entities.CartItem;

public interface CartService {
    String addBookToCart(Long bookId, int quantity) throws NotFoundException, Exception;

    List<CartItem> getCartItems(Long cartId) throws NotFoundException;

    String removeBookFromCart(Long bookId) throws NotFoundException, Exception;

    String checkout() throws NotFoundException, Exception;
}
