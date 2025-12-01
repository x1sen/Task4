package com.conference.Task4.service;

import com.conference.Task4.dao.DaoFactory;
import com.conference.Task4.entity.User;
import com.conference.Task4.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void register(User user) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Invalid input");
            }
            user.setPassword(Integer.toString(user.getPassword().hashCode()));
            DaoFactory.getInstance().createUserDao(conn).create(user);
        } catch (Exception e) {
            logger.error("Register error", e);
            throw e;
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public User login(String username, String password) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            User user = DaoFactory.getInstance().createUserDao(conn).findByUsername(username);
            if (user != null && user.getPassword().equals(Integer.toString(password.hashCode()))) {
                return user;
            }
            return null;
        } catch (Exception e) {
            logger.error("Login error", e);
            return null;
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public User findById(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createUserDao(conn).findById(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<User> findAll() {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createUserDao(conn).findAll();
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void update(User user) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(Integer.toString(user.getPassword().hashCode()));
            }
            DaoFactory.getInstance().createUserDao(conn).update(user);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createUserDao(conn).delete(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }
}