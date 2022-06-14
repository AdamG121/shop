//package com.adam.shop.mapper.impl;
//
//import com.adam.shop.domain.dao.User;
//import com.adam.shop.domain.dto.UserDto;
//import com.adam.shop.mapper.UserMapper;
//import org.springframework.stereotype.Component;
//
//@Component //bo nie będzie komunikacji z klientem ani logiki biznesowej
//public class UserMapperImpl implements UserMapper {
//    @Override
//    public User userDtoToUser(UserDto userDto) {
//        return User.builder()
//                .id(userDto.getId())
//                .firstName(userDto.getFirstName())
//                .lastName(userDto.getLastName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .build();
//    }
//
//    @Override
//    public UserDto userToUserDto(User user) {
//        return UserDto.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .build();
//    }
//}
