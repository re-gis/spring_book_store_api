package dev.com.store.ServiceImpl;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Cart;
import dev.com.store.Entities.CartItem;
import dev.com.store.Entities.User;
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
    private final UserServiceImpl userServiceImpl;

    public String addBookToCart(Long bookId, int quantity) throws Exception, NotFoundException {
        Cart cart = new Cart();
        User loggedUser = userServiceImpl.getLoggedInUser();
        cart.setUser(loggedUser);
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException());
        // create cartItem
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(cartItem.getTotalPrice());
        cart.addCartItem(cartItem);

        cartRepo.save(cart);
        return "Item added to cart...";
    }

    public List<CartItem> getCartItems(Long cartId) throws NotFoundException {
        // get the cart
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException());
        return cart.getCartItems();
    }

    // public CartItem getOneCartItem(Long cartId, Long cartItemId) throws
    // NotFoundException {

    // }

    // public String removeBookFromCart(Long bookId) throws NotFoundException {
    // }
}
