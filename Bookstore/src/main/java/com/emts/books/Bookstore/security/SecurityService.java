package com.emts.books.Bookstore.security;
import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private CustomerRepository customerRepository;

    @Autowired
    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getAuthenticatedCustomer() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerRepository.findByUsername(userDetails.getUsername()).get();
    }
}
