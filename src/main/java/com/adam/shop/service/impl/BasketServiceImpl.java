package com.adam.shop.service.impl;

import com.adam.shop.domain.dao.Basket;
import com.adam.shop.domain.dao.Product;
import com.adam.shop.exception.ProductQuantityExceededException;
import com.adam.shop.repository.BasketRepository;
import com.adam.shop.repository.ProductRepository;
import com.adam.shop.security.SecurityUtils;
import com.adam.shop.service.BasketService;
import com.adam.shop.service.ProductService;
import com.adam.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void addProduct(Long productId, Integer quantity) {
        Product product = productService.getById(productId);
        if (product.getQuantity() >= quantity) {
            basketRepository.save(Basket.builder() //dodawanie to jak zapisywanie w repozytorium
                    .product(product)
                    .user(userService.getCurrentUser())
                    .quantity(quantity)
                    .build());
        } else
            throw new ProductQuantityExceededException("Przekroczona dostępna ilość");
    }

    @Override
    public void update(Long productId, Integer quantity) {
        String currentUserEmail = SecurityUtils.getCurrentUserEmail();
        basketRepository.findByProductIdAndUserEmail(productId, currentUserEmail);
    }

    @Override
    public void removeProduct(Long productId) {
        String currentUserEmail = SecurityUtils.getCurrentUserEmail();
        basketRepository.deleteByProductIdAndUserEmail(productId, currentUserEmail);
    }

    @Override
    public List<Product> getBasket() {
        return basketRepository.findByUserEmail(SecurityUtils.getCurrentUserEmail()).stream()
                .map(basket -> {
                    Product product = basket.getProduct();
                    product.setQuantity(basket.getQuantity());
                    return product;
                })
                .collect(Collectors.toList());
    }
}
