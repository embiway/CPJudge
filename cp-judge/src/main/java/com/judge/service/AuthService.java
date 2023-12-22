package com.judge.service;

import com.embi.User;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public interface AuthService {
    @RequestMapping("/signup")
    String signup(@RequestHeader(value = "userName") String userName, @RequestHeader(value = "password") char[] password) throws Exception;
    @RequestMapping("/authenticate")
    boolean authenticateUser(@RequestHeader(value = "Authorization") String token) throws Exception;
    @RequestMapping("/login")
    String login(@RequestHeader(value = "userName") String userName, @RequestHeader(value = "password") char[] password) throws Exception;
}
