package com.embi.server;

import com.embi.Code;
import com.embi.Verdict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CodeVerdictManagerImpl implements CodeVerdictManager{

    @Autowired
    CodeExecutor codeExecutor;

    @Override
    public List<Verdict> checkResult(Code code) throws Exception {
        List<Verdict> verdicts = new ArrayList<>();
        for (Map.Entry<String, String> io : code.getProblem().getIO().entrySet()) {
            CompletableFuture<String> execFuture = codeExecutor.executeCode(codeExecutor.compileCode(code), code, io.getKey());
            CompletableFuture<Verdict> finalResult = execFuture.thenApplyAsync((res) -> {
                Verdict verdict = new Verdict();
                if (res.contains(Verdict.ResultCode.CF.getName())) {
                    verdict.setResultCode(Verdict.ResultCode.CF);
                    verdict.setMessage(res.replaceFirst(Verdict.ResultCode.CF.getName(), ""));
                    return verdict;
                } else if (res.contains(Verdict.ResultCode.TLE.getName())) {
                    verdict.setResultCode(Verdict.ResultCode.TLE);
                    return verdict;
                }

                if (code.getProblem().isUseOutputVerifier()) {
                    try {
                        CompletableFuture<String> verifierFuture = codeExecutor.executeCode(
                                codeExecutor.compileCode(code.getProblem().getOutputVerifier()), code, res);
                        if ("true".equals(verifierFuture.get())) verdict.setResultCode(Verdict.ResultCode.AC);
                        else verdict.setResultCode(Verdict.ResultCode.WA);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println(res.replace('\n', ' '));
                    System.out.println(io.getValue().replace('\n', ' '));
                    if (res.replace('\n', ' ').equals(io.getValue().replace('\n', ' '))) verdict.setResultCode(Verdict.ResultCode.AC);
                    else verdict.setResultCode(Verdict.ResultCode.WA);
                }
                return verdict;
            });

            verdicts.add(finalResult.get());
        }

        return verdicts;
    }
}
