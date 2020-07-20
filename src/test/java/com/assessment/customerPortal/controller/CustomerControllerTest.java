package com.assessment.customerPortal.controller;

import com.assessment.customerPortal.model.Address;
import com.assessment.customerPortal.model.Customer;
import com.assessment.customerPortal.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private static final String customer_id1 = "abc-123";

    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /createCustomer - Success")
    void testCreateCustomer() throws Exception {

        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer1 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer1.setCurrentAddress(address1);

        Address address2 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer2 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer2.setCurrentAddress(address2);

        mockMvc.perform(post("/api/v1/customerPortal/createCustomer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customer1)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /retrieveAllCustomers - Success")
    void testRetrieveAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer1 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer1.setCurrentAddress(address1);

        Address address2 = new Address("add2","def","wxy","2543AS","","");
        Customer customer2 = new Customer("abc-456", "Fname2", "Lname2", 30);
        customer2.setCurrentAddress(address2);

        customers.add(customer1);
        customers.add(customer2);

        doReturn(customers).when(customerService).retrieveAllCustomers();

        mockMvc.perform(get("/api/v1/customerPortal/retrieveAllCustomers"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(customers.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].firstName", equalTo(customers.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName",  equalTo(customers.get(0).getLastName())))
                .andExpect(jsonPath("$[0].age", equalTo(customers.get(0).getAge())))
                .andExpect(jsonPath("$[1].id", equalTo(customers.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].firstName", equalTo(customers.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].lastName",  equalTo(customers.get(1).getLastName())))
                .andExpect(jsonPath("$[1].age", equalTo(customers.get(1).getAge())));

    }

    @Test
    @DisplayName("GET /retrieveCustomerById - Success")
    void testRetrieveCustomerById() throws Exception {
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer1 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer1.setCurrentAddress(address1);

        doReturn(customer1).when(customerService).retrieveCustomerById(customer_id1);

        mockMvc.perform(get("/api/v1/customerPortal/retrieveCustomer/{id}",customer_id1))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(customer1.getId().toString())))
                .andExpect(jsonPath("$.firstName", equalTo(customer1.getFirstName())))
                .andExpect(jsonPath("$.lastName",  equalTo(customer1.getLastName())))
                .andExpect(jsonPath("$.age", equalTo(customer1.getAge())));
    }

    @Test
    @DisplayName("GET /searchCustomerByName - Success")
    void testSearchCustomerByName() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer1 = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer1.setCurrentAddress(address1);

        Address address2 = new Address("add2","def","wxy","2543AS","","");
        Customer customer2 = new Customer("abc-456", "Fname2", "Lname2", 30);
        customer2.setCurrentAddress(address2);

        customers.add(customer1);
        customers.add(customer2);

        doReturn(customers).when(customerService).searchCustomerByName("Fname1","Lname1");
        mockMvc.perform(get("/api/v1/customerPortal/searchCustomerByName?firstname=Fname1&lastname=Lname1"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(customers.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].firstName", equalTo(customers.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName",  equalTo(customers.get(0).getLastName())))
                .andExpect(jsonPath("$[0].age", equalTo(customers.get(0).getAge())))
                .andExpect(jsonPath("$[1].id", equalTo(customers.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].firstName", equalTo(customers.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].lastName",  equalTo(customers.get(1).getLastName())))
                .andExpect(jsonPath("$[1].age", equalTo(customers.get(1).getAge())));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
