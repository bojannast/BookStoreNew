package com.emts.books.Bookstore.api;

import com.emts.books.Bookstore.model.Book;
import com.emts.books.Bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/api/books")
    public Book createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(request.title, request.authorId);
    }

    public static class CreateBookRequest {

        public String title;

        public Long authorId;
    }

    @GetMapping("/api/books/{id}")
    public Book findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/api/books")
    public List<Book> findBooks(@RequestParam(required = false) Long authorId) {
        if (authorId != null) {
            return bookService.findBooksByAuthorId(authorId);
        }
        return bookService.findBooks();
    }

    @GetMapping("/api/authors/{authorId}/books")
    public List<Book> findBooksByAuthorId(@PathVariable Long authorId) {
        return bookService.findBooksByAuthorId(authorId);
    }
}
