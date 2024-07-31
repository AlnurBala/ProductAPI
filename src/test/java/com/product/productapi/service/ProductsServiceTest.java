package com.product.productapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.product.productapi.domain.Products;
import com.product.productapi.dto.request.ProductsRequest;
import com.product.productapi.dto.response.ProductsResponse;
import com.product.productapi.mapper.ProductsMapper;
import com.product.productapi.repo.ProductsRepository;
import com.product.productapi.service.impl.ProductsServiceImpl;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private ProductsMapper productsMapper;

    @InjectMocks
    private ProductsServiceImpl productsService;

    public ProductsServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        ProductsResponse response = new ProductsResponse(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);
        Page<ProductsResponse> page = new PageImpl<>(Collections.singletonList(response));
        Pageable pageable = Pageable.unpaged();

        when(productsRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(new Products())));
        when(productsMapper.toDTOp(any())).thenReturn(page);

        Page<ProductsResponse> result = productsService.getAllProducts(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).productName()).isEqualTo("Product 1");
    }

    @Test
    void testCreateProduct() {
        ProductsRequest request = new ProductsRequest(null, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);
        Products product = new Products(null, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);
        ProductsResponse response = new ProductsResponse(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);

        when(productsMapper.fromDTO(request)).thenReturn(product);
        when(productsRepository.save(product)).thenReturn(product);
        when(productsMapper.toDTO(product)).thenReturn(response);

        ProductsResponse result = productsService.createProduct(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void testUpdateProduct() {
        ProductsRequest request = new ProductsRequest(1, "Product 1", "Updated Description", BigDecimal.valueOf(15.0), 150);
        Products existingProduct = new Products(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100);
        Products updatedProduct = new Products(1, "Product 1", "Updated Description", BigDecimal.valueOf(15.0), 150);
        ProductsResponse response = new ProductsResponse(1, "Product 1", "Updated Description", BigDecimal.valueOf(15.0), 150);

        // Mock repository and mapper behavior
        when(productsRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productsMapper.mapUpdateRequestToEntity(existingProduct, request)).thenReturn(existingProduct);
        when(productsRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productsMapper.toDTO(updatedProduct)).thenReturn(response);

        // Execute the method
        ProductsResponse result = productsService.updateProduct(1, request);

        // Verify the result
        assertThat(result).isEqualTo(response);
    }


    @Test
    void testDeleteProductById() {
        Integer id = 1;

        when(productsRepository.existsById(id)).thenReturn(true);
        doNothing().when(productsRepository).deleteById(id);

        productsService.deleteProductById(id);

        verify(productsRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductById_Success() {
        Integer id = 1;

        when(productsRepository.existsById(id)).thenReturn(true);

        productsService.deleteProductById(id);

        verify(productsRepository).deleteById(id);
    }

}
