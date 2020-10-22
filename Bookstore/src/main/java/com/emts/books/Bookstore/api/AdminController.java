package com.emts.books.Bookstore.api;

import com.emts.books.Bookstore.model.Book;
import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.service.AdminService;
import com.emts.books.Bookstore.service.BookService;
import com.emts.books.Bookstore.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private AdminService adminService;

    private BookService bookService;

    private CustomerService customerService;

    public AdminController(AdminService adminService, BookService bookService, CustomerService customerService) {
        this.adminService = adminService;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @GetMapping("/api/admin/customers")
    public List<Customer> findCustomers()
    {
        return customerService.findCustomers();
    }

    @GetMapping("/api/admin/books")
    public List<Book> findBooks()
    {
        return bookService.findBooks();
    }
}
