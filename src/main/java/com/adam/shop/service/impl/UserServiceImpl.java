package com.adam.shop.service.impl;

import com.adam.shop.domain.dao.User;
import com.adam.shop.repository.RoleRepository;
import com.adam.shop.repository.UserRepository;
import com.adam.shop.security.SecurityUtils;
import com.adam.shop.service.MailService;
import com.adam.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor //wieloargumentowy konstruktor dla finalnych zmiennych
@Service //rejestruje klasę jako bean w Springu
//inicjalizacja klasy przez Springa - nie musimy podawać konstruktorów (żeby Spring wiedział że sam musi tą klasę zainicjalizować)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; //wstrzyknięcie obiektu i mam dostęp do metod z JPA
    private final PasswordEncoder passwordEncoder; //żeby enkodować hasło przy zapisie użytkownika
    private final RoleRepository roleRepository; //żeby nadać użytkownikowi role
    private final MailService mailService;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); //enkodowanie hasła uzytkownika
        roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Collections.singletonList(role))); //nadanie użytkownikowi roli - defaultowo nadajemy użytkownikowi 1 rolę
        // w findByName podajemy na sztywno rolę bo to my określamy w ramach tej metody jaką nazwę roli możemy nadać użytkownikowi
        // metoda ifPresent bo findByName zwraca Optionala
        user.setActivatedToken(UUID.randomUUID().toString());
        userRepository.save(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", user.getFirstName());
        variables.put("link", "http://localhost:8080/api/users/activate?activatedToken=" + user.getActivatedToken());
        mailService.send(user.getEmail(), "welcome mail", variables, null, null);
        return user;
    }

    @Transactional //jeśli obiekt był modyfikowany to zaktualizuje się sam w bazie danych
    @Override
    public User update(User user, Long id) {
        User userDb = getById(id);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());
        return userDb;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with " + id + " doesn't exist"));
        // w repozytoriach jest taka zasada, że jak zwracamy 1 obiekt to trzeba go opakować w optionala
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> page(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()) //przez nazwe klasy bo metoda getCurrentUserEmail jest statyczna
                .orElseThrow(() -> new EntityNotFoundException("User not logged"));
    }


}
