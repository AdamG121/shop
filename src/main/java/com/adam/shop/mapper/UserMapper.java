package com.adam.shop.mapper;

import com.adam.shop.domain.dao.User;
import com.adam.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") //dzięki tej adnotacji MapStruct potrafi wygenerować implementację do tego interfejsu
public interface UserMapper {
    User userDtoToUser (UserDto userDto);
    @Mapping(target = "password", ignore = true) //pole klasy ignorowane przy wysyłaniu danych uzytkownikowi w controller
    UserDto userToUserDto (User user);
}
