package com.emts.books.Bookstore.service;

import com.emts.books.Bookstore.model.Book;
import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.model.ShoppingCart;
import com.emts.books.Bookstore.model.dto.ChargeRequest;
import com.emts.books.Bookstore.model.enumerations.CartStatus;
import com.emts.books.Bookstore.model.exceptions.ActiveShoppingCartAlreadyExsists;
import com.emts.books.Bookstore.model.exceptions.ProductOutOfStockException;
import com.emts.books.Bookstore.model.exceptions.ShoppingCartIsNotActiveException;
import com.emts.books.Bookstore.repository.ShoppingCartRepository;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CustomerService customerService;
    private final PaymentService paymentService;
    private final BookService bookService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CustomerService customerService, PaymentService paymentService, BookService bookService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.customerService = customerService;
        this.paymentService = paymentService;
        this.bookService = bookService;
    }


    public ShoppingCart createShoppingCart(Long id) {
        Customer customer = this.customerService.findById(id);
        if (this.shoppingCartRepository.existsByCustomerUsernameAndStatus(id, CartStatus.CREATED)) {
            throw new ActiveShoppingCartAlreadyExsists();
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        return this.shoppingCartRepository.save(shoppingCart);
    }


    public ShoppingCart addProductToShoppingCart(Long id, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(id);
        Book book = this.bookService.findById(bookId);
        List<Book> books = shoppingCart.getBooks();
        for (Book b : books) {
            if (b.getId().equals(bookId)) {
                return shoppingCart;
            }
        }
        books.add(book);

        return this.shoppingCartRepository.save(shoppingCart);
    }


    public ShoppingCart removeProductFromShoppingCart(Long id, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(id);
        shoppingCart.setBooks(
                shoppingCart.getBooks()
                        .stream()
                        .filter(item -> !item.getId().equals(bookId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getActiveShoppingCartOrCreateNew(Long id) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByCustomerUsernameAndStatus(id, CartStatus.CREATED);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setCustomer(this.customerService.findById(id));
            shoppingCart = this.shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }


    public ShoppingCart cancelActiveShoppingCart(Long id) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByCustomerUsernameAndStatus(id, CartStatus.CREATED);
        if (shoppingCart == null) {
            throw new ShoppingCartIsNotActiveException();
        }
        shoppingCart.setStatus(CartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }


    @Transactional
    public ShoppingCart checkoutShoppingCart(Long id, ChargeRequest chargeRequest) throws Exception {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByCustomerUsernameAndStatus(id, CartStatus.CREATED);
        if (shoppingCart == null) {
            throw new ShoppingCartIsNotActiveException();
        }

        float price = 0;
        List<Book> books = shoppingCart.getBooks();

        for (Book book : books) {
            if (book.getQuantity() <= 0) {
                throw new ProductOutOfStockException(book.getId());
            }
            book.setQuantity(book.getQuantity() -1);
            price += book.getPrice();
        }

        Charge charge=null;
        try{
            charge=this.paymentService.pay(chargeRequest);
        }
        catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException ex)
        {
            throw new Exception() ;
        }

        //paymentService.pay(price)

        shoppingCart.setBooks(books);
        shoppingCart.setStatus(CartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart findActiveShoppingCartByUsername(Long id)
    {
        return this.shoppingCartRepository.findByCustomerUsernameAndStatus(id,CartStatus.CREATED);
    }
}
