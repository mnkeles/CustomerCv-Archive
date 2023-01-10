package com.customercvarchive.controller;

import com.customercvarchive.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal")
@RequiredArgsConstructor
@Slf4j
public class InternalApiController {

    private final UserService userService;

    @PutMapping("/make-admin/{username}")
    public ResponseEntity makeAdmin(@PathVariable String username){
        userService.makeAdmin(username);
        log.info("make admin:{}",username);
        return new ResponseEntity( HttpStatus.OK);
    }
}
