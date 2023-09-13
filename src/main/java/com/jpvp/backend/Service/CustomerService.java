package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.UsernameTakenException;
import com.jpvp.backend.Model.Customer;
import com.jpvp.backend.Persistance.JpaCustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private JpaCustomerDao jpaCustomerDao;

    public Customer createCustomer(Customer customer) {
        if (jpaCustomerDao.userNameExists(customer.getUserName())) {
            throw new UsernameTakenException();
        }

        return jpaCustomerDao.createCustomer(customer);
    }

    public List<Customer> listCustomers() {
        return jpaCustomerDao.getAllCustomers();
    }

    public Customer getClientByID(long id) {
        return null;
    }

}
