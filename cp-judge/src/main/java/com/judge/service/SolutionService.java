package com.judge.service;

import com.embi.DTO.CodeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solve")
public interface SolutionService {
    @RequestMapping(path = "/demo1")
    String demo();

    @RequestMapping(path = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE)
    String submitSolution(CodeDTO codeDTO) throws JsonProcessingException;
}
