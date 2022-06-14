package com.adam.shop.controller;

import com.adam.shop.domain.dao.Product;
import com.adam.shop.domain.dao.User;
import com.adam.shop.domain.dto.ProductDto;
import com.adam.shop.domain.dto.UserDto;
import com.adam.shop.mapper.ProductMapper;
import com.adam.shop.service.ProductService;

import com.adam.shop.validator.groups.Create;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PreAuthorize("isAuthenticated()")//sprawdzenie czy użytkownik jest zalogowany, w cudzysłowiu piszemy językiem skryptowym nazywającym się SPELL (Spring Expression Language)
    // isAnonymous() - użytkonik nie może być zalogowany
    // isAuthenticated() - użytkoniwk musi być zalogowany
    // hasRole() - uzytkownik musi mieć Rolę
    @Validated(Create.class)
    @PostMapping
    public ProductDto saveProduct(@RequestBody @Valid ProductDto product) {
        return productMapper.productToProductDto(productService.save(productMapper.productDtoToProduct(product)));
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productMapper.productToProductDto(productService.getById(id));
    }

//    @PutMapping("/{id}")
//    public ProductDto updateProduct(@RequestBody @Valid ProductDto product, @PathVariable Long id) {
//        return productMapper.productToProductDto(productService.update(productMapper.productDtoToProduct(product), id));
//    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestPart @Valid ProductDto product, @PathVariable Long id,
                                    @RequestPart (required = false) MultipartFile file) { //oznacza że zmienna jest opcjonalna
        return productMapper.productToProductDto(productService.update(productMapper.productDtoToProduct(product), id, file));
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable Long id){
        productService.removeById(id);
    }

    @GetMapping
    public Page<ProductDto> pageProduct(@RequestParam int size, @RequestParam int page){
        return productService.page(PageRequest.of(page, size))
                .map(productMapper::productToProductDto);
    }
}
