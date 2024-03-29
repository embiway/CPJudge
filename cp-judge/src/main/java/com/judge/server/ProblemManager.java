package com.judge.server;

import com.embi.Problem;

public interface ProblemManager {

    void createProblem(Problem problem);

    Problem getProblem(int problemId);

    void deleteProblem(int problemId);
}
