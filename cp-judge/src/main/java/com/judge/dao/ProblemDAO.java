package com.judge.dao;

import com.embi.Problem;

public interface ProblemDAO {
    void add(Problem problem);

    Problem getProblem(int problemId);

    void delete(int problemId);
}
