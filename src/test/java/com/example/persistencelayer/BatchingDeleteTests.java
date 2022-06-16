package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class BatchingDeleteTests {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Transactional
    void deleteByConditionWithPredefinedQuery() {
        long start = System.currentTimeMillis();
        customerRepository.deleteBySurnameContainingUsingQuery("surname1");
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Test
    @Transactional
    void deleteByCondition() {
        long start = System.currentTimeMillis();
        customerRepository.deleteBySurnameContaining("surname1");
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Test
    @Transactional
    void loadAndDeleteByCondition() {
        List<Customer> customers = customerRepository.findBySurnameContaining("surname1");
        long start = System.currentTimeMillis();
        customerRepository.deleteAll(customers);
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
