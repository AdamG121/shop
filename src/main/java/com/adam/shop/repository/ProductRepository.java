package com.adam.shop.repository;

import com.adam.shop.domain.dao.Product;
import com.adam.shop.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, RevisionRepository<Product, Long, Integer> {
    //RevisionRepository jest do auditingu
}
