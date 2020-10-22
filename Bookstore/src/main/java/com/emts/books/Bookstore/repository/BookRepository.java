package com.emts.books.Bookstore.repository;

import com.emts.books.Bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByAuthorId(Long id);
}
