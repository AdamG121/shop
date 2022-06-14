package com.adam.shop.security;

import com.adam.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    //sprawdzenie czy aktualnie zalogowany użytkownik ma dostęp do Usera
    public boolean hasAccessToUser(Long userId) {
        return userService.getCurrentUser().getId().equals(userId);
    }
}
