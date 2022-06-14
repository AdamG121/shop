//package com.adam.shop.mapper.impl;
//
//import com.adam.shop.domain.dao.Product;
//import com.adam.shop.domain.dto.ProductDto;
//import com.adam.shop.mapper.ProductMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductMapperImpl implements ProductMapper {
//    @Override
//    public Product productDtoToProduct(ProductDto productDto) {
//        return Product.builder()
//                .id(productDto.getId())
//                .name(productDto.getName())
//                .price(productDto.getPrice())
//                .quantity(productDto.getQuantity())
//                .description(productDto.getDescription())
//                .build();
//    }
//
//    @Override
//    public ProductDto productToProductDto(Product product) {
//        return ProductDto.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .price(product.getPrice())
//                .quantity(product.getQuantity())
//                .description(product.getDescription())
//                .build();
//    }
//}
