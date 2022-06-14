package com.adam.shop.controller;


import com.adam.shop.flyweight.generic.GenericFactory;
import com.adam.shop.flyweight.generic.strategy.generator.FileGeneratorStrategy;
import com.adam.shop.flyweight.model.FileType;
import com.adam.shop.flyweight.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RequiredArgsConstructor
@RequestMapping("/api/files")
@RestController
public class FileController {
    private final GeneratorFactory generatorFactory;
    private final GenericFactory <FileType, FileGeneratorStrategy> genericFactory;

    @GetMapping
    public void getFile(@RequestParam FileType fileType){
        generatorFactory.getStrategyByType(fileType).generateFile();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/generic")
    ResponseEntity<byte[]> generateFile(@RequestParam FileType fileType){
        byte[] file = genericFactory.getStrategyByType(fileType).generateFile();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, Integer.toString(file.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachments;filename=report." + fileType.name().toLowerCase(Locale.ROOT));
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }
}
