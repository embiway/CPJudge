package com.embi.server;

import com.embi.Code;
import com.embi.Verdict;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

public interface CodeVerdictManager {
    List<Verdict> checkResult(Code code) throws Exception;
}
