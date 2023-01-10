package com.customercvarchive.service.impl;

import com.customercvarchive.exception.EntityNotFoundException;
import com.customercvarchive.mapper.UserMapper;
import com.customercvarchive.model.Role;
import com.customercvarchive.model.User;
import com.customercvarchive.model.dto.UserDto;
import com.customercvarchive.repository.UserRepository;
import com.customercvarchive.security.UserPrincipal;
import com.customercvarchive.security.jwt.JwtProvider;
import com.customercvarchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDto saveUser(UserDto userDto) {
        userDto.setCreateTime(LocalDateTime.now());
        userDto.setRole(Role.USER);
        User user = userMapper.toUser(userDto);
        userRepository.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void makeAdmin(String username) {
        if (findByUsername(username) == null || findByUsername(username).equals(""))
            throw new EntityNotFoundException("File", username);
        userRepository.updateUserRole(username, Role.ADMIN);
    }

    @Override
    public void deleteUser(String username) {
        User byUsername = userRepository.getByUsername(username);
        if (byUsername == null || byUsername.equals("")) {
            throw new EntityNotFoundException("User", "username : " + username);
        } else if (byUsername.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("No permission to delete user : " + username);
        }

    }



}
