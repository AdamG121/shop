package com.adam.shop.controller;

import com.adam.shop.domain.dao.User;
import com.adam.shop.domain.dto.UserDto;
import com.adam.shop.mapper.UserMapper;
import com.adam.shop.service.UserService;
import com.adam.shop.validator.groups.Create;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated //klasa też musi być oznaczona adnotacją @Validated żeby ta adnotacja działała przy metodach
@RequiredArgsConstructor // wieloargumentowy konstruktor dla zmiennych finalnych - do wstrzyknięcia zmiennej
@RequestMapping("/api/users") //
@RestController // rejestruje klase jako bean w Springu - to oznacza że będziemy deklarować end-point (np link w Post Man) w tej klasie
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/activate")
    public void activateUser(@RequestParam String activatedToken){

    }

    @ResponseStatus(HttpStatus.CREATED)
    @Validated(Create.class) //włączamy walidatory, które są ustawione na klasę Create
    @PostMapping //nadanie metodzie metody http, zapisywanie
    public UserDto saveUser(@RequestBody @Valid UserDto user) { //adnotacja mówi, że będziemy przesyłać JSONa w requeście do servera, który reprezentuje klasę User
        // trzeba dodać adnotację valid, żeby działały dodane validacje w pakiecie domain
        return userMapper.userToUserDto(userService.save(userMapper.userDtoToUser(user)));
    }

    @Operation (security = @SecurityRequirement(name = "token"))
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))") //adnotacja sprawdzająca dostęp do metody
    // adnotacja @PreAuthorised ma defaultowe metody
    // isAuthenticated() - sprawdza czy użytkownik jest zalogowany
    // czy użytkownik ma dostęp do Usera którego chce pobrać lub czy ma role admina
    //@securityService.hasAccessToUser(#id) - żeby odwołać się do metody napisanej w klasie SecurityService
    // - zaczynam od @ i piszę nazwę klasy z małej litery zgodnie z camelCase i  po kropce odwołuje się do metody

    //isAuthenticated i hasRole to defaultowe metody adnotacji @PreAuthorize
    @GetMapping("/{id}") //adnotacja do metody http get
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.userToUserDto(userService.getById(id));
    } //adnotacja do zmiennej z linku

    @Operation (security = @SecurityRequirement(name = "token"))
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @PutMapping("/{id}") //gdy zmienna z parametru funkcji jest oznaczona adnotacją PathVariable wtedy zawsze musimy w linku w {} podać nazwe zmiennej
    public UserDto updateUser(@RequestBody @Valid UserDto user, @PathVariable Long id) {
         return userMapper.userToUserDto(userService.update(userMapper.userDtoToUser(user), id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation (security = @SecurityRequirement(name = "token"))
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id){
        userService.removeById(id);
    }

    @Operation (security = @SecurityRequirement(name = "token"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<UserDto> pageUser(@RequestParam int size,@RequestParam int page){ //z defaultu mappuje się do ?page=0&size=10 - tą adnotację dajemy przed argumentami do pageable
        return userService.page(PageRequest.of(page, size))
                .map(userMapper::userToUserDto); //tworzenie Pageable
    }
}
