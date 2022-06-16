package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Employee;
import com.example.persistencelayer.model.Person;
import com.example.persistencelayer.repository.CustomerRepository;
import com.example.persistencelayer.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("single_table")
public class InheritanceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @Transactional
    void insert(){
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            customers.add(new Customer("name", "surname", new ArrayList<>()));
        }
        customerRepository.saveAll(customers);
        customerRepository.flush();
    }

    @Test
    @Transactional
    void getAllPersons(){
        List<Customer> customers = customerRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
    }
}
