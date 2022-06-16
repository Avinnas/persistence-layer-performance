package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Product;
import com.example.persistencelayer.repository.CustomerRepository;
import com.example.persistencelayer.repository.OrderRepository;
import com.example.persistencelayer.repository.ProductRepository;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    private long getMemoryUse(){
        putOutTheGarbage();
        long totalMemory = Runtime.getRuntime().totalMemory();
        putOutTheGarbage();
        long freeMemory = Runtime.getRuntime().freeMemory();
        return (totalMemory - freeMemory);
    }

    private void collectGarbage(){
        try{
            System.gc();
            Thread.currentThread().sleep(100);
            System.runFinalization();
            Thread.currentThread().sleep(100);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    private void putOutTheGarbage() {
        collectGarbage();
        collectGarbage();
    }

    @Test
    public void loadProducts() {
        Runtime runtime = Runtime.getRuntime();
        int loopCount = 50;
        long sum = 0;
        long start, end, memory1=1, memory2=1;


        memory1 = getMemoryUse();
        start = System.nanoTime();
        List<Product> products= productRepository.findAllWithCaching();
        end = System.nanoTime();
        memory2 = getMemoryUse();


        System.out.println(memory2-memory1 + " B");
        System.out.println(end - start);

        products.forEach(product -> product.getProductId());

        for (int i = 0; i < loopCount; i++) {
            start = System.nanoTime();
            products = productRepository.findAllWithCaching();
            end = System.nanoTime();
            System.out.println(end-start);
            sum += end-start;
        }
        System.out.println(sum/loopCount);
//        cacheManager.getCache("products").clear();
    }

    @Test
    @Transactional
    void firstLevelCache(){

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
    void secondLevelEntityCache(){
        long start = System.nanoTime();
        Customer customer= customerRepository.findById(100L).get();
        long end = System.nanoTime();
        var a  = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.example.persistencelayer.model.Customer");

        System.out.println(end - start);
        System.out.println(a.getSize());

        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            Customer customer2 = customerRepository.findById(100L).get();
            end = System.nanoTime();
            System.out.println(end - start);
        }
    }


}
