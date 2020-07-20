package com.assessment.customerPortal.integration;

import com.assessment.customerPortal.model.Address;
import com.assessment.customerPortal.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerPortalIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String customerId1 = "abc-123";
    private static final String customerId2 = "abc-456";

    @Test
    @DisplayName("Retrieving All customers")
    void testRetrieveAllCustomers() throws Exception {
        Address address1 = new Address("add1","abc","xyz","2543AS","","");
        Customer customer = new Customer("abc-123", "Fname1", "Lname1", 30);
        customer.setCurrentAddress(address1);

        mockMvc.perform(post("/api/v1/customerPortal/createCustomer").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customer)))
                .andExpect(status().isOk());

        Address address2 = new Address("add2","def","wxy","2543AS","","");
        Customer customer2 = new Customer("abc-456", "Fname2", "Lname2", 30);
        customer2.setCurrentAddress(address2);

        mockMvc.perform(post("/api/v1/customerPortal/createCustomer").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/customerPortal/retrieveAllCustomers"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", equalTo(customer.getFirstName())))
                .andExpect(jsonPath("$[1].firstName", equalTo(customer2.getFirstName())));

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
