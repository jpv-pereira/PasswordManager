package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Customer;
import com.jpvp.backend.Model.StoredPassword;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    Customer findByUserMame(String userName);

    <T> boolean verifyExists(String rowName, String verifyString, Class<T> type);

    void createStoredPassword(Customer customer, StoredPassword storedPassword);

}
