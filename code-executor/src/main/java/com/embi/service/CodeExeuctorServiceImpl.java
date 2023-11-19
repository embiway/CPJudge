package com.embi.service;

import com.embi.*;
import com.embi.server.CodeVerdictManager;
import com.embi.service.DTO.CodeDTO;
import com.embi.service.DTO.VerdictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
    public VerdictDTO checkResult(CodeDTO codeDTO) {
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
}
