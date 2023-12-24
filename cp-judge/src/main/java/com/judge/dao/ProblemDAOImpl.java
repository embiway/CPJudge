package com.judge.dao;

import com.embi.Problem;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProblemDAOImpl implements ProblemDAO {
    @Autowired
    @Qualifier("config")
    SqlSessionFactory sqlSessionFactory;

    public ProblemDAOImpl() throws IOException {

    }

    @Override
    public void add(Problem problem) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.insert("insertProblem", problem);
            sqlSession.commit();
        }
    }

    @Override
    public Problem getProblem(int problemId) {
        Problem problem = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            problem = sqlSession.selectOne("getProblem", problemId);
        }
        return problem;
    }

    @Override
    public void delete(int problemId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.delete("deleteProblem", problemId);
            sqlSession.commit();
        }
    }
}
