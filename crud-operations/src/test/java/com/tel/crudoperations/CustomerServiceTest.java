package com.tel.crudoperations;

import com.tel.crudoperations.models.Customer;
import com.tel.crudoperations.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    public CustomerRepository customerRepository;

    @Test
    public void testAddCustomer() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:8082/customer/";
        URI uri = new URI(baseUrl);

        Customer c= new Customer(1,"Manju","N",true);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Customer> entity = new HttpEntity<Customer>(c,headers);
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("technical", "Assessment"));
        ResponseEntity<Object> result = restTemplate.exchange(baseUrl,HttpMethod.POST, entity, Object.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }



    @Test
    public void testGetCustomer() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        customerRepository.save(new Customer(1,"Sanju","N",true));

        final String baseUrl = "http://localhost:8082/customer/1";
        URI uri = new URI(baseUrl);
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("technical", "Assessment"));
        ResponseEntity<Customer> result = restTemplate.getForEntity(uri, Customer.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().getCustomerFName().equals("Sanju"));


    }

    @Test
    public void testGetAllCustomers() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:8082/customer/";
        URI uri = new URI(baseUrl);
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("technical", "Assessment"));
        ResponseEntity<Object> result = restTemplate.getForEntity(uri, Object.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());

    }

    @Test
    public void testUpdateCustomer() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        customerRepository.save(new Customer(1,"Sanju","N",true));

        final String baseUrl = "http://localhost:8082/customer/1";
        URI uri = new URI(baseUrl);
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("technical", "Assessment"));
        Customer c= new Customer(1,"Manjunath","N",true);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Customer> entity = new HttpEntity<Customer>(c,headers);
        ResponseEntity<Customer> result = restTemplate.exchange(baseUrl,HttpMethod.PUT, entity, Customer.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());

    }


    @Test
    public void testDeleteCustomer() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        customerRepository.save(new Customer(1,"Sanju","N",true));
        final String baseUrl = "http://localhost:8082/customer/1";
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("technical", "Assessment"));
        ResponseEntity<Object> result = restTemplate.exchange(baseUrl,HttpMethod.DELETE, null, Object.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());

    }
}
