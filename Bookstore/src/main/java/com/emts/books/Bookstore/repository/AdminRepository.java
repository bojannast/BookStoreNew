package com.emts.books.Bookstore.repository;

import com.emts.books.Bookstore.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
