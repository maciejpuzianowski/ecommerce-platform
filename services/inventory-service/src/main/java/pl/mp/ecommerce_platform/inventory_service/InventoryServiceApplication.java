package pl.mp.ecommerce_platform.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mp.ecommerce_platform.inventory_service.model.Inventory;
import pl.mp.ecommerce_platform.inventory_service.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initInventory(InventoryRepository inventoryRepository) {
		return args -> {
			inventoryRepository.save(new Inventory(null, 1L, 50));
			inventoryRepository.save(new Inventory(null, 2L, 70));
			inventoryRepository.save(new Inventory(null, 3L, 100));
			inventoryRepository.save(new Inventory(null, 4L, 90));
			inventoryRepository.save(new Inventory(null, 5L, 45));
			inventoryRepository.save(new Inventory(null, 6L, 65));
			inventoryRepository.save(new Inventory(null, 7L, 77));
			inventoryRepository.save(new Inventory(null, 8L, 57));
			inventoryRepository.save(new Inventory(null, 9L, 88));
			inventoryRepository.save(new Inventory(null, 10L, 23));
		};
	}
}
