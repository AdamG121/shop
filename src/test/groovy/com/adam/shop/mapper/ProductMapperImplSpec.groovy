package com.adam.shop.mapper

import com.adam.shop.domain.dao.Product
import com.adam.shop.domain.dto.ProductDto
import spock.lang.Specification

import java.time.LocalDateTime

class ProductMapperImplSpec extends Specification {
    def productMapperImpl = new ProductMapperImpl()

    def 'productDtoToProduct test'() {
        given:
        def productDto = new ProductDto(12l, 'smart watch', 599.99, 10, 'balck', 1)

        when:
        def result = productMapperImpl.productDtoToProduct(productDto)

        then:
        //musimy sprawdzić wszystkie pola zmiennej result
        result.id == productDto.id
        result.name == productDto.name
        result.price == productDto.price
        result.quantity == productDto.quantity
        result.description == productDto.description
        result.url == null
        result.createdBy == null
        result.createdDate == null
        result.lastModifiedBy == null
        result.lastModifiedDate == null
    }

    def 'productToProductDto test' (){
        given:
        def product = new Product(10l, 'computer', 1999.9, 20, 'computer', 'http://www.computer.pl', 'admin',
                LocalDateTime.now(), 'admin', LocalDateTime.now())

        when:
        def result = productMapperImpl.productToProductDto(product)

        then:
        //musimy sprawdzić wszystkie pola zmiennej result
        result.id == product.id
        result.name == product.name
        result.price == product.price
        result.quantity == product.quantity
        result.description == product.description
        result.revisionNumber == null

    }
}
