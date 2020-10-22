package com.emts.books.Bookstore.repository;

import com.emts.books.Bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer>findByUsername(String username);

    @Transactional
    void deleteById(Long id);
}
