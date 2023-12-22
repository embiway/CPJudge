package com.judge.service;

import com.embi.User;
import com.judge.server.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthManager authManager;
    @Override
    public String signup(String userName, char[] password) throws Exception {
        return null;
    }

    @Override
    public boolean authenticateUser(String token) throws Exception {
        User user = new User();
        user.setToken(token);
        return authManager.authenticateUser(user);
    }

    @Override
    public String login(String userName, char[] password) throws Exception {
        return null;
    }
}
