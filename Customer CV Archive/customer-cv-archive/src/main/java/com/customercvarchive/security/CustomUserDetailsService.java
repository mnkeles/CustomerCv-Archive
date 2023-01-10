package com.customercvarchive.security;

import com.customercvarchive.model.User;
import com.customercvarchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));


        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name())); // Bir kullanıcının birden fazla rolü olabilir


        return UserPrincipal.builder()
                .user(user).id(user.getUserId())
                .username(username)
                //.password(user.getPassword())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                // Database de şifreleri, şifreli olarak saklamak için bunu yaptık üstteki gibi yaparsak şifresiz olarak saklanıyordu
                .authorities(authorities)
                .build();

       /* return new org.springframework.security.core.userdetails.User(user.getName(),
                new BCryptPasswordEncoder().encode(user.getPassword()), getGrantedAuth(user));

        */
    }
}


