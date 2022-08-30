package ab.persistencelayer;

import ab.persistencelayer.model.Customer;
import ab.persistencelayer.repository.CustomerRepository;
import org.hibernate.Session;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class BatchingUpdateTests {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    CustomerRepository customerRepository;

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 30, 50, 60, 80, 100, 120})
    @Transactional
    void singleSessionUpdate(int batchSize) {
        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);

        List<Customer> customerList = customerRepository.findMultipleTop(1000);
        customerList.forEach(x -> x.setSurname("TESTSURNAME"));


        long start = System.nanoTime();
        customerRepository.saveAll(customerList);
        customerRepository.flush();

        long end = System.nanoTime();
        System.out.println((end - start));

    }


}
