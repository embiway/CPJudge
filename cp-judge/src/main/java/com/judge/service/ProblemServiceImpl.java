package com.judge.service;

import com.judge.server.ProblemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problem")
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemManager problemManager;
    @Override
    @RequestMapping(path = "/demo")
    public void demo() {
        throw new RuntimeException("Hello");
    }
}
