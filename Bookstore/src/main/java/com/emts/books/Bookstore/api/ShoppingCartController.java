package com.emts.books.Bookstore.api;

import com.emts.books.Bookstore.model.ShoppingCart;
import com.emts.books.Bookstore.service.CustomerService;
import com.emts.books.Bookstore.service.ShoppingCartService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final CustomerService customerService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CustomerService customerService) {
        this.shoppingCartService = shoppingCartService;
        this.customerService = customerService;
    }

    @PostMapping("/{id}/add-book")
    public String addBookToShoppingCart(@PathVariable Long id)
    {
        ShoppingCart shoppingCart=this.shoppingCartService.addProductToShoppingCart(this.customerService.getCurrentCustomerId(),id);
        return "redirect:/books";
    }


    @PostMapping("/{id}/remove-book")
    public String removeBookToShoppingCart(@PathVariable Long id)
    {
        ShoppingCart shoppingCart=this.shoppingCartService.addProductToShoppingCart(this.customerService.getCurrentCustomerId(),id);
        return "redirect:/books";
    }
}
