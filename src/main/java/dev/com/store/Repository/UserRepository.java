package dev.com.store.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.com.store.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

}
