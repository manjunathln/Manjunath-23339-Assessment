package com.tel.crudoperations.rest;

import com.tel.crudoperations.exceptions.ResourceNotAuthorizedException;
import com.tel.crudoperations.exceptions.ResourceNotFoundException;
import com.tel.crudoperations.exceptions.ResourceNotValidException;
import com.tel.crudoperations.models.Customer;
import com.tel.crudoperations.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerResource {
    static Logger logger = LoggerFactory.getLogger(CustomerResource.class);
    @Autowired
    public CustomerService customerService;

    /**
     * INSERT a customer record into database
     * @param customer
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<Object> insertCustomerRecord(@RequestBody Customer customer){
        logger.info("Insert Customer Records");
        return customerService.addCustomerDetails(customer);

    }

    /**
     * GET a customer record by Customer Number
     * @param customerId
     * @return
     */
    @GetMapping(value = "/{customerId}",produces = "application/json")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable("customerId") int customerId ) throws ResourceNotFoundException, ResourceNotAuthorizedException {
        logger.info("Getting Customer Record");
        logger.debug("Calling Get Customer.."+customerId);
        return customerService.getCustomerDetails(customerId);

    }

    /**
     * UPDATE a customer Record into database
     * @param customerId
     * @param customer
     * @return
     */
    @PutMapping(value = "/{customerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateCustomerDetails(@PathVariable("customerId") int customerId, @RequestBody Customer customer ) throws ResourceNotFoundException, ResourceNotValidException {
        logger.info("Updating Customer Record");
        logger.debug("Calling Method Update Customer.."+customerId);
        return customerService.updateCustomerDetails(customerId,customer);

    }

    /**
     * DELETE a customer Record from Database
     * @param customerId
     * @return
     */
    @DeleteMapping(value = "/{customerId}",produces = "application/json")
    public ResponseEntity<Object> deleteCustomerDetails(@PathVariable("customerId") int customerId ) throws ResourceNotFoundException {
        logger.info("Updating Customer Record");
        logger.debug("Calling Method Delete Customer.."+customerId);
        return customerService.deleteCustomerDetails(customerId);

    }

    /**
     * GET ALL Customers from database
     * @return
     */
    @GetMapping(value = "/",produces = "application/json")
    public ResponseEntity<Object> getAllCustomerDetails(){
        logger.info("Get all Customer Records");
        logger.debug("Calling Method Get All Customers..");
        return customerService.getAllCustomerDetails();
    }
}
