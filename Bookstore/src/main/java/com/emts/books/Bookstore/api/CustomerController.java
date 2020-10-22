package com.emts.books.Bookstore.api;

import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.security.SecurityService;
import com.emts.books.Bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private CustomerService customerService;

    private SecurityService securityService;

    @Autowired
    public CustomerController(CustomerService customerService, SecurityService securityService) {
        this.customerService = customerService;
        this.securityService = securityService;
    }

    @GetMapping("/api/customers")
    public List<Customer>findCustomers()
    {
        return customerService.findCustomers();
    }

    @GetMapping("/api/customers/{id}")
    public Customer findById(@PathVariable Long id)
    {
        return customerService.findById(id);
    }

    @GetMapping("/api/customers/me")
    public Customer findAuthenticatedCustomer()
    {
        return securityService.getAuthenticatedCustomer();
    }

    @DeleteMapping("/api/customers/{id}")
    public void deleteById(@PathVariable Long id) {

        customerService.deleteById(id);

    }
}
