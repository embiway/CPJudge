package com.embi;

public class Code {
    int id;
    Problem problem;
    String code;
    Language language;

    public Code(String code) {
        this.code = code;
        this.language = Language.C_PLUS_PLUS;
    }

    public Code(int id, Problem problem, String code, Language language) {
        this.id = id;
        this.problem = problem;
        this.code = code;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
