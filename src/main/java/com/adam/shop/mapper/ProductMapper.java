package com.adam.shop.mapper;

import com.adam.shop.domain.dao.Product;
import com.adam.shop.domain.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productDtoToProduct (ProductDto productDto);
    ProductDto productToProductDto (Product product);
}
