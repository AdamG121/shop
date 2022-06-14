package com.adam.shop.service.impl;

import com.adam.shop.domain.dao.Template;
import com.adam.shop.repository.TemplateRepository;
import com.adam.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public Template findByName(String name) {
        return templateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Template with " + name + " doesn't exist"));
    }

    public Template save(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public void removeById(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    public Template getById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template with " + id + " doesn't exist"));
    }

    @Transactional
    @Override
    public Template update(Template template, Long id) {
        Template templateDb = getById(id);
        templateDb.setName(template.getName());
        templateDb.setBody(template.getBody());
        templateDb.setSubject(template.getSubject());
        return templateDb;
    }

    @Override
    public Page<Template> page(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }
}
