package com.conference.Task4.service;

import com.conference.Task4.dao.DaoFactory;
import com.conference.Task4.entity.Conference;
import com.conference.Task4.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class ConferenceService {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceService.class);

    public void create(Conference conference) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            if (conference.getName() == null || conference.getName().isEmpty()) {
                throw new IllegalArgumentException("Invalid input");
            }
            DaoFactory.getInstance().createConferenceDao(conn).create(conference);
        } catch (Exception e) {
            logger.error("Create conference error", e);
            throw e;
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public Conference findById(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createConferenceDao(conn).findById(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Conference> findAll() {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createConferenceDao(conn).findAll();
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void update(Conference conference) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createConferenceDao(conn).update(conference);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createConferenceDao(conn).delete(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }
}