package com.judge.dao;

import com.embi.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class AuthDAOImpl implements AuthDAO{

    String resource = "config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);;

    public AuthDAOImpl() throws IOException {
    }

    @Override
    public void addUser(User user) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.insert("insertUser", user);
            sqlSession.commit();
        }
    }

    @Override
    public Optional<User> getUser(String userName) {
        User user = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            user = sqlSession.selectOne("getUser", userName);
        }
        return Optional.of(user);
    }
}
