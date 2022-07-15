package ab.persistencelayer;

import ab.persistencelayer.model.Order;
import ab.persistencelayer.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class LazyLoadingProductsTests {
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    void loadAndDisplayProductsByEmployeeWithFetch(){
        List<Order> orders = orderRepository.findByEmployeeIdWithFetch(200001);
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
    }

    @Test
    @Transactional
    void loadAndDisplayProductsByEmployee(){
        List<Order> orders = orderRepository.findByEmployeeId(200001);
        orders.forEach(order -> order.getProducts().forEach(product -> product.getName()));
    }

    @Test
    @Transactional
    void loadProductsByEmployee(){
        List<Order> orders = orderRepository.findByEmployeeId(200001);
    }
}
