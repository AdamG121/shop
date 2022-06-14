package com.adam.shop.mapper;


import com.adam.shop.domain.dao.Template;
import com.adam.shop.domain.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") //dzięki tej adnotacji MapStruct potrafi wygenerować implementację do tego interfejsu
public interface TemplateMapper {
    Template templateDtoToTemplate (TemplateDto templateDto);
    TemplateDto templateToTemplateDto (Template template);
}
