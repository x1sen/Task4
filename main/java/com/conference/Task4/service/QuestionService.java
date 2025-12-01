package com.conference.Task4.service;

import com.conference.Task4.dao.DaoFactory;
import com.conference.Task4.entity.Question;
import com.conference.Task4.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public void create(Question question) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            if (question.getQuestion() == null || question.getQuestion().isEmpty()) {
                throw new IllegalArgumentException("Invalid input");
            }
            DaoFactory.getInstance().createQuestionDao(conn).create(question);
        } catch (Exception e) {
            logger.error("Create question error", e);
            throw e;
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public Question findById(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createQuestionDao(conn).findById(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Question> findAllByUserId(int userId) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createQuestionDao(conn).findAllByUserId(userId);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Question> findAll() {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createQuestionDao(conn).findAll();
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void update(Question question) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createQuestionDao(conn).update(question);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createQuestionDao(conn).delete(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }
}