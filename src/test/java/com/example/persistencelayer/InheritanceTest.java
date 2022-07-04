package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Employee;
import com.example.persistencelayer.model.Person;
import com.example.persistencelayer.repository.CustomerRepository;
import com.example.persistencelayer.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("table_per_class")
public class InheritanceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @ParameterizedTest
    @ValueSource(ints = {50, 100, 500, 1000})
    @Transactional
    void insert(int customersCount){
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < customersCount; i++) {
            customers.add(new Customer("name", "surname", new ArrayList<>()));
        }
        long start = System.currentTimeMillis();
        customerRepository.saveAll(customers);
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @ParameterizedTest
    @ValueSource(ints = {50, 100, 500, 1000})
    @Transactional
    void getPersons(int personsCount){
        long start = System.currentTimeMillis();
        List<Customer> customers = customerRepository.findAll(PageRequest.of(0,personsCount)).getContent();
        List<Employee> employees = employeeRepository.findAll(PageRequest.of(0,personsCount)).getContent();
        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
}
