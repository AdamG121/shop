package com.adam.shop.repository;

import com.adam.shop.domain.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // zapytanie szukające rolę po nazwie
    // nazwa roli -> zgodnie z klasą Role - pole String name
    Optional<Role> findByName(String name); //zapytanie bazodanowe - jeśli zapytanie zwraca więcej niż 1 element to jest lista, a jeśli ma maksymalnie 1 elemnt to jest optinal

    //implementacja powyższej metody sama się generuje na podstawie nazwy
}
