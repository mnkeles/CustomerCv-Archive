package com.customercvarchive.service;

import com.customercvarchive.model.User;
import com.customercvarchive.model.dto.UserDto;

import java.util.Optional;

public interface UserService {


    UserDto saveUser(UserDto userDto);

    Optional<User> findByUsername(String username);

    void makeAdmin(String username);

    void deleteUser(String username);
}
