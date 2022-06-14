package com.adam.shop.service;

import com.adam.shop.domain.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user); // zapisywanie użytkownika do bazy danych

    User update(User user, Long id); // aktualizacja użytkownika

    User getById(Long id); // pobieranie użytkownika po id

    void removeById(Long id); // usuwanie użytkownika po id

    Page<User> page(Pageable pageable); // pobieranie poage - dzielenie danych np. kilka stron kategorii w Allegro

    User getCurrentUser();
}
