package com.adam.shop.security;

import com.adam.shop.domain.dao.Role;
import com.adam.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //oznaczamy klasę jako bean w Springu
@RequiredArgsConstructor //wstrzykiwanie
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // dostarczenie użytkownika z bazy danych do logowania na podstawie email

    // 1) musimy znaleźć użytkownika w bazie danych na podstawie podanego maila
    // UserDetails jest interfejsem, a implementacją tego interfejsu jest klasa User z pakietu security
    // z polami username, password i Collection (reprezentujące Rolę usera)
    // w funkcji map wywołuje kontruktor klasy User z pakietu Security
    // role musimy przemapować na SimpleGrantedAuthority
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new User(email, user.getPassword(), user.getRoles().stream() //security zawsze przyjmuje że może być kilka ról
                        .map(role -> new SimpleGrantedAuthority(role.getName()))// rola musi być przemapowana na SimpleGrantedAuthority bo
                        // taki jest wymóg Spring security
                        .collect(Collectors.toSet())))
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika na podstawie podanego maila"));
    }

}
