package com.emts.books.Bookstore.service;

import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.model.exceptions.CustomerNotFoundException;
import com.emts.books.Bookstore.repository.CustomerRepository;
import com.emts.books.Bookstore.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private SecurityService securityService;

    @Autowired

    public CustomerService(CustomerRepository customerRepository,SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.securityService=securityService;
    }

    public List<Customer> findCustomers()
    {
       return  customerRepository.findAll();
    }

    public Customer findById(Long id)
    {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

    }

    public Customer findAuthenticatedCustomer()
    {
        return securityService.getAuthenticatedCustomer();
    }


    public Long getCurrentCustomerId() {
        return this.findAuthenticatedCustomer().getId();
    }


    public void deleteById(Long id)
    {
         customerRepository.deleteById(id);
    }
}
