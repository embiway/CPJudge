package com.judge.service;

import com.embi.DTO.CodeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.judge.Constants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/solve")
public class SolutionServiceImpl implements SolutionService {
    @Override
    @RequestMapping(path = "/demo1")
    public String demo() {
        return "Hello World";
    }

    @Override
    @RequestMapping(path = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String submitSolution(@RequestBody CodeDTO codeDTO) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.CODE_EXECUTOR_SERVER + Constants.CODE_EXECUTOR_SERVICE + Constants.CODE_EXECUTOR_API;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCodeDTO = objectMapper.writeValueAsString(codeDTO);
        HttpEntity<CodeDTO> entity = new HttpEntity<CodeDTO>(codeDTO,headers);

        return restTemplate.postForObject(url, entity, String.class);
    }

    private String convertCodeDTOToJSON(CodeDTO code) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCodeDTO = objectMapper.writeValueAsString(code);
        return jsonCodeDTO;
    }
}
