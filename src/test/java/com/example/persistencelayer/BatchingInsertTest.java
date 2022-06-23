package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.repository.CustomerRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BatchingInsertTest {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    CustomerRepository customerRepository;


    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    @Transactional
    void singleSessionInsert(int batchSize) {
        long sum = 0;

        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);

        List<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            customerList.add(new Customer("name", "surname", new ArrayList<>()));
        }

        long start = System.currentTimeMillis();
        customerRepository.saveAll(customerList);
        customerRepository.flush();

        long end = System.currentTimeMillis();
        System.out.println(batchSize + ": " + (end - start));

    }



}

