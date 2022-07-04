package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Product;
import com.example.persistencelayer.repository.CustomerRepository;
import com.example.persistencelayer.repository.OrderRepository;
import com.example.persistencelayer.repository.ProductRepository;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.statistics.StatisticsGateway;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CacheTests {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;
//
//    @Autowired
//    CacheManager cacheManager;


    @Test
    public void loadProducts() {
        Runtime runtime = Runtime.getRuntime();
        int loopCount = 50;
        long sum = 0;
        long start, end, memory1 = 1, memory2 = 1;


        memory1 = MemoryUtils.getMemoryUse();
        start = System.nanoTime();
        List<Product> products = productRepository.findAllWithCaching();
        end = System.nanoTime();
        memory2 = MemoryUtils.getMemoryUse();


        System.out.println(memory2 - memory1 + " B");
        System.out.println(end - start);

        products.forEach(product -> product.getProductId());

        for (int i = 0; i < loopCount; i++) {
            start = System.nanoTime();
            products = productRepository.findAllWithCaching();
            end = System.nanoTime();
            System.out.println(end - start);
            sum += end - start;
        }
        System.out.println(sum / loopCount);
//        cacheManager.getCache("products").clear();
    }

    @Test
    @Transactional
    void firstLevelCache() {

        long start = System.nanoTime();
        Product product = productRepository.findById(100L).get();
        long end = System.nanoTime();

        System.out.println(end - start);

        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            Product product2 = productRepository.findById(100L).get();
            end = System.nanoTime();
            System.out.println(end - start);
        }

    }

    @Test
    void secondLevelEntityCache() {
        int OBJECT_COUNT = 10;

        List<Long> ids = LongStream.rangeClosed(1, OBJECT_COUNT)
                .boxed().collect(Collectors.toList());

        List<Product> products = new ArrayList<>();
        List<Product> products2 = new ArrayList<>();

        System.out.println(CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.example.persistencelayer.model.Product").getSize());
        long start = System.nanoTime();
        Product p = productRepository.findById(100L).get();
        long end = System.nanoTime();

        var a = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.example.persistencelayer.model.Product");

        System.out.println(end - start);
        System.out.println(a.getSize());

        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            Product p2 = productRepository.findById(100L).get();
            end = System.nanoTime();
            System.out.println(end - start);
        }

        CacheManager.ALL_CACHE_MANAGERS.get(0).clearAll();

        StatisticsGateway statisticsGateway = a.getStatistics();

        System.out.println();
    }


}
