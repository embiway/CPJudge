package com.judge.server;

import com.embi.Problem;
import com.judge.dao.ProblemDAO;
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

    public static void main(String[] args) {
        ProblemManagerImpl problemManager = new ProblemManagerImpl();
        Problem problem = new Problem();
        problem.setId(1);
        problem.setStatement("hello World");

        problemManager.createProblem(problem);
    }
}
