package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.dtos.ProductDto;
import com.example.JavaFinalProject.model.Product;

import java.util.List;

public interface ProductServiceInterface {
    // Obține toate produsele
    List<ProductDto> getAllProducts();

    // Găsește un produs după ID
    Product findProductById(Long id);



    // Adaugă un produs (cu DTO ca parametru)
    void addProduct(ProductDto productDto);

    // Șterge un produs după ID
    boolean deleteProduct(Long id);

    // Actualizează stocul unui produs
    void updateProductStock(Long id, Integer quantity);

    // Actualizează un produs existent
    boolean updateProduct(Long id, ProductDto productDto);


    ProductDto getProductById(Long id);
}

