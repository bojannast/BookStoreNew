package com.emts.books.Bookstore.service;

import com.emts.books.Bookstore.model.Author;
import com.emts.books.Bookstore.model.Book;
import com.emts.books.Bookstore.model.exceptions.ProductNotFoundException;
import com.emts.books.Bookstore.repository.AuthorRepository;
import com.emts.books.Bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book createBook(String title, Long authorId) {
        Author author = authorRepository.findById(authorId).get();
        Book book = new Book(title, author);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }


    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
}