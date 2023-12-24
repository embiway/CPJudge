package com.judge.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problem")
public interface ProblemService {
    @RequestMapping(path = "/demo")
    void demo();
}
