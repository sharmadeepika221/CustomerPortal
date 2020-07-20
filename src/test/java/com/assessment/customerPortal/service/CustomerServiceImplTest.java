package com.assessment.customerPortal.service;

import com.assessment.customerPortal.exception.ResourceNotFoundException;
import com.assessment.customerPortal.model.Address;
import com.assessment.customerPortal.model.Customer;
import com.assessment.customerPortal.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @InjectMocks
    CustomerServiceImpl customerService;
    @Mock
    CustomerRepository repository;

    @Test
    @DisplayName("Test creating customer session with given fields")
    public void testCreateCustomer() {
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer.setCurrentAddress(address1);
        doReturn(customer).when(repository).save(customer);
        customerService.createCustomer(customer);
    }

    @Test
    @DisplayName("Test update Address with given customer id")
    public void testUpdateCustomer() throws ResourceNotFoundException {
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer1 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer1.setCurrentAddress(address1);

        Address address2 = new Address("add2","def","wxy","2543AS","","");
        // when
        when(repository.findById("abc-123")).thenReturn(Optional.of(customer1));
        doReturn(customer1).when(repository).save(customer1);
        Customer actualResponse = customerService.updateCustomer("abc-123", address2);

        // then
        assertEquals(actualResponse.getCurrentAddress().getCity(), address2.getCity());

    }

    @Test
    @DisplayName("Test retrieve all customers")
    public void testRetrieveAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer1 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer1.setCurrentAddress(address1);

        Address address2 = new Address("add2","def","wxy","2543AS","","");
        Customer customer2 = new Customer("abc-456", "Fname2", "Lname2", 30);
        customer2.setCurrentAddress(address2);

        customers.add(customer1);
        customers.add(customer2);
        doReturn(customers).when(repository).findAll();
        List<Customer> actualResponse = customerService.retrieveAllCustomers();
        assertEquals(actualResponse.size(), customers.size());
    }

    @Test
    @DisplayName("Test retrieve customer by id")
    public void testRetrieveCustomerById() {
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer.setCurrentAddress(address1);

        when(repository.findById("abc-123")).thenReturn(Optional.of(customer));
        Customer actualResponse = customerService.retrieveCustomerById("abc-123");
        assertEquals(actualResponse.getFirstName(), customer.getFirstName());
    }

    @Test
    @DisplayName("Test search customer by first name and last name")
    public void testSearchCustomerByName() {
        List<Customer> customers = new ArrayList<>();
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer.setCurrentAddress(address1);
        customers.add(customer);

        when(repository.findByFirstNameAndLastName("Fname1", "Lname1")).thenReturn(customers);
        List<Customer> actualResponse = customerService.searchCustomerByName("Fname1", "Lname1");
        assertEquals(actualResponse.size(), customers.size());
        assertEquals(actualResponse.get(0).getFirstName(), customers.get(0).getFirstName());
    }

}
