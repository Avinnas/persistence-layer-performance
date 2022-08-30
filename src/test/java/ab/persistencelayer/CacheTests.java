package ab.persistencelayer;

import ab.persistencelayer.model.Product;
import ab.persistencelayer.repository.CustomerRepository;
import ab.persistencelayer.repository.OrderRepository;
import ab.persistencelayer.repository.ProductRepository;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("cache")
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

    @AfterClass
    public void shutdownCacheManager(){
        CacheManager.ALL_CACHE_MANAGERS.get(0).shutdown();
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    public void loadProducts(int limit) {
        Runtime runtime = Runtime.getRuntime();
        int loopCount = 5;
        long sum = 0;
        long start, end, memory1 = 1, memory2 = 1;


        memory1 = MemoryUtils.getMemoryUse();
        start = System.nanoTime();
        List<Product> products;
        products = productRepository.findMultipleTopWithCaching(limit);
        end = System.nanoTime();
        memory2 = MemoryUtils.getMemoryUse();

        long productsSize = GraphLayout.parseInstance(products).totalSize();
        Cache cache = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("products");

        System.out.println(memory2 - memory1 - productsSize + " B");
        System.out.println(end - start);

        List<?> a = cache.getKeys();

        products.forEach(product -> product.getProductId());


        for (int i = 0; i < loopCount; i++) {
            start = System.nanoTime();
            products = productRepository.findMultipleTopWithCaching(limit);
            end = System.nanoTime();
            System.out.println(end - start);
            sum += end - start;
            products = new ArrayList<>();
        }
        System.out.println(sum / loopCount);


        CacheManager.ALL_CACHE_MANAGERS.get(0).clearAll();

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20})
    @Transactional
    void firstLevelCache(int OBJECT_NUMBER) {

        List<Product> products = new ArrayList<>();
        List<Product> products2 = new ArrayList<>();

        long start = System.nanoTime();
        for (int i = 1; i <= OBJECT_NUMBER; i++) {
            products.add(productRepository.findById((long) i).get());
        }

        long end = System.nanoTime();

        Cache cache = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.example.persistencelayer.model.Product");

        System.out.println(end - start);

        int CACHE_HIT_COUNT = 3;
        long sum = 0;
        for (int i = 0; i < CACHE_HIT_COUNT; i++) {
            start = System.nanoTime();
            for (int j = 1; j <= OBJECT_NUMBER; j++) {
                products2.add(productRepository.findById((long) j).get());
            }

            end = System.nanoTime();
            sum+= end -start;
        }
        System.out.println(sum/CACHE_HIT_COUNT);
        cache.removeAll();

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20})
    void secondLevelEntityCache(int OBJECT_NUMBER) {

        List<Product> products = new ArrayList<>();
        List<Product> products2 = new ArrayList<>();

        long start = System.nanoTime();
        for (int i = 1; i <= OBJECT_NUMBER; i++) {
            products.add(productRepository.findById((long) i).get());
        }

        long end = System.nanoTime();

        Cache cache = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.example.persistencelayer.model.Product");

        System.out.println(end - start);

        int CACHE_HIT_COUNT = 3;
        long sum = 0;
        for (int i = 0; i < CACHE_HIT_COUNT; i++) {
            start = System.nanoTime();
            for (int j = 1; j <= OBJECT_NUMBER; j++) {
                products2.add(productRepository.findById((long) j).get());
            }

            end = System.nanoTime();
            sum+= end -start;
        }
        System.out.println(sum/CACHE_HIT_COUNT);
        cache.removeAll();

    }

//    @ParameterizedTest
//    @ValueSource(ints = {100})
//    @Transactional
//    void firstLevelCacheMemory(int OBJECT_NUMBER) {
//        long memory1=1, memory2=1;
//
//        List<Product> products = new ArrayList<>();
//
//        memory1 = MemoryUtils.getMemoryUse();
//        for (int i = 1; i <= OBJECT_NUMBER; i++) {
//            products.add(productRepository.findById((long) i).get());
//        }
//
//        memory2 = MemoryUtils.getMemoryUse();
//        System.out.println(products.size());
//        System.out.println(memory2-memory1);
//    }


}
