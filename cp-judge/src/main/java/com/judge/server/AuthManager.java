package com.judge.server;

import com.embi.Token;
import com.embi.User;

public interface AuthManager {
    User signup(User user) throws Exception;
    boolean authenticateUser(User user) throws Exception;
    String login(User user) throws Exception;
}
