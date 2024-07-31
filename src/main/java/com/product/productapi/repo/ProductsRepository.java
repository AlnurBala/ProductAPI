package com.product.productapi.repo;

import com.product.productapi.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Integer> {

}
