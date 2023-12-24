package com.judge.dao;

import com.embi.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class AuthDAOImpl implements AuthDAO{
    @Autowired
    @Qualifier("config")
    SqlSessionFactory sqlSessionFactory;

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
