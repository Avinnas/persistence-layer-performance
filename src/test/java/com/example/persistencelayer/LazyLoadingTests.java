package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.model.Product;
import com.example.persistencelayer.repository.OrderRepository;
import com.example.persistencelayer.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class LazyLoadingTests {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;


    @Test
    @Transactional
    void imageLoading() {
        List<Product> products = productRepository.findAll();
    }

    @Test
    @Transactional
    void imageLoadingAndDisplaying() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            try {
                product.getImage().getImage().length();
            } catch (SQLException e) {
                e.getMessage();
            }
        });
    }

    @Test
    @Transactional
    void imageLoadingWithFetch(){
        List<Product> products = productRepository.findAllWithFetchedImages();
        products.forEach(product -> {
            try {
                product.getImage().getImage().length();
            } catch (SQLException e) {
                e.getMessage();
            }
        });
    }

    @Test
    @Transactional
    void orderProductLoading() {
        List<Order> orders = orderRepository.findAll();
    }


    @Test
    @Transactional
    void orderProductLoadingAndDisplaying() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
    }

    @Test
    @Transactional
    void orderProductLoadAndDisplayWithFetch() {
        List<Order> orders = orderRepository.findAllFetchProducts();
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
    }


}
