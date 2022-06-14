package com.adam.shop.service;

import com.adam.shop.domain.dao.Basket;
import com.adam.shop.domain.dao.Product;

import java.util.List;

public interface BasketService {
    void addProduct(Long productId, Integer quantity);

    void update(Long productId, Integer quantity);

    void removeProduct(Long productId);

    List<Product> getBasket(); //zwracamy listę produktów z koszyka
}
