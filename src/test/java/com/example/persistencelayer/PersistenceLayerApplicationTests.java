package com.example.persistencelayer;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.repository.CustomerRepository;
import com.example.persistencelayer.service.CustomerService;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
class PersistenceLayerApplicationTests {
	@Autowired
	CustomerRepository customerRepository;

	@Test
	void contextLoads() {
	}
//
//	@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

	@Test
	@Transactional
	void populateCustomers() {
		CustomerService service = new CustomerService(customerRepository);
		int testQuantity = 1;
		long sum=0;
		for (int i = 0; i < testQuantity; i++) {
			long start = System.currentTimeMillis();
			service.addCustomers(1000);
			long end = System.currentTimeMillis();
			sum += end-start;
		}

		System.out.println(sum/testQuantity);

	}

	@Test
	@Transactional
	void deleteByCondition() {
		customerRepository.deleteBySurnameContaining("surname1");
	}

	@Test
	@Transactional
	void LoadAndDeleteByCondition() {
		List<Customer> customers = customerRepository.findBySurnameContaining("surname1");
		customerRepository.deleteAll(customers);
		customerRepository.flush();
	}

}
