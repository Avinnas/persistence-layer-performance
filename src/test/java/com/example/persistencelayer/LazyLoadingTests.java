package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.model.Product;
import com.example.persistencelayer.repository.OrderRepository;
import com.example.persistencelayer.repository.ProductRepository;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class LazyLoadingTests {


    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;


//    @Test
//    @Transactional
//    void imageLoading() {
//        List<Product> products = productRepository.findAll();
//    }
//
//    @Test
//    @Transactional
//    void imageLoadingAndDisplaying() {
//        List<Product> products = productRepository.findAll();
//        products.forEach(product -> {
//            try {
//                product.getImage().getImage().length();
//            } catch (SQLException e) {
//                e.getMessage();
//            }
//        });
//    }
//
//    @Test
//    @Transactional
//    void imageLoadingWithFetch(){
//        List<Product> products = productRepository.findAllWithFetchedImages();
//        products.forEach(product -> {
//            try {
//                product.getImage().getImage().length();
//            } catch (SQLException e) {
//                e.getMessage();
//            }
//        });
//    }

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000})
    void orderProductLoading(int limit) {
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findMultipleTop(limit);
        long end = System.currentTimeMillis();
        int size = orders.size();
        System.out.println(end-start);
    }


    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000})
    void orderProductLoadingAndDisplaying(int limit) {
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findMultipleTop(limit);
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
        long end = System.currentTimeMillis();
        int size = orders.size();
        System.out.println(end-start);

    }

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000})
    void orderProductLoadAndDisplayWithFetch(int limit) {
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findMultipleTopFetchProducts(limit);
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
        long end = System.currentTimeMillis();
        int size = orders.size();
        System.out.println(end-start);
    }


}
