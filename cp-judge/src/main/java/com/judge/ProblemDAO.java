package com.judge;

import com.embi.Problem;

public interface ProblemDAO {
    void add(Problem problem);

    Problem getProblem(int problemId);

    void delete(int problemId);
}
