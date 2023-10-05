package dev.com.store.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.store.Entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {}