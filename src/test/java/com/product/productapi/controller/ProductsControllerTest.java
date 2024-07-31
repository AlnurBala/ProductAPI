package com.product.productapi.controller;

import com.product.productapi.dto.request.ProductsRequest;
import com.product.productapi.dto.response.ProductsResponse;
import com.product.productapi.service.ProductsService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductsControllerTest {

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Pageable pageable = Pageable.unpaged();
        ProductsResponse productResponse = new ProductsResponse(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);
        Page<ProductsResponse> page = new PageImpl<>(Collections.singletonList(productResponse));

        when(productsService.getAllProducts(pageable)).thenReturn(page);

        Page<ProductsResponse> response = productsController.getAllProducts(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).productName()).isEqualTo("Product 1");
    }

    @Test
    void testCreateProduct() {
        ProductsRequest request = new ProductsRequest(null, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);
        ProductsResponse response = new ProductsResponse(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);

        when(productsService.createProduct(request)).thenReturn(response);

        ResponseEntity<ProductsResponse> responseEntity = productsController.createProduct(request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(response);
    }

    @Test
    void testUpdateProduct() {
        ProductsRequest request = new ProductsRequest(1, "Product 1", "Updated Description", BigDecimal.valueOf(15.0), 150);
        ProductsResponse response = new ProductsResponse(1, "Product 1", "Updated Description", BigDecimal.valueOf(15.0), 150);

        when(productsService.updateProduct(1, request)).thenReturn(response);

        ProductsResponse updatedResponse = productsController.updateProduct(1, request);

        assertThat(updatedResponse).isEqualTo(response);
    }

    @Test
    void testDeleteProductById() {
        Integer id = 1;

        doNothing().when(productsService).deleteProductById(id);

        ResponseEntity<Void> responseEntity = productsController.deleteProductById(id);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testDeleteProductById_NotFound() {
        Integer id = 1;

        doThrow(new EntityNotFoundException("Product not found")).when(productsService).deleteProductById(id);

        ResponseEntity<Void> responseEntity = productsController.deleteProductById(id);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
