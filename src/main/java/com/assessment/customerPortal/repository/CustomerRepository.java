package com.assessment.customerPortal.repository;

import com.assessment.customerPortal.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
    List<Customer> findByFirstNameAndLastName(String firstName, String lastname);
    Optional<Customer> findById(String id);
}
