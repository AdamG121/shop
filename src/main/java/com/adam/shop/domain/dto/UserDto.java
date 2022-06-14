package com.adam.shop.domain.dto;

import com.adam.shop.validator.PasswordValid;
import com.adam.shop.validator.groups.Create;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@PasswordValid
@Builder
@JsonInclude (JsonInclude.Include.NON_NULL) // jeśli jakieś pole z klasy UserDto będzie miało wartość null to nie będzie wysyłane do klienta
@Data // generuje getery setery toString equals hashCode i wieloargumentowy konstruktor dla finalnych zmiennych
@NoArgsConstructor // bez-agumentowy konstruktor
@AllArgsConstructor // wieloargumentowy konstruktor
public class UserDto {
    @Null(groups = Create.class) //wskazuje że nie podajemy tego pola przy zapisywaniu
    private Long id;
    @NotBlank // adnotacja tykjo do Stringów - wymaga żeby długość nie była 0 i nie mogą być same spacje
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    private Integer revisionNumber; //co zostało (jakie pola) zmodyfikowane w danej modyfikacji
    //nie podaje się tego przy zapisywaniu użytkownika w postmanie bo to tylko zwracamy, nigdy nie podajemy revision number - wartość wysyłana do użytkownika a nie do serwera
}
