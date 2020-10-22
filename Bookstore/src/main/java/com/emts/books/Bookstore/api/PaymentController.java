package com.emts.books.Bookstore.api;

import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.model.ShoppingCart;
import com.emts.books.Bookstore.model.dto.ChargeRequest;
import com.emts.books.Bookstore.service.CustomerService;
import com.emts.books.Bookstore.service.ShoppingCartService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/payments")
public class PaymentController {


    @Value("STRIPE_P_KEY")
    private String publicKey;

    private ShoppingCartService shoppingCartService;

    private Customer customer;

    public PaymentController(){};

    public PaymentController(ShoppingCartService shoppingCartService,Customer customer) {
        this.shoppingCartService = shoppingCartService;
        this.customer=customer;

    }


    @GetMapping("/charge")
    public String getCheckoutPage(Model model)
    {
        try{
            ShoppingCart shoppingCart=this.shoppingCartService.findActiveShoppingCartByUsername(this.customer.getId());
            model.addAttribute("shoppingCart",shoppingCart);
            model.addAttribute("currency","eur");
            model.addAttribute("amount",(int)(shoppingCart.getBooks().stream().mapToDouble(book->book.getPrice()).sum()*100));
            model.addAttribute("stripePublicKey",this.publicKey);
            return "checkout";
        }
        catch (RuntimeException ex)
        {
            return ex.getLocalizedMessage();
        }


    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest, Model model) throws Exception {
        try{
            this.shoppingCartService.checkoutShoppingCart(this.customer.getId(),chargeRequest);
        } catch (RuntimeException | CardException | APIException | AuthenticationException | InvalidRequestException | APIConnectionException ex) {
            return ex.getLocalizedMessage();
        }
        return null;
    }
}
