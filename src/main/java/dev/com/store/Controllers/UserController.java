package dev.com.store.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.com.store.Entities.User;
import dev.com.store.ServiceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/current-user")
    public ResponseEntity<User> getLoggedInUser() throws Exception {
        return ResponseEntity.ok(userServiceImpl.getLoggedInUser());
    }
}
