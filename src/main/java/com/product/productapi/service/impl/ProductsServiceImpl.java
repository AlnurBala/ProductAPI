package com.product.productapi.service.impl;

import com.product.productapi.domain.Products;
import com.product.productapi.dto.request.ProductsRequest;
import com.product.productapi.dto.response.ProductsResponse;
import com.product.productapi.mapper.ProductsMapper;
import com.product.productapi.repo.ProductsRepository;
import com.product.productapi.service.ProductsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Override
    public Page<ProductsResponse> getAllProducts(Pageable pageable) {
        var productsEntity = productsRepository.findAll(pageable);
        return productsMapper.toDTOp(productsEntity);
    }

    @Override
    public ProductsResponse createProduct(ProductsRequest productsRequest) {
        var productsEntity = productsMapper.fromDTO(productsRequest);
        productsEntity = productsRepository.save(productsEntity);
        return productsMapper.toDTO(productsEntity);
    }

    @Override
    public ProductsResponse updateProduct(Integer id, ProductsRequest productsRequest) {
        var existingProduct = productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productsMapper.mapUpdateRequestToEntity(existingProduct, productsRequest);
        var updatedProduct = productsRepository.save(existingProduct);
        return productsMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(Integer id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product not found");
        }
    }
}
