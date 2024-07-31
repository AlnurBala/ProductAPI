package com.product.productapi.mapper;


import com.product.productapi.domain.Products;
import com.product.productapi.dto.request.ProductsRequest;
import com.product.productapi.dto.response.ProductsResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface ProductsMapper {
    Products fromDTO(ProductsRequest productsRequest);
    ProductsResponse toDTO(Products products);

    @Mapping(target = "productName", source = "productName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    Products mapUpdateRequestToEntity(@MappingTarget Products products, ProductsRequest productsRequest);

    default Page<ProductsResponse> toDTOp(Page<Products> products) {
        List<ProductsResponse> propertyResponsDtos = products
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(propertyResponsDtos, products.getPageable(), products.getTotalElements());
    }
}



