package com.adam.shop.domain.dto;

import com.adam.shop.validator.groups.Create;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // jeśli jakieś pole z klasy ProductDto będzie miało wartość null to nie będzie wysyłane do klienta
@Data // generuje getery setery toString equals hashCode i wieloargumentowy konstruktor dla finalnych zmiennych
@NoArgsConstructor // bez-agumentowy konstruktor
@AllArgsConstructor // wieloargumentowy konstruktor
public class ProductDto {
    @Null(groups = Create.class)
    private Long id;
    @NotBlank
    private String name;
    @Positive
    private Double price;
    @Positive
    private Integer quantity;
    @NotBlank
    private String description;
    private Integer revisionNumber;
}
