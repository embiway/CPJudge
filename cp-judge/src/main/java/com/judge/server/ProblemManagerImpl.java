package com.judge.server;

import com.embi.Problem;
import com.judge.dao.ProblemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
