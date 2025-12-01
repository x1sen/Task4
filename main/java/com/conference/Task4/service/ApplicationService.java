package com.conference.Task4.service;

import com.conference.Task4.dao.DaoFactory;
import com.conference.Task4.entity.Application;
import com.conference.Task4.util.ConnectionPool;
import com.conference.Task4.util.WebServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    public void create(Application application) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            if (application.getDescription() == null || application.getDescription().isEmpty()) {
                throw new IllegalArgumentException("Invalid input");
            }
            application.setStatus("pending");
            DaoFactory.getInstance().createApplicationDao(conn).create(application);
        } catch (Exception e) {
            logger.error("Create application error", e);
            throw e;
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public Application findById(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createApplicationDao(conn).findById(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Application> findAllByUserId(int userId) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createApplicationDao(conn).findAllByUserId(userId);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Application> findAll() {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createApplicationDao(conn).findAll();
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void update(Application application) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createApplicationDao(conn).update(application);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void approve(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            Application application = DaoFactory.getInstance().createApplicationDao(conn).findById(id);
            if (application != null) {
                application.setStatus("approved");
                DaoFactory.getInstance().createApplicationDao(conn).update(application);
                WebServiceUtil.notifyStatus("Application " + id + " approved");
            }
        } catch (Exception e) {
            logger.error("Approve application error", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void reject(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            Application application = DaoFactory.getInstance().createApplicationDao(conn).findById(id);
            if (application != null) {
                application.setStatus("rejected");
                DaoFactory.getInstance().createApplicationDao(conn).update(application);
                WebServiceUtil.notifyStatus("Application " + id + " rejected");
            }
        } catch (Exception e) {
            logger.error("Reject application error", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createApplicationDao(conn).delete(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }
}