package dev.com.store.Controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.com.store.Entities.CartItem;
import dev.com.store.Services.CartService;
import dev.com.store.dtos.AddToCartDto;
import dev.com.store.dtos.RemoveFromCartDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartDto dto) throws NotFoundException, Exception {
        return ResponseEntity.ok(cartService.addBookToCart(dto.getBookId(), dto.getQuantity()));
    }

    @GetMapping("/{cart}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable("cart") Long id) throws NotFoundException {
        return ResponseEntity.ok(cartService.getCartItems(id));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeItemFromCart(@RequestBody RemoveFromCartDto removeFromCartDto)
            throws NotFoundException, Exception {
        return ResponseEntity.ok(cartService.removeBookFromCart(removeFromCartDto.getBookId()));
    }

}
