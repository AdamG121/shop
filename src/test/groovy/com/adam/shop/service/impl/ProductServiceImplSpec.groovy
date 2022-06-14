package com.adam.shop.service.impl

import com.adam.shop.config.properties.FilePropertiesConfig
import com.adam.shop.domain.dao.Product
import com.adam.shop.repository.ProductRepository
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDateTime

//class ProductServiceImplSpec extends Specification {
//    def productRepository = Mock(ProductRepository)
//    def filePropertiesConfig = Mock(FilePropertiesConfig)
//
//    def productServiceImpl = new ProductServiceImpl(productRepository, filePropertiesConfig)
//
//    def 'should get product by id'() {
//        given:
//        def id = 10L
//
//        when:
//        productServiceImpl.getById(id)
//
//        then:
//        1 * productRepository.findById(id) >> Optional.of(new Product())
//        0 * _
//    }
//
//    def 'should remove product by id'() {
//        given:
//        def id = 10L
//
//        when:
//        productServiceImpl.removeById(id)
//
//        then:
//        1 * productRepository.deleteById(id)
//        0 * _
//    }
//
//    def 'should get page product'() {
//        given:
//        def pageable = PageRequest.of(1, 10)
//
//        when:
//        productServiceImpl.page(pageable)
//
//        then:
//        1 * productRepository.findAll(pageable)
//        0 * _
//    }
//
//    def 'should save product'() {
//        given:
//        def product = Mock(Product)
//
//        when:
//        productServiceImpl.save(product)
//
//        then:
//        1 * productRepository.save(product)
//        0 * _
//    }
//}
