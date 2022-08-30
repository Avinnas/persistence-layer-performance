package ab.persistencelayer;

import ab.persistencelayer.model.Customer;
import ab.persistencelayer.model.Order;
import ab.persistencelayer.repository.CustomerRepository;
import ab.persistencelayer.repository.OrderRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BatchingDeleteTests {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Transactional
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    void loadAndDeleteOrderByCondition(int batchSize) {

        List<Order> orders = orderRepository.findMultipleTop(1000);
        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);
        long start = System.currentTimeMillis();
        orderRepository.deleteAll(orders);
        orderRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
