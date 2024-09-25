package pl.mp.ecommerce_platform.product_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mp.ecommerce_platform.product_service.model.Product;
import pl.mp.ecommerce_platform.product_service.repository.ProductRepository;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ProductRepository productRepository) {
		return args -> {
			productRepository.save(new Product(null, "Apple iPhone 14", "Latest model of Apple iPhone", 999.99));
			productRepository.save(new Product(null, "Samsung Galaxy S23", "Flagship Samsung smartphone with excellent camera", 849.99));
			productRepository.save(new Product(null, "Sony WH-1000XM5 Headphones", "Noise-cancelling wireless over-ear headphones", 299.99));
			productRepository.save(new Product(null, "Apple MacBook Pro", "16-inch Apple MacBook Pro with M2 chip", 2399.99));
			productRepository.save(new Product(null, "Dell XPS 13", "Ultra-thin laptop with 11th Gen Intel i7", 1299.99));
			productRepository.save(new Product(null, "Google Pixel 7", "Latest Google Pixel smartphone with stock Android", 599.99));
			productRepository.save(new Product(null, "Sony PlayStation 5", "Next-gen gaming console with 8K support", 499.99));
			productRepository.save(new Product(null, "Nikon Z6 II Camera", "Full-frame mirrorless camera with 24.5 MP sensor", 1999.99));
			productRepository.save(new Product(null, "Apple iPad Pro 12.9", "Apple’s latest iPad Pro with Liquid Retina display", 1099.99));
			productRepository.save(new Product(null, "Bose SoundLink Revolve", "Portable Bluetooth speaker with 360-degree sound", 179.99));
		};
	}

}
