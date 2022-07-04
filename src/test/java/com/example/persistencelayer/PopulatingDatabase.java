package com.example.persistencelayer;

import com.example.persistencelayer.model.*;
import com.example.persistencelayer.repository.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@ActiveProfiles("table_per_class")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PopulatingDatabase {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageRepository imageRepository;

    @Test
    @org.junit.jupiter.api.Order(1)
    void populateCustomers() {
        long sum = 0;
        List<Customer> customers = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            customers.add(new Customer("TESTNAME", "surname" + j, new ArrayList<>()));
        }
        customerRepository.saveAll(customers);
        long end = System.currentTimeMillis();

        sum += end - start;
        System.out.println(sum);

    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void populateEmployeeTable() {
        Faker faker = new Faker();

        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            double random = ThreadLocalRandom.current().nextDouble(3000, 7000);
            Employee employee = new Employee(faker.name().firstName(), faker.name().lastName(), BigDecimal.valueOf(random), null);
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void populateImageTable() {
        byte[] bytes;
        List<Image> images = new ArrayList<>();
        try {
            for (int i = 0; i < 5000; i++) {
                int size = ThreadLocalRandom.current().nextInt(30000, 200000);
                bytes = new byte[size];
                SecureRandom.getInstanceStrong().nextBytes(bytes);
                images.add(new Image(new SerialBlob(bytes)));
            }
            imageRepository.saveAll(images);

        } catch (NoSuchAlgorithmException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void populateProductTable() {
        List<Image> images = imageRepository.findAll();
        Random random = new Random();

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            String generatedString = random.ints(97, 122)
                    .limit(10)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            int category = random.nextInt(5);
            int imageId = category * 100 + random.nextInt(100);
            products.add(new Product(generatedString, random.nextInt(20),
                    images.get(imageId), ProductCategory.values()[category], null));
        }
        productRepository.saveAll(products);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void populateOrderTable() {
        List<Product> products = productRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        List<Order> orders = new ArrayList<>();

        Instant fourYearsAgo = Instant.now().minus(Duration.ofDays(4 * 365));
        Instant tenDaysAgo = Instant.now().minus(Duration.ofDays(10));

        for (int i = 0; i < 100000; i++) {
            long creationDate = ThreadLocalRandom.current().nextLong(fourYearsAgo.getEpochSecond(), tenDaysAgo.getEpochSecond());
            long deliveryDuration = ThreadLocalRandom.current().nextInt(24, 96);
            long deliveryDate = Instant.ofEpochSecond(creationDate).plus(Duration.ofHours(deliveryDuration)).getEpochSecond();

            int productQuantity = ThreadLocalRandom.current().nextInt(1, 11);
            List<Product> tempProducts = new ArrayList<>();
            for (int j = 0; j < productQuantity; j++) {
                int productIndex = ThreadLocalRandom.current().nextInt(products.size());
                tempProducts.add(products.get(productIndex));
            }
            int customerIndex = ThreadLocalRandom.current().nextInt(customers.size());
            int employeeIndex = ThreadLocalRandom.current().nextInt(employees.size());

            orders.add(new Order(
                    LocalDateTime.ofEpochSecond(creationDate, 0, ZoneOffset.UTC),
                    LocalDateTime.ofEpochSecond(deliveryDate, 0, ZoneOffset.UTC),
                    customers.get(customerIndex),
                    employees.get(employeeIndex), tempProducts));
        }
        orderRepository.saveAll(orders);
    }


}
