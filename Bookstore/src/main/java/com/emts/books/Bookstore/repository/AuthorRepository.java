package com.emts.books.Bookstore.repository;

import com.emts.books.Bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
