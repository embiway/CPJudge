package com.embi.service;

import com.embi.DTO.CodeDTO;
import com.embi.DTO.VerdictDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


public interface CodeExecutorService {

    VerdictDTO checkResult(CodeDTO code);

    void demoMethod();
}
