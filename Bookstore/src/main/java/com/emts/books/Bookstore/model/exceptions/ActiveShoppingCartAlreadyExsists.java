package com.emts.books.Bookstore.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ActiveShoppingCartAlreadyExsists extends RuntimeException{
    public ActiveShoppingCartAlreadyExsists() {
        super("ActiveShoppingCartAlreadyExists");
    }
}

