package com.adam.shop.service;

import com.adam.shop.domain.dao.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemplateService {
    Template findByName(String name);

    Template save (Template template);

    void removeById(Long id);

    Template getById(Long id);

    Template update(Template template, Long id);

    Page<Template> page(Pageable pageable);
}
