package com.example.JavaFinalProject;

import com.example.JavaFinalProject.dtos.ProductDto;
import com.example.JavaFinalProject.model.Product;
import com.example.JavaFinalProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JavaFinalProjectApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(JavaFinalProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<ProductDto> products = productService.getAllProducts();

        System.out.println("Produsele din baza de date:");
        for (ProductDto product : products) {
            System.out.println("ID: " + product.getId() +
                    ", Nume: " + product.getName() +
                    ", Descriere: " + product.getDescription() +
                    ", Preț: " + product.getPrice());
        }
    }
}
