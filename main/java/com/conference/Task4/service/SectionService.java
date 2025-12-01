package com.conference.Task4.service;

import com.conference.Task4.dao.DaoFactory;
import com.conference.Task4.entity.Section;
import com.conference.Task4.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class SectionService {
    private static final Logger logger = LoggerFactory.getLogger(SectionService.class);

    public void create(Section section) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            if (section.getName() == null || section.getName().isEmpty()) {
                throw new IllegalArgumentException("Invalid input");
            }
            DaoFactory.getInstance().createSectionDao(conn).create(section);
        } catch (Exception e) {
            logger.error("Create section error", e);
            throw e;
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public Section findById(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createSectionDao(conn).findById(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Section> findAllByConferenceId(int conferenceId) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createSectionDao(conn).findAllByConferenceId(conferenceId);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public List<Section> findAll() {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            return DaoFactory.getInstance().createSectionDao(conn).findAll();
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void update(Section section) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createSectionDao(conn).update(section);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }

    public void delete(int id) {
        Connection conn = ConnectionPool.getInstance().getConnection();
        try {
            DaoFactory.getInstance().createSectionDao(conn).delete(id);
        } finally {
            ConnectionPool.getInstance().releaseConnection(conn);
        }
    }
}