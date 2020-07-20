# Customer Management portal Project ( Backend)
This is a Spring Boot Web application used to manage the customers.

## Steps needed:
* Clone this repository
* You can build the project by running 
```
        mvn clean package
```
* Once successfully built, you can run the service by one of these two methods:
```
        mvn spring-boot:run
```

The REST API details are mentioned in the below url . The port used is 9090. 
```
        http://localhost:9444/swagger-ui.html#/

```

## Endpoints :
* /createCustomer           --To create a new Customer
* /updateCustomer/{id}      --Update the address of the customer of id
* /retrieveAllCustomers     --Retrieve all customers    
* /retrieveCustomer/{id}    --Retrieve customer by id
* /searchCustomerByName     -- Search customer by first name and last name

