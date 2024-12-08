package com.example.JavaFinalProject;

import com.example.JavaFinalProject.controller.ProductController;
import com.example.JavaFinalProject.dtos.ProductDto;
import com.example.JavaFinalProject.enums.ProductCategory;
import com.example.JavaFinalProject.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddProduct() throws Exception {
        ProductDto productDto = new ProductDto(null, "Sirop de Catina", "Pentru imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI);

        mockMvc.perform(post("/api/products/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllProducts() throws Exception {
        ProductDto product1 = new ProductDto(1L, "Sirop de Catina", "Pentru imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI);
        ProductDto product2 = new ProductDto(2L, "Ceai Antistres", "Calmant", BigDecimal.valueOf(19.99), ProductCategory.CEAIURI);

        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Sirop de Catina"))
                .andExpect(jsonPath("$[1].name").value("Ceai Antistres"));
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDto productDto = new ProductDto(1L, "Sirop de Catina", "Pentru imunitate", BigDecimal.valueOf(29.99), ProductCategory.SIROPURI);

        when(productService.getProductById(1L)).thenReturn(productDto);

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sirop de Catina"))
                .andExpect(jsonPath("$.description").value("Pentru imunitate"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        when(productService.deleteProduct(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDto updatedProductDto = new ProductDto(null, "Ceai Antistres", "Ceai calmant ce reduce stresul", BigDecimal.valueOf(24.99), ProductCategory.CEAIURI);

        when(productService.updateProduct(1L, updatedProductDto)).thenReturn(true);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        ProductDto updatedProductDto = new ProductDto(null, "Ceai Cardiac", "Ceai impotriva afectiunilor inimii", BigDecimal.valueOf(24.99), ProductCategory.CEAIURI);

        when(productService.updateProduct(1L, updatedProductDto)).thenReturn(false);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDto)))
                .andExpect(status().isNotFound());
    }
}
