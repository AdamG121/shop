package com.adam.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
// jeśli jakieś pole z klasy UserDto będzie miało wartość null to nie będzie wysyłane do klienta
@Data // generuje getery setery toString equals hashCode i wieloargumentowy konstruktor dla finalnych zmiennych
@NoArgsConstructor // bez-agumentowy konstruktor
@AllArgsConstructor // wieloargumentowy konstruktor
public class TokenDto {
    private String token;
}
