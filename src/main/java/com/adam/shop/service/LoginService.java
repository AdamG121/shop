package com.adam.shop.service;

import com.adam.shop.domain.dto.TokenDto;

public interface LoginService {
    TokenDto login(String email, String passowrd);
}
