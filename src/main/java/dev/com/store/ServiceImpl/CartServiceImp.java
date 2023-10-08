package dev.com.store.ServiceImpl;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Cart;
import dev.com.store.Entities.CartItem;
import dev.com.store.Repository.BookRepo;
import dev.com.store.Repository.CartItemRepo;
import dev.com.store.Repository.CartRepo;
import dev.com.store.Services.CartService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final BookRepo bookRepo;

    public CartItem addBookToCart(Long bookId, int quantity) throws NotFoundException {
        Cart cart = new Cart();
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException());
        // create cartItem
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(cartItem.getTotalPrice());
    }

    public List<CartItem> getCartItems(Long cartId) throws NotFoundException {

    }

    public CartItem getOneCartItem(Long cartId, Long cartItemId) throws NotFoundException {

    }

    public String removeBookFromCart(Long bookId) throws NotFoundException {
    }
}
