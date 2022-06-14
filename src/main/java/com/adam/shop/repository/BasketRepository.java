package com.adam.shop.repository;

import com.adam.shop.domain.dao.Basket;
import com.adam.shop.domain.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long>, RevisionRepository<Basket, Long, Integer> {
    List<Basket> findByUserEmail(String email); //lista wpisów
    Optional<Basket> findByProductIdAndUserEmail(Long productId, String email);

    void deleteByProductIdAndUserEmail(Long productId, String email);
}
