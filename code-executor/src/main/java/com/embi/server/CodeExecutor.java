package com.embi.server;

import com.embi.Code;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface CodeExecutor {
    CompletableFuture<String> compileCode(Code code) throws IOException;
    CompletableFuture<String> executeCode(CompletableFuture<String> compileFuture, Code code, String inputParams) throws Exception;
}
