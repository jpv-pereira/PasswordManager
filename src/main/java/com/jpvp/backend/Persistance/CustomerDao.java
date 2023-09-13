package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    boolean userNameExists(String userName);

}
