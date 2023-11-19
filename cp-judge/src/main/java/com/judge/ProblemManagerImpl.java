package com.judge;

import com.embi.Problem;
import org.springframework.beans.factory.annotation.Autowired;

public class ProblemManagerImpl implements ProblemManager {

    @Autowired
    ProblemDAO problemDAO;

    @Override
    public void createProblem(Problem problem) {
        problemDAO.add(problem);
    }

    @Override
    public Problem getProblem(int problemId) {
        return problemDAO.getProblem(problemId);
    }

    @Override
    public void deleteProblem(int problemId) {
        problemDAO.delete(problemId);
    }
}
