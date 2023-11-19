package com.judge;

import com.embi.Problem;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class ProblemDAOImpl implements ProblemDAO {

    String resource = "problem-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
//    @Autowired
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);;

    public ProblemDAOImpl() throws IOException {

    }

    @Override
    public void add(Problem problem) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.insert("ProblemMapper.insertProblem", problem);
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
            sqlSession.delete("ProlemMapper.deleteProblem", problemId);
        }
    }

    public static void main(String[] args) throws IOException {
        ProblemDAO problemDAO = new ProblemDAOImpl();
        Problem problem = new Problem();
        problem.setId(1);
        problem.setStatement("Hello World");

        System.out.println(problemDAO.getProblem(1));
    }
}
