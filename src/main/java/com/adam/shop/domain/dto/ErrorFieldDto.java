package com.adam.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@JsonInclude(JsonInclude.Include.NON_NULL) // jeśli jakieś pole z klasy ProductDto będzie miało wartość null to nie będzie wysyłane do klienta
@Data // generuje getery setery toString equals hashCode i wieloargumentowy konstruktor dla finalnych zmiennych
@AllArgsConstructor // wieloargumentowy konstruktor
public class ErrorFieldDto {
    private String message; //komunikat błędu
    private String field; //jakiego pola klasy dotyczy błąd
}
