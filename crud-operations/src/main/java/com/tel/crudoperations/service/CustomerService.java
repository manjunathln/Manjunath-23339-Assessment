package com.tel.crudoperations.service;

import com.tel.crudoperations.exceptions.ResourceNotAuthorizedException;
import com.tel.crudoperations.exceptions.ResourceNotFoundException;
import com.tel.crudoperations.exceptions.ResourceNotValidException;
import com.tel.crudoperations.models.Customer;
import com.tel.crudoperations.repository.CustomerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;


@Service
@CacheConfig(cacheNames = {"customer","allCustomers"})
public class CustomerService {
    static  Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    public CustomerRepository customerRepository;

    @Caching(evict = {
            @CacheEvict(value ="customer" , allEntries=true),
            @CacheEvict(value ="allCustomers" , allEntries=true)
    })

    public ResponseEntity<Object> addCustomerDetails(Customer customer) {
        customerRepository.save(customer);
        HashMap<String,String> respMsg=new HashMap<>();
        respMsg.put("add Operation","Customer Details added successfully");
        return new ResponseEntity<>(respMsg, HttpStatus.OK);
    }

    @Cacheable(value = "customer", sync =  true)
    public ResponseEntity<Customer> getCustomerDetails(int customerId) throws ResourceNotFoundException, ResourceNotAuthorizedException {

       logger.debug("Calling Service Method Get Customer.."+customerId);
      Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        if(!customer.isCustomerStatus())
            throw new ResourceNotAuthorizedException("Not authorized to access this id " + customerId);

        return new ResponseEntity<>(customer,HttpStatus.OK);
    }


    @Caching(evict = {
            @CacheEvict(value ="customer" , allEntries=true),
            @CacheEvict(value ="allCustomers" , allEntries=true)
    })
    @Transactional
    public ResponseEntity<Object> updateCustomerDetails(int customerId, Customer customer) throws ResourceNotFoundException,ResourceNotValidException {
        logger.debug("Calling Service Method Update Customer.."+customerId);
            Customer custDetails = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
           if(customerId==customer.getCustomerId()) {
               Customer updatedCustDetails = customerRepository.save(customer);
               return new ResponseEntity<>(updatedCustDetails, HttpStatus.OK);
           }else{
               throw new ResourceNotValidException("Customer Id and Customer details are not matching please verify!");

           }
        }

    @Caching(evict = {
            @CacheEvict(value ="customer" , allEntries=true),
            @CacheEvict(value ="allCustomers" , allEntries=true)
    })
    @Transactional
    public ResponseEntity<Object> deleteCustomerDetails(int customerId) throws ResourceNotFoundException {
        logger.debug("Calling Service Method Delete Customer.."+customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        customerRepository.deleteById(customerId);
        HashMap<String,String> respMsg=new HashMap<>();
        respMsg.put("Delete Operation","Customer Details deleted successfully");
        return new ResponseEntity<>(respMsg, HttpStatus.OK);
     }
    @Cacheable(value = "allCustomers" ,sync = true)
    public ResponseEntity<Object> getAllCustomerDetails() {
        logger.debug("Calling Service Method Get All Customers..");
        List<Customer> customers= (List<Customer>) customerRepository.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
