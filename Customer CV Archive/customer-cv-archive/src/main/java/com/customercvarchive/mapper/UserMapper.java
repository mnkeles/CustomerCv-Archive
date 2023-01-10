package com.customercvarchive.mapper;

import com.customercvarchive.model.User;
import com.customercvarchive.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDto userDto){
        return User.builder().userId(userDto.getUserId()).username(userDto.getUsername())
                .password(userDto.getPassword()).createTime(userDto.getCreateTime())
                .role(userDto.getRole()).build();
    }

    public UserDto toUserDto(User user){
        return UserDto.builder().userId(user.getUserId()).username(user.getUsername())
                .password(user.getPassword()).createTime(user.getCreateTime())
                .role(user.getRole()).build();
    }
}
