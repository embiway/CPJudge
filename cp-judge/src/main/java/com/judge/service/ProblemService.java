package com.judge.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ProblemService {
    @RequestMapping("/demo")
    String demo();
}
