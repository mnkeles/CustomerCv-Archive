package com.customercvarchive.controller;


import com.customercvarchive.mapper.UserMapper;
import com.customercvarchive.model.User;
import com.customercvarchive.model.dto.UserDto;
import com.customercvarchive.service.AuthenticationService;
import com.customercvarchive.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserDto userDTO ) {
        User user=userMapper.toUser(userDTO);
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return  new ResponseEntity(HttpStatus.CONFLICT);
        }
        log.info("sign up user, Username:{}",userDTO.getUsername());
        return new ResponseEntity(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody UserDto userDTO){
        log.info("sign in user , Username:{}",userDTO.getUsername());
        return  new ResponseEntity(authenticationService.signIn(userMapper.toUser(userDTO)),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        log.info("delete user by username:{}",username);
        return  new ResponseEntity(HttpStatus.OK);
    }


}
