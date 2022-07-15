package ab.persistencelayer;

import ab.persistencelayer.model.Customer;
import ab.persistencelayer.model.Employee;
import ab.persistencelayer.model.Person;
import ab.persistencelayer.repository.CustomerRepository;
import ab.persistencelayer.repository.EmployeeRepository;
import ab.persistencelayer.repository.PersonRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("single_table")
public class InheritanceTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @ParameterizedTest
    @ValueSource(ints = {50, 100, 500, 1000})
    @Transactional
    void insert(int customersCount){
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < customersCount; i++) {
            customers.add(new Customer("name", "surname", new ArrayList<>()));
        }
        long start = System.currentTimeMillis();
        customerRepository.saveAll(customers);
        customerRepository.flush();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @ParameterizedTest
    @ValueSource(ints = {50, 100, 500, 1000})
    @Transactional
    void getPersons(int personsCount){
        long start = System.currentTimeMillis();
        List<Customer> customers = customerRepository.findAll(PageRequest.of(0,personsCount)).getContent();
        List<Employee> employees = employeeRepository.findAll(PageRequest.of(0,personsCount)).getContent();
        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

    @ParameterizedTest
    @ValueSource(ints = {100, 200, 1000, 2000})
    @Transactional
    void getPersonsPolymorphic(int personsCount){
        long start = System.currentTimeMillis();
        List<Person> customers = personRepository.findAll(PageRequest.of(0,personsCount)).getContent();
        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
}
