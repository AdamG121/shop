package com.adam.shop.mapper

import com.adam.shop.domain.dao.Role
import com.adam.shop.domain.dao.User
import com.adam.shop.domain.dto.UserDto
import org.hibernate.envers.DefaultRevisionEntity
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata
import org.springframework.data.history.Revision
import spock.lang.Specification

import java.time.LocalDateTime

class HistoryMapperImplSpec extends Specification{
    def historyMapper = new HistoryMapperImpl()

    def 'should map revision to UserDto'(){
        given:
        //w Groovy'm można zapisać listę w nawiasach [] i stworzyć w nich obiekt
        def user = new User(123l, 'Jan','Kowalski', 'jan.kowalski@gmail.com', 'haslo',
                'admin', LocalDateTime.now(), 'admin', LocalDateTime.now(), [new Role(1,'user')])
        def revision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity()), user)

        when:
        def result = historyMapper.revisionToUserDto(revision)

        then:
        //trzeba sprawdzić wszystkie pola klasy UserDto (result)
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        //gdy mapujemy do Dto to nigdy nie powinniśmy pokazywać użytkownikowi hasła - password i confirmPassword muszą być null
        result.password == null
        result.confirmPassword == null
        result.revisionNumber == 0

    }
}
