package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.repository.CustomerRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
public class TestUtils {

    @Transactional
    @Rollback
    public void updateTransaction(List<Customer> customerList, CustomerRepository customerRepository){
        customerRepository.saveAll(customerList);
        customerRepository.flush();
    }
}
