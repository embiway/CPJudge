package com.judge.service;

import com.embi.DTO.CodeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface SolutionService {
    String demo();

    String submitSolution(CodeDTO codeDTO) throws JsonProcessingException;
}
