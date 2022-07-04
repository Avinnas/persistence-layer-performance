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
import java.sql.*;
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
        System.out.println((end - start));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    @Transactional
    void identityGeneration(int batchSize){

        int ENTITY_COUNT = 1000;

        String url = "jdbc:mysql://localhost:3306/mysql_db?rewriteBatchedStatements=true";
        String user = "root";
        String password = "password";


        long start = System.currentTimeMillis();
        try(Connection conn = DriverManager.getConnection(url, user, password);
        ){

            String sql = "INSERT INTO customers(name, surname) VALUES(?,?)";
            PreparedStatement statement= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            conn.setAutoCommit(false);

            List<Long> list1 = new ArrayList<>();


            for (int i = 0; i < ENTITY_COUNT; i++) {
                statement.setString(1,"name " +i);
                statement.setString(2,"surname" + i);
                statement.addBatch();

                if(i % batchSize == 0 || i +1 == ENTITY_COUNT){
                    statement.executeBatch();
                    statement.clearParameters();
                    ResultSet keys = statement.getGeneratedKeys();
                    while(keys.next()){
                        list1.add(keys.getLong(1));
                    }
                }
            }
            conn.commit();
            long end = System.currentTimeMillis();

            System.out.println(end - start);

            customersCleanup(list1);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void customersCleanup(List<Long> idsToRemove){
        customerRepository.deleteAllById(idsToRemove);
        customerRepository.flush();
    }

}

