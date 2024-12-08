package com.example.JavaFinalProject;

import com.example.JavaFinalProject.dtos.ProductDto;
import com.example.JavaFinalProject.enums.ProductCategory;
import com.example.JavaFinalProject.model.Product;
import com.example.JavaFinalProject.repository.ProductRepository;
import com.example.JavaFinalProject.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    // Mock pentru repository-ul de produse
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    // Instanțierea serviciului de produse, folosind mock-ul repository-ului
    private final ProductService productService = new ProductService(productRepository);

    @Test
    void testGetAllProducts() {
        // Creez produse fictive pentru testare
        Product product1 = new Product("Sirop de Catina si Macese", "Sirop pentru imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI, 40);
        Product product2 = new Product("Ceai Antistres", "Ceai calmant", BigDecimal.valueOf(29.99), ProductCategory.CEAIURI, 40);

        // Simulez comportamentul repository-ului pentru metoda findAll
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Apelez metoda de testat
        List<ProductDto> products = productService.getAllProducts();

        // Verific rezultatele
        assertEquals(2, products.size());
        assertEquals("Sirop de Catina si Macese", products.get(0).getName());
        assertEquals("Ceai Antistres", products.get(1).getName()); // Verific numele celui de-al doilea produs
    }

    @Test
    void testAddProduct() {
        // Creez un DTO pentru produsul care va fi adăugat
        ProductDto productDto = new ProductDto(null, "Sirop de Catina", "Pentru imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI);

        // Apelez metoda de testat
        productService.addProduct(productDto);

        // Verific că metoda save a fost apelată o dată
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Simulez că produsul există în repository
        when(productRepository.existsById(1L)).thenReturn(true);

        // Apelez metoda de testat
        boolean result = productService.deleteProduct(1L);

        // Verific rezultatele
        assertEquals(true, result);
        // Verific că metoda returnează true
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L); // Verific că metoda deleteById a fost apelată o dată
    }

    @Test
    void testDeleteProductNotFound() {
        // Simulez că produsul nu există în repository
        when(productRepository.existsById(1L)).thenReturn(false);

        // Apelez metoda de testat
        boolean result = productService.deleteProduct(1L);

        // Verific rezultatele
        assertEquals(false, result); // Verific că metoda returnează false
        Mockito.verify(productRepository, Mockito.never()).deleteById(1L); // Verific că metoda deleteById nu a fost apelată
    }

    @Test
    void testGetProductById() {
        // Creez un produs fictiv
        Product product = new Product("Sirop de Catina", "Imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI, 10);

        // Simulez comportamentul repository-ului pentru metoda findById
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Apelez metoda de testat
        ProductDto productDto = productService.getProductById(1L);

        // Verific rezultatele
        assertEquals("Sirop de Catina", productDto.getName()); // Verific numele produsului
        assertEquals("Imunitate", productDto.getDescription()); // Verific descrierea produsului
    }

    @Test
    void testUpdateProduct() {
        // Creez un produs și un DTO cu actualizări
        Product product = new Product("Sirop de Catina", "Imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI, 10);
        ProductDto updatedProductDto = new ProductDto(null, "Sirop de Macese", "Pentru sănătate", BigDecimal.valueOf(39.99), ProductCategory.SIROPURI);

        // Simulez comportamentul repository-ului pentru metoda findById
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Apelez metoda de testat
        boolean result = productService.updateProduct(1L, updatedProductDto);

        // Verific rezultatele
        assertEquals(true, result); // Verific că metoda returnează true
        assertEquals("Sirop de Macese", product.getName()); // Verific că numele a fost actualizat
        assertEquals(BigDecimal.valueOf(39.99), product.getPrice()); // Verific că prețul a fost actualizat
        Mockito.verify(productRepository, Mockito.times(1)).save(product); // Verific că metoda save a fost apelată o dată
    }
}
