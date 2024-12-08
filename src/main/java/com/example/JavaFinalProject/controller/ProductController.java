package com.example.JavaFinalProject.controller;

import com.example.JavaFinalProject.dtos.ProductDto;
import com.example.JavaFinalProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Permite gestionarea produselor din magazin


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200") // Permite cererile doar din acest URL
public class ProductController {

    @Autowired
    private ProductService productService;

    // Adaugă un produs nou folosind ProductDto

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
    }

    // Obține toate produsele
    @GetMapping("/api/products")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // Obține un produs după ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        if (productDto != null) {
            return new ResponseEntity<>(productDto, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Șterge un produs
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // Actualizează un produs
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        boolean isUpdated = productService.updateProduct(id, productDto);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }



}
