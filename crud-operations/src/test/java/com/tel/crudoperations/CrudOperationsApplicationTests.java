package com.tel.crudoperations;

import com.tel.crudoperations.exceptions.ResourceNotAuthorizedException;
import com.tel.crudoperations.exceptions.ResourceNotFoundException;
import com.tel.crudoperations.exceptions.ResourceNotValidException;
import com.tel.crudoperations.models.Customer;
import com.tel.crudoperations.repository.CustomerRepository;
import com.tel.crudoperations.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CrudOperationsApplicationTests {

	@Autowired
	private CustomerService customerService;

	@MockBean
	private CustomerRepository customerRepository;

	@Test
	public void addCustomer(){
		Customer c = new Customer(1,"alex","Jorge",true);
		when(customerRepository.save(c)).thenReturn(c);
		assertEquals(HttpStatus.OK,customerService.addCustomerDetails(c).getStatusCode());
	}

	@Test
	public void getAllCustomers(){
     when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(1,"Sanju","N",true)));
     assertEquals(HttpStatus.OK,customerService.getAllCustomerDetails().getStatusCode());
	}


	@Test
	public void getCustomers() throws ResourceNotFoundException, ResourceNotAuthorizedException {
		Customer cust=new Customer(1,"Sanju","N",true);
		when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(cust));
		assertEquals(HttpStatus.OK,customerService.getCustomerDetails(1).getStatusCode());
		assertEquals("Sanju",customerService.getCustomerDetails(1).getBody().getCustomerFName());
	}


	@Test
	public void updateCustomers() throws ResourceNotFoundException, ResourceNotValidException {
		Customer cust=new Customer(1,"Sanju","N",true);
		customerService.addCustomerDetails(cust);
		when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(cust));
		assertEquals(HttpStatus.OK,customerService.updateCustomerDetails(1,cust).getStatusCode());
	}

	@Test
	public void deleteCustomers() throws ResourceNotFoundException {
		Customer cust=new Customer(1,"Sanju","N",true);
		when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(cust));
		assertEquals(HttpStatus.OK,customerService.deleteCustomerDetails(1).getStatusCode());


	}

}
