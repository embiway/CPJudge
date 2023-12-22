package com.embi;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

public class Problem {
    private int id;
    private String statement;
    private long timeLimit;
    private long memoryLimit;
    private Map<String, String> IO;
    private Code outputVerifier;
    private boolean useOutputVerifier;

    public Problem() {}
    public Problem(int id, String statement, long timeLimit, long memoryLimit) {
        this.id = id;
        this.statement = statement;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public Code getOutputVerifier() {
        return outputVerifier;
    }

    public void setOutputVerifier(Code outputVerifier) {
        this.outputVerifier = outputVerifier;
    }

    public boolean isUseOutputVerifier() {
        return useOutputVerifier;
    }

    public void setUseOutputVerifier(boolean useOutputVerifier) {
        this.useOutputVerifier = useOutputVerifier;
    }

    public Map<String, String> getIO() {
        return IO;
    }

    public void setIO(Map<String, String> IO) {
        this.IO = IO;
    }

}
