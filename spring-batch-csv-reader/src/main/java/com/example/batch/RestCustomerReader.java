package com.example.batch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RestCustomerReader implements ItemReader<Customer> {

    private final String url;
    private final RestTemplate restTemplate;
    private int nextCustomer;
    private List<Customer> customerList;

    public RestCustomerReader(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public Customer read() throws Exception {
        if (this.customerList == null) {
            customerList = fetchCustomers();
        }
        Customer customer = null;

        if (nextCustomer < customerList.size()) {
            customer = customerList.get(nextCustomer);
            nextCustomer++;
        } else {
            nextCustomer = 0;
            customerList = null;
        }
        return customer;
    }

    private List<Customer> fetchCustomers() {
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(this.url, Customer[].class);
        Customer[] customers = response.getBody();
        if (customers != null) {
            return Arrays.asList(customers);
        }
        return null;
    }
}
