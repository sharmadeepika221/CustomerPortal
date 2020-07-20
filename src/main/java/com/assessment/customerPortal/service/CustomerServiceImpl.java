package com.assessment.customerPortal.service;

import com.assessment.customerPortal.exception.ResourceNotFoundException;
import com.assessment.customerPortal.model.Address;
import com.assessment.customerPortal.model.Customer;
import com.assessment.customerPortal.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for customer management
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerServiceImpl() {
    }

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * This method searches the customers by first name and last name
     * @param firstName
     * @param lastName
     * @return List<Customer>
     */
    @Override
    public List<Customer> searchCustomerByName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Updates the address of the customer
     * @param id
     * @param address
     * @return
     * @throws ResourceNotFoundException
     */
    @Override
    public Customer updateCustomer(String id, Address address) throws ResourceNotFoundException {
        Customer savedCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
        Address currentAddress = savedCustomer.getCurrentAddress();
        currentAddress.setCity(address.getCity());
        currentAddress.setFlatNo(address.getFlatNo());
        currentAddress.setCountry(address.getCountry());
        currentAddress.setPostalCode(address.getPostalCode());
        savedCustomer.setCurrentAddress(currentAddress);
        log.info("Address is getting updated", currentAddress.getFlatNo(),currentAddress.getCity(),
                currentAddress.getPostalCode(),currentAddress.getCountry());
        return  customerRepository.save(savedCustomer);
    }

    /**
     * Retrieves all the customers
     * @return List<Customer>
     */
    @Override
    public List<Customer> retrieveAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * Retrieves the customer by id
     * @param id
     * @return Customer
     */
    @Override
    public Customer retrieveCustomerById(String id) {
        return customerRepository.findById(id).get();
    }

    /**
     * @param customer
     * @return customer
     */
    @Override
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
