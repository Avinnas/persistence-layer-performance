package ab.persistencelayer;

import ab.persistencelayer.model.Order;
import ab.persistencelayer.repository.OrderRepository;
import ab.persistencelayer.repository.ProductRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class LazyLoadingTests {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {1000})
    void orderProductLoading(int limit) {
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findMultipleTop(limit);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }


    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {1000})
    void orderProductLoadingAndDisplaying(int limit) {
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findMultipleTop(limit);
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {1000})
    void orderProductLoadAndDisplayWithFetch(int limit) {
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findMultipleTopFetchProducts(limit);
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }


}
