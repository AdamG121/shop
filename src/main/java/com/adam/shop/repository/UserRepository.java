package com.adam.shop.repository;

import com.adam.shop.domain.dao.Role;
import com.adam.shop.domain.dao.User;
import org.hibernate.envers.RevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User, Long, Integer> {
    //Integer - typ kolumny Rev

    // zapytanie szukajaca Usera po emailu
    Optional<User> findByEmail(String email);

    // List<User> findByNameIn(String email) - jakbyśmy chcieli listę użytkowników
}
