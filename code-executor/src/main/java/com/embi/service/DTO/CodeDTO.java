package com.embi.service.DTO;

import java.util.Map;

public class CodeDTO {
    int id;
    int problemId;
    int problemTimeLimit;
    int problemMemoryLimit;
    String language;
    boolean useOutputVerifier;
    String outputVerifier;

    String actualCode;
    Map<String, String> io;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getProblemTimeLimit() {
        return problemTimeLimit;
    }

    public void setProblemTimeLimit(int problemTimeLimit) {
        this.problemTimeLimit = problemTimeLimit;
    }

    public int getProblemMemoryLimit() {
        return problemMemoryLimit;
    }

    public void setProblemMemoryLimit(int problemMemoryLimit) {
        this.problemMemoryLimit = problemMemoryLimit;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isUseOutputVerifier() {
        return useOutputVerifier;
    }

    public void setUseOutputVerifier(boolean useOutputVerifier) {
        this.useOutputVerifier = useOutputVerifier;
    }

    public String getOutputVerifier() {
        return outputVerifier;
    }

    public void setOutputVerifier(String outputVerifier) {
        this.outputVerifier = outputVerifier;
    }

    public String getActualCode() {
        return actualCode;
    }

    public void setActualCode(String actualCode) {
        this.actualCode = actualCode;
    }

    public Map<String, String> getIo() {
        return io;
    }

    public void setIo(Map<String, String> io) {
        this.io = io;
    }
}
