package ab.persistencelayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class PersistenceLayerApplication {


	public static void main(String[] args) {
		SpringApplication.run(PersistenceLayerApplication.class, args);
	}

}
