package com.adam.shop.mapper

import com.adam.shop.domain.dao.Role
import com.adam.shop.domain.dao.User
import com.adam.shop.domain.dto.UserDto
import spock.lang.Specification

import java.time.LocalDateTime

class UserMapperImplSpec extends Specification {
    def userMapperImpl = new UserMapperImpl()

    def 'userDtoToUser test'() {
        given:
        def userDto = new UserDto(123l, 'Jan', 'Kowalski', 'jan.kowalski@gmail.com', 'haslo', 'haslo', 1)

        when:
        def result = userMapperImpl.userDtoToUser(userDto)

        then:
        //musimy sprawdzić wszystkie pola zmiennej result
        result.id == userDto.id
        result.firstName == userDto.firstName
        result.lastName == userDto.lastName
        result.email == userDto.email
        result.password == userDto.getPassword()
        result.createdBy == null
        result.createdDate == null
        result.lastModifiedBy == null
        result.lastModifiedDate == null
        result.roles == null
    }

    def 'userToUserDto test'() {
        given:
        def user = new User(123l, 'Piotr', 'Nowak', 'piotr.nowak@gmail.com', 'haslo',
                'admin', LocalDateTime.now(), 'admin', LocalDateTime.now(), [new Role(1,'user')])

        when:
        def result = userMapperImpl.userToUserDto(user)

        then:
        //musimy sprawdzić wszystkie pola zmiennej result
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        result.password == null
        result.confirmPassword == null
        result.revisionNumber == null

    }
}
