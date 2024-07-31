package com.product.productapi.service;


import com.product.productapi.dto.request.ProductsRequest;
import com.product.productapi.dto.response.ProductsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsService {
    Page<ProductsResponse> getAllProducts(Pageable pageable);
    ProductsResponse createProduct(ProductsRequest productsRequest);
    ProductsResponse updateProduct(Integer id, ProductsRequest productsRequest);
    void deleteProductById(Integer id);

}
