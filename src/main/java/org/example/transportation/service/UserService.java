package org.example.transportation.service;

import org.example.transportation.dto.UserDto;
import org.example.transportation.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
