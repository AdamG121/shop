package com.adam.shop.service;

import com.adam.shop.domain.dao.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    @CachePut(cacheNames = "product", key = "#result.id") //wstawianie / nadpisywanie
    Product save(Product product);

    @CachePut(cacheNames = "product", key = "#id") //odwołujemy się do parametru funkcji
    Product update(Product product, Long id, MultipartFile file);

    @Cacheable(cacheNames = "product", key = "#id") //wstawienie lub pobieranie z cascha
    Product getById(Long id);

    @CacheEvict(cacheNames = "product", key = "#id")
    void removeById(Long id);

    Page<Product> page(Pageable pageable);
}
