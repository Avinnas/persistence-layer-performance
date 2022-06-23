package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.repository.CustomerRepository;
import com.example.persistencelayer.repository.OrderRepository;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BatchingDeleteTests {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    void deleteByConditionWithPredefinedQuery() {
        long start = System.currentTimeMillis();
        customerRepository.deleteBySurnameContainingUsingQuery("surname1");
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100})
    void deleteByCondition(int batchSize) {

        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);

        long start = System.currentTimeMillis();
        customerRepository.deleteBySurnameContaining("surname12");
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
    @Transactional
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    void deleteOrderByCondition(int batchSize) {

        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);
        long start = System.currentTimeMillis();
        orderRepository.deleteByCreationDateBefore(LocalDateTime.of(2018,9,1,0,0));
        orderRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    void loadAndDeleteOrderByCondition(int batchSize) {

        List<Order> orders = orderRepository.findMultipleTop(1000);
        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);
        long start = System.currentTimeMillis();
        orderRepository.deleteAll(orders);
        orderRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    @Transactional
    void loadAndDeleteByCondition(int batchSize) {
        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);
        List<Customer> customers = customerRepository.findMultipleTop(500);
        long start = System.currentTimeMillis();
        customerRepository.deleteAll(customers);
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
