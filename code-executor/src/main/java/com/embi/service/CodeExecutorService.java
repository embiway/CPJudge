package com.embi.service;

import com.embi.DTO.CodeDTO;
import com.embi.DTO.VerdictDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public interface CodeExecutorService {

    @RequestMapping(path = "/checkResult", consumes = MediaType.APPLICATION_JSON_VALUE)
    VerdictDTO checkResult(@RequestBody CodeDTO code);

    @RequestMapping(path = "/demo")
    void demoMethod();
}
