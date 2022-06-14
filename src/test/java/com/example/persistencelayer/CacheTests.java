package com.example.persistencelayer;

import com.example.persistencelayer.model.Product;
import com.example.persistencelayer.repository.OrderRepository;
import com.example.persistencelayer.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CacheTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CacheManager cacheManager;

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
        cacheManager.getCache("products").clear();
    }

}
