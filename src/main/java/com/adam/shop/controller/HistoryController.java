package com.adam.shop.controller;

import com.adam.shop.domain.dto.ProductDto;
import com.adam.shop.domain.dto.UserDto;
import com.adam.shop.mapper.HistoryMapper;
import com.adam.shop.repository.ProductRepository;
import com.adam.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

//w tym kontrolerze będziemy pobierać historię wszystkich obiektów - bez podziału na historie poszczególnych klas

@RequiredArgsConstructor
@RequestMapping("/api/history")
@RestController
public class HistoryController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryMapper historyMapper;


    //wyświetlanie histori zmian wprowadzonych do danego użytkownika
    @GetMapping("/user/{id}")
    public Page<UserDto> getUserHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) { //bo klasa która trworzy Pageable przyjmuje typy proste
        return userRepository.findRevisions(id, PageRequest.of(page, size))
                .map(historyMapper::revisionToUserDto); //tworzenie Pageable w drugim parametrze na podstawie page i size - ważna kolejność
    }

    @GetMapping("/product/{id}")
    public Page<ProductDto> getProductHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size))
                .map(historyMapper::revisionToProductDto);
    }

}

//@GetMapping("/user/{id}")
//Page<Revision<Integer, User>> getUserHistory(@PathVariable Long id, @RequestParam int page,@RequestParam int size) { //bo klasa która trworzy Pageable przyjmuje typy proste
//    return userRepository.findRevisions(id, PageRequest.of(page, size)); //tworzenie Pageable w drugim parametrze na podstawie page i size - ważna kolejność
//}


