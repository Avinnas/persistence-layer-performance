package ab.persistencelayer;

import ab.persistencelayer.model.Customer;
import ab.persistencelayer.repository.CustomerRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
//@ActiveProfiles("UoW")
public class UnitOfWorkTests {

    @Autowired
    CustomerRepository customerRepository;

    @ParameterizedTest
    @ValueSource(ints = {500, 1000, 5000})
    @Transactional
    void singleFlushUpdate(int objectNumber) {
        List<Customer> customerList = new ArrayList<>();

        customerList = customerRepository.findMultipleTop(objectNumber);

        customerList.forEach(customer -> customer.setSurname("testSurname123"));
        long start = System.currentTimeMillis();
        customerRepository.saveAll(customerList);

        for (int i = 0; i < customerList.size(); i+=2) {
            Customer customer = customerList.get(i);
            customer.setSurname("testSurname456");
        }

        customerRepository.saveAll(customerList);
        customerRepository.flush();

        long end = System.currentTimeMillis();
        System.out.println((end - start));

    }

    @ParameterizedTest
    @ValueSource(ints = {500, 1000, 5000})
    @Transactional
    void multipleFlushUpdate(int objectNumber) {
        List<Customer> customerList = new ArrayList<>();

        customerList = customerRepository.findMultipleTop(objectNumber);

        customerList.forEach(customer -> customer.setSurname("testSurname123"));
        long start = System.currentTimeMillis();
        customerRepository.saveAll(customerList);
        customerRepository.flush();

        for (int i = 0; i < customerList.size(); i+=2) {
            Customer customer = customerList.get(i);
            customer.setSurname("testSurname456");
        }

        customerRepository.saveAll(customerList);
        customerRepository.flush();

        long end = System.currentTimeMillis();
        System.out.println((end - start));

    }


    @ParameterizedTest
    @ValueSource(ints = {100, 500, 1000})
    @Transactional
    void saveFlush(int objectNumber) {


        long start = System.currentTimeMillis();
        for (int i = 0; i < objectNumber; i++) {
            Customer customer = new Customer("name" +i, "surname"+i, new ArrayList<>());
            customerRepository.saveAndFlush(customer);
        }
        long end = System.currentTimeMillis();

        System.out.println(end-start);
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 500, 1000})
    @Transactional
    void singleFlush(int objectNumber) {


        long start = System.currentTimeMillis();
        for (int i = 0; i < objectNumber; i++) {
            Customer customer = new Customer("name" +i, "surname"+i, new ArrayList<>());
            customerRepository.save(customer);
        }
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
