package com.assessment.customerPortal.controller;

import com.assessment.customerPortal.exception.ResourceNotFoundException;
import com.assessment.customerPortal.model.Address;
import com.assessment.customerPortal.model.Customer;
import com.assessment.customerPortal.service.CustomerService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller class for customer management.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/customerPortal")
@Api(tags = { "CustomerPortal" })
public class CustomerController extends AbstractRestHandler{
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a new Customer
     * @param requestedCustomer
     * @return
     */
    @PostMapping("/createCustomer")
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid Customer requestedCustomer) {
        customerService.createCustomer(requestedCustomer);
        return new ResponseEntity<String>("Customer created successfully", HttpStatus.OK);
    }

    /**
     * Updates the current living address of the customer with id.
     * @param id
     * @param address
     * @return Customer
     * @throws ResourceNotFoundException
     */
    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Customer> UpdateCustomer(@PathVariable String id,
                                                   @Valid @RequestBody Address address) throws ResourceNotFoundException {
        return ok().body(customerService.updateCustomer(id, address));
    }

    /**
     * This method retrieves all the customers
     * @return List<Customer>
     * @throws ResourceNotFoundException
     */
    @GetMapping("/retrieveAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() throws ResourceNotFoundException {
        List<Customer> results = customerService.retrieveAllCustomers();
        if (results == null) {
            logger.error("No Customers are there");
            throw new ResourceNotFoundException("No record found");
        }
        return new ResponseEntity<List<Customer>>(results, HttpStatus.OK);
    }

    /**
     * Retrieves the customer with passed id
     * @param id
     * @return Customer
     * @throws ResourceNotFoundException
     */
    @GetMapping("/retrieveCustomer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) throws ResourceNotFoundException {
        Customer result = customerService.retrieveCustomerById(id);
        if (result == null) {
            logger.error("No Customer found by this id");
            throw new ResourceNotFoundException("No record found");
        }
        return new ResponseEntity<Customer>(result, HttpStatus.OK);
    }

    /**
     * This method searches for the customers by First name and Last name
     * @param params
     * @return List<Customer></Customer>
     * @throws ResourceNotFoundException
     */
    @GetMapping("/searchCustomerByName")
    public ResponseEntity<List<Customer>> getCustomerByName(@RequestParam Map<String, String> params) throws ResourceNotFoundException {
        List<Customer> results = customerService.searchCustomerByName(params.get("firstname"), params.get("lastname"));
        if (results == null) {
            logger.error("No Customers are there");
            throw new ResourceNotFoundException("No record found");
        }
        return new ResponseEntity<List<Customer>>(results, HttpStatus.OK);
    }
}
