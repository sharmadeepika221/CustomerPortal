package com.assessment.customerPortal.service;

import com.assessment.customerPortal.exception.ResourceNotFoundException;
import com.assessment.customerPortal.model.Address;
import com.assessment.customerPortal.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> searchCustomerByName(String firstName, String lastName);
    Customer updateCustomer(String id, Address address) throws ResourceNotFoundException;
    List<Customer> retrieveAllCustomers();
    Customer retrieveCustomerById(String id);
    void createCustomer(Customer customer);
}
