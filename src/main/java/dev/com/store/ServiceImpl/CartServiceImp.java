package dev.com.store.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import dev.com.store.Entities.Book;
import dev.com.store.Entities.Cart;
import dev.com.store.Entities.CartItem;
import dev.com.store.Entities.Order;
import dev.com.store.Entities.OrderItem;
import dev.com.store.Entities.User;
import dev.com.store.Enums.OStatus;
import dev.com.store.Repository.BookRepo;
import dev.com.store.Repository.CartItemRepo;
import dev.com.store.Repository.CartRepo;
import dev.com.store.Repository.OrderRepo;
import dev.com.store.Services.CartService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final BookRepo bookRepo;
    private final UserServiceImpl userServiceImpl;
    private final OrderRepo orderRepo;

    public String addBookToCart(Long bookId, int quantity) throws Exception, NotFoundException {
        User loggedUser = userServiceImpl.getLoggedInUser();
        Optional<Cart> userCart = cartRepo.findByUserId(loggedUser.getId());

        if (userCart.isPresent()) {
            Cart cart = userCart.get();
            Book book = bookRepo.findById(bookId)
                    .orElseThrow(() -> new NotFoundException());

            if (book.getStock() < quantity) {
                return "Not enough stock!";
            }

            // Create cartItem
            CartItem cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(cartItem.getTotalPrice());
            cart.getCartItems().add(cartItem);

            cartItemRepo.save(cartItem);
            cartRepo.save(cart);

            return "Item added to cart...";
        }

        Cart cart = new Cart();
        cart.setUser(loggedUser);
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException());
        if (book.getStock() < quantity) {
            return "Not enough stock!";
        }
        // create cartItem
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(cartItem.getTotalPrice());
        cart.setCartItem(cartItem);
        // cart.setCartItems();

        // save the cart_item also
        cartRepo.save(cart);
        cartItemRepo.save(cartItem);
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

    public String removeBookFromCart(Long bookId) throws NotFoundException, Exception {
        User loggedUser = userServiceImpl.getLoggedInUser();

        // get the user cart
        Optional<Cart> uCart = cartRepo.findByUserId(loggedUser.getId());

        if (!uCart.isPresent()) {
            return "Cart not found!";
        }

        Cart cart = uCart.get();
        List<CartItem> cartItems = cart.getCartItems();

        Optional<CartItem> tCartItem = cartItems.stream().filter(item -> item.getBook().getId() == bookId)
                .findFirst();

        if (!tCartItem.isPresent()) {
            return "Cart-item not found!";
        }

        cartItems.remove(tCartItem.get());
        cart.setCartItems(cartItems);
        cartRepo.save(cart);

        return "Cart item removed successfully!";

    }

    public String checkout() throws NotFoundException, Exception {
        List<OrderItem> orderItems = new ArrayList<>();
        // get cart of logged user
        User user = userServiceImpl.getLoggedInUser();
        Optional<Cart> uCart = cartRepo.findByUserId(user.getId());
        if (!uCart.isPresent()) {
            return "Cart not found!";
        }
        Cart cart = uCart.get();
        List<CartItem> cartItems = cart.getCartItems();
        Order order = new Order();

        for (CartItem cartItem : cartItems) {
            Book book = bookRepo.findById(cartItem.getBook().getId())
                    .orElseThrow(() -> new Exception("The Book is not found!"));

            if (book.getStock() < cartItem.getQuantity()) {
                return "No enough stock!";
            }
            // Update the book stock
            book.setStock(book.getStock() - cartItem.getQuantity());
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(order);
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(0, orderItem);

            order.setUser(user);
            order.setOrderStatus(OStatus.PENDING);
            order.setOrderItems(orderItems);

        }
        orderRepo.save(order);
        cartItems.clear();
        return "Order had been sent...";
    }
}
