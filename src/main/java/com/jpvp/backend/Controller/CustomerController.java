package com.jpvp.backend.Controller;

import com.jpvp.backend.Model.Customer;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /*
    A simple test mapping to see if I haven't royally screwed up and that the server is actually able to respond with 200
     */
    @GetMapping
    public String testMapping() {
        System.out.println("testedtested");
        return "Test";
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody String email, String password) {

    }

    @PostMapping(value = "/register")
    public Customer createCustomer(@RequestBody Customer customer) {

        System.out.println("Tried to register ");
        return customerService.createCustomer(customer);
    }

    @GetMapping(value = "/all")
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = "/findByUserName")
    public Customer findByUserMame (String userName) {
        return customerService.findByUserName(userName);
    }

    @PostMapping(value = "/createStoredPassword")
    public void createStoredPassword(Customer customer, StoredPassword storedPassword) {
        customerService.createStoredPassword(customer, storedPassword);
    }
}
