package com.emts.books.Bookstore.repository;

import com.emts.books.Bookstore.model.ShoppingCart;
import com.emts.books.Bookstore.model.enumerations.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findByCustomerUsernameAndStatus(Long id, CartStatus status);

    boolean existsByCustomerUsernameAndStatus(Long id, CartStatus status);

}
