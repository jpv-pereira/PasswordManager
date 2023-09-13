package com.jpvp.backend.Controller;

import com.jpvp.backend.Model.Customer;
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
        return "Test";
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomner(customer);
    }

    @GetMapping(value = "/getList")
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }
}
