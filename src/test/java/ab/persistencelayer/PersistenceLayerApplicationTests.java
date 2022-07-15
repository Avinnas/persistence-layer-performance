package ab.persistencelayer;

import ab.persistencelayer.model.Customer;
import ab.persistencelayer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
class PersistenceLayerApplicationTests {
	@Autowired
    CustomerRepository customerRepository;

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
