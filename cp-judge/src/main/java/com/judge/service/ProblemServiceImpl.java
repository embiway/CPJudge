package com.judge.service;

import com.judge.server.ProblemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemManager problemManager;
    @Override
    public void demo() {
        throw new RuntimeException("Hello");
    }
}
