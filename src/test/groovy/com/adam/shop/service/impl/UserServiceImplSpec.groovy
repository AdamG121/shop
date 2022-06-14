package com.adam.shop.service.impl

import com.adam.shop.domain.dao.Role
import com.adam.shop.domain.dao.User
import com.adam.shop.repository.RoleRepository
import com.adam.shop.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.persistence.EntityNotFoundException
import java.time.LocalDateTime

class UserServiceImplSpec extends Specification {
    def userRepository = Mock(UserRepository) //wstrzyknięcie obiektu i mam dostęp do metod z JPA
    def passwordEncoder = Mock(PasswordEncoder) //żeby enkodować hasło przy zapisie użytkownika
    def roleRepository = Mock(RoleRepository) //żeby nadać użytkownikowi role

    def userService = new UserServiceImpl(userRepository, passwordEncoder, roleRepository)

    def 'should get user by id'() {
        given:
        def id = 10L

        when:
        userService.getById(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(new User()) // >> operator do mokowania wartości funkcji
        //bez Usera wywaliłby nam exception
        0 * _

    }

    def 'should remove user by id'() {
        given:
        def id = 10L

        when:
        userService.removeById(id)

        then:
        1 * userRepository.deleteById(id)
        0 * _

    }

    def 'should get page user'() {
        given:
        def pageable = PageRequest.of(1, 10)

        when:
        userService.page(pageable)

        then:
        1 * userRepository.findAll(pageable)
        0 * _
    }

    def 'should save user'() {
        given:
        def user = Mock(User)
        def role = new Role()

        when:
        userService.save(user)

        then:
        1 * user.getPassword() >> 'haslo'
        1 * passwordEncoder.encode('haslo') >> 'haslo1'
        1 * user.setPassword('haslo1')
        1 * roleRepository.findByName("ROLE_USER") >> Optional.of(role)
        1 * user.setRoles(Collections.singletonList(role))
        1 * userRepository.save(user)
        0 * _
    }

    def 'should update user'() {
        given:
        def user = new User(123l, 'Jan', 'Kowalski', 'jan.kowalski@gmail.com', 'haslo',
                'admin', LocalDateTime.now(), 'admin', LocalDateTime.now(), [new Role(1, 'user')])
        def userDb = new User(123l, 'Piotr', 'Nowak', 'piotr.nowak@gmail.com', 'haslo',
                'admin', LocalDateTime.now(), 'admin', LocalDateTime.now(), [new Role(1, 'user')])
        def id = 123l
        //mockowania metody nie moglibyśmy przeprowadzić w then
        userRepository.findById(id) >> Optional.of(userDb)

        when:
        def result = userService.update(user, id)

        then:

        result.getFirstName() == user.getFirstName()
        result.getLastName() == user.getLastName()
        result.getEmail() == user.getEmail()
    }

    def 'should not get current user'(){
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)
        def authentication = Mock(Authentication)
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> 'admin'
        userRepository.findByEmail('admin') >> Optional.empty()

        when:
        userService.getCurrentUser()

        then:
        def exception = thrown EntityNotFoundException
        exception.message == 'User not logged'
    }
}
