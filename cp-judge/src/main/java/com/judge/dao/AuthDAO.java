package com.judge.dao;

import com.embi.User;

import java.util.Optional;

public interface AuthDAO {
    void addUser(User user);
    Optional<User> getUser(String userName);
}
