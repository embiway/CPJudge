package com.embi.service;

import com.embi.*;
import com.embi.server.CodeVerdictManager;
import com.embi.DTO.CodeDTO;
import com.embi.DTO.VerdictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeExeuctorServiceImpl implements CodeExecutorService {

    @Autowired
    CodeVerdictManager codeVerdictManager;

    private Code getCodeFromDTO(CodeDTO codeDTO) {
        Problem problem = new Problem();
        problem.setId(codeDTO.getProblemId());
        problem.setUseOutputVerifier(codeDTO.isUseOutputVerifier());
        problem.setIO(codeDTO.getIo());
        problem.setOutputVerifier(new Code(codeDTO.getOutputVerifier()));
        problem.setTimeLimit(codeDTO.getProblemTimeLimit());
        problem.setMemoryLimit(codeDTO.getProblemMemoryLimit());

        return new Code(codeDTO.getId(), problem, codeDTO.getActualCode(), Language.getLanguage(codeDTO.getLanguage()));
    }

    private VerdictDTO getDTOFromVerdict(Code code, Verdict verdict) {
        VerdictDTO verdictDTO = new VerdictDTO();
        verdictDTO.setCodeId(code.getId());
        verdictDTO.setResult(verdict.getResultCode().getName());
        verdictDTO.setMessage(verdict.getMessage());
        return verdictDTO;
    }

    @Override
    @RequestMapping(path = "/checkResult", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VerdictDTO checkResult(@RequestBody CodeDTO codeDTO) {
        Code code = getCodeFromDTO(codeDTO);
        Verdict consolidatedVerdict = new Verdict();

        try {
            List<Verdict> verdicts = codeVerdictManager.checkResult(code);
            if (!Verdict.ResultCode.AC.equals(verdicts.get((int)verdicts.size()-1).getResultCode())) {
                consolidatedVerdict = verdicts.get((int)verdicts.size()-1);
            }
            else {
                // All tests passed
                consolidatedVerdict = new Verdict();
                consolidatedVerdict.setResultCode(Verdict.ResultCode.AC);
                consolidatedVerdict.setMessage("All tests passed");
            }
        } catch (Exception e) {
            consolidatedVerdict.setResultCode(Verdict.ResultCode.UE);
            consolidatedVerdict.setMessage(e.getMessage());
        }

        return getDTOFromVerdict(code, consolidatedVerdict);
    }

    @Override
    @RequestMapping(path = "/demo")
    public void demoMethod() {
        throw new RuntimeException("Fuck You");
    }
}
