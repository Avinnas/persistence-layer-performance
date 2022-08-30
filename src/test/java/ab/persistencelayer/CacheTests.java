package ab.persistencelayer;

import ab.persistencelayer.model.Product;
import ab.persistencelayer.repository.CustomerRepository;
import ab.persistencelayer.repository.OrderRepository;
import ab.persistencelayer.repository.ProductRepository;
import net.sf.ehcache.CacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;


    @Before
    public void createCacheManager(){
        CacheManager.newInstance();
    }

    @After
    public void shutdownCacheManager(){
        CacheManager.ALL_CACHE_MANAGERS.get(0).shutdown();
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    public void loadProducts(int limit) {
        Runtime runtime = Runtime.getRuntime();
        int loopCount = 5;
        long sum = 0;
        long start, end;

        start = System.nanoTime();
        List<Product> products;
        products = productRepository.findMultipleTopWithCaching(limit);
        end = System.nanoTime();

        System.out.println(end - start);

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
        CacheManager.ALL_CACHE_MANAGERS.get(0).clearAll();

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
        CacheManager.ALL_CACHE_MANAGERS.get(0).clearAll();

    }

}
