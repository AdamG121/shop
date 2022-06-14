package com.adam.shop.controller;

import com.adam.shop.domain.dao.Template;
import com.adam.shop.domain.dto.ProductDto;
import com.adam.shop.domain.dto.TemplateDto;
import com.adam.shop.domain.dto.UserDto;
import com.adam.shop.mapper.TemplateMapper;
import com.adam.shop.repository.TemplateRepository;
import com.adam.shop.service.TemplateService;
import com.adam.shop.validator.groups.Create;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated //jak korzystamy z do metody to do klasy też musimy dodać tą adnotację
@RequestMapping("/api/templates")
@RequiredArgsConstructor
@RestController
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @Validated(Create.class)
    @PostMapping
    public TemplateDto createTemplate(@RequestBody @Valid TemplateDto templateDto) {
        return templateMapper.templateToTemplateDto(templateService.save(templateMapper.templateDtoToTemplate(templateDto)));
    }


    @GetMapping("/{id}")
    public TemplateDto getTemplateById(@PathVariable Long id) {
        return templateMapper.templateToTemplateDto(templateService.getById(id));
    }


    @PutMapping("/{id}")
    public Template updateTemplate(@RequestBody @Valid Template template, @PathVariable Long id) {
        return templateService.update(template, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void removeTemplate(@PathVariable Long id) {
        templateService.removeById(id);
    }


    @GetMapping
    public Page<Template> pageTemplate(@RequestParam int size, @RequestParam int page) {
        return templateService.page(PageRequest.of(page, size));
    }
}

