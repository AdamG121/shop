package com.adam.shop.validator.impl

import com.adam.shop.domain.dto.UserDto
import spock.lang.Specification

class PasswordValidatorSpec extends Specification {
    //zmienna aby odwołać się do niestatycznej metody
    def passwordValidator = new PasswordValidator()

    def 'should test password validator'() {
        //zmienne potrzebne do przetestowania metody
        given:
        //trzeba określić jakie pola UserDto będą nam potrzebne
        def userDto = new UserDto(password: password, confirmPassword: confirmPassword)

        //wywołanie metody
        when:
        def result = passwordValidator.isValid(userDto, null)

        //sprwdzenie wyniku metody
        //każda linijka musi zwrócić boolean
        //
        then:
        result == expected

        where:
        // znak || oddziela input od output'u
        password | confirmPassword || expected
        'haslo'  | 'haslo'         || true //test przeszedł
        'haslo'  | 'haslo1'        || false //test nie przeszedł
    }
}
