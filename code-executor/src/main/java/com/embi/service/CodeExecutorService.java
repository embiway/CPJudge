package com.embi.service;

import com.embi.service.DTO.CodeDTO;
import com.embi.service.DTO.VerdictDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public interface CodeExecutorService {

    @RequestMapping(path = "/checkResult", consumes = MediaType.APPLICATION_JSON_VALUE)
    VerdictDTO checkResult(@RequestBody CodeDTO code);

    @RequestMapping(path = "/demo")
    void demoMethod();
}
