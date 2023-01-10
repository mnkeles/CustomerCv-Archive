package com.customercvarchive.service.impl;

import com.customercvarchive.exception.EntityNotFoundException;
import com.customercvarchive.model.User;
import com.customercvarchive.security.UserPrincipal;
import com.customercvarchive.security.jwt.JwtProvider;
import com.customercvarchive.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public User signIn(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        // authenticationManager ın arka tarafta yaptığı iş  UserDetailsService den loadByUsername service i çağırır


        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (userPrincipal == null) {
            throw new EntityNotFoundException("User", userPrincipal.getId());
        }

        String jwt = jwtProvider.generateToken(userPrincipal);   //    burada authenticate olan kullanıcıya bir jwt token oluşturuyoruz

        User signInUser = userPrincipal.getUser();

        signInUser.setToken(jwt);

        return signInUser;
    }
}
