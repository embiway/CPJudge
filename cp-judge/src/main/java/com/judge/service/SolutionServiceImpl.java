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

@Component
public class SolutionServiceImpl implements SolutionService {
    @Override
    public String demo() {
        return "Hello World";
    }

    @Override
    public String submitSolution(@RequestBody CodeDTO codeDTO) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.CODE_EXECUTOR_SERVER + Constants.CODE_EXECUTOR_SERVICE + "/checkResult";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCodeDTO = objectMapper.writeValueAsString(codeDTO);
        HttpEntity<String> entity = new HttpEntity<String>(jsonCodeDTO,headers);

        return restTemplate.postForObject(url, entity, String.class);
    }
}
