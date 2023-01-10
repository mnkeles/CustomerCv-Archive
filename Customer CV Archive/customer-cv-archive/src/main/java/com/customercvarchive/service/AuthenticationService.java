package com.customercvarchive.service;

import com.customercvarchive.model.User;

public interface AuthenticationService {
    User signIn(User user);
}
