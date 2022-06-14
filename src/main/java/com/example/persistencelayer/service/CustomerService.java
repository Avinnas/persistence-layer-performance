package com.example.persistencelayer.service;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public void addCustomers(int quantity){
        for (int i = 0; i < quantity; i++) {
            customerRepository.save(new Customer("TESTNAME", "surname"+i, new ArrayList<>()));
        }
        customerRepository.flush();
    }

}