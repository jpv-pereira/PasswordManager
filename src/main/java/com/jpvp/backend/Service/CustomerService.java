package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.EmailTakenException;
import com.jpvp.backend.Exception.UsernameTakenException;
import com.jpvp.backend.Model.Customer;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaCustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private JpaCustomerDao jpaCustomerDao;

    public Customer createCustomer(Customer customer) {
        /*
        If the email is in use the user already has an account, more important than knowing if the username is taken
         */
        if (jpaCustomerDao.verifyExists("email", customer.getEmail(), Customer.class)) {
            throw new EmailTakenException();
        }

        if (jpaCustomerDao.verifyExists("userName", customer.getUserName(), Customer.class)) {
            throw new UsernameTakenException();
        }

        return jpaCustomerDao.createCustomer(customer);
    }

    public List<Customer> listCustomers() {
        return jpaCustomerDao.getAllCustomers();
    }

    public Customer findByUserName(String userName) {
        return jpaCustomerDao.findByUserMame(userName);
    }

    public Customer getClientByID(long id) {
        return null;
    }

    public void createStoredPassword(Customer customer, StoredPassword storedPassword) {
        jpaCustomerDao.createStoredPassword(customer, storedPassword);
    }

}
