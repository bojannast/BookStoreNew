package com.emts.books.Bookstore.service;

import com.emts.books.Bookstore.model.Admin;
import com.emts.books.Bookstore.model.Book;
import com.emts.books.Bookstore.model.Customer;
import com.emts.books.Bookstore.repository.AdminRepository;
import com.emts.books.Bookstore.repository.BookRepository;
import com.emts.books.Bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    private CustomerRepository customerRepository;

    private BookRepository bookRepository;


    @Autowired

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Customer>findCustomers(){
        return  customerRepository.findAll();
    }

    public List<Book>findBooks(){
        return  bookRepository.findAll();
    }

}
