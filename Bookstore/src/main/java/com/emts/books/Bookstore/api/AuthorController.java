package com.emts.books.Bookstore.api;

import com.emts.books.Bookstore.model.Author;
import com.emts.books.Bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/authors")
    public List<Author> findAuthors()
    {
        return authorService.findAuthors();
    }

}
