package com.product.productapi.controller;


import com.product.productapi.dto.request.ProductsRequest;
import com.product.productapi.dto.response.ProductsResponse;
import com.product.productapi.service.ProductsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public Page<ProductsResponse> getAllProducts(Pageable pageable) {
        return productsService.getAllProducts(pageable);
    }

    @PostMapping
    public ResponseEntity<ProductsResponse> createProduct(@RequestBody ProductsRequest productsRequest) {
        ProductsResponse response = productsService.createProduct(productsRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ProductsResponse updateProduct(@PathVariable Integer id, @RequestBody ProductsRequest productsRequest) {
        return productsService.updateProduct(id, productsRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Integer id) {
        try {
            productsService.deleteProductById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }


}
