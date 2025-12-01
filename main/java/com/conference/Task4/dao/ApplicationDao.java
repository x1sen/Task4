package com.conference.Task4.dao;

import com.conference.Task4.entity.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationDao.class);
    private final Connection conn;

    public ApplicationDao(Connection conn) {
        this.conn = conn;
    }

    public void create(Application application) {
        String sql = "INSERT INTO applications (user_id, section_id, status, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, application.getUserId());
            ps.setInt(2, application.getSectionId());
            ps.setString(3, application.getStatus());
            ps.setString(4, application.getDescription());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    application.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Create application error", e);
        }
    }

    public Application findById(int id) {
        String sql = "SELECT * FROM applications WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                application.setSectionId(rs.getInt("section_id"));
                application.setStatus(rs.getString("status"));
                application.setDescription(rs.getString("description"));
                return application;
            }
        } catch (SQLException e) {
            logger.error("Find application by id error", e);
        }
        return null;
    }

    public List<Application> findAllByUserId(int userId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                application.setSectionId(rs.getInt("section_id"));
                application.setStatus(rs.getString("status"));
                application.setDescription(rs.getString("description"));
                applications.add(application);
            }
        } catch (SQLException e) {
            logger.error("Find applications by user id error", e);
        }
        return applications;
    }

    public List<Application> findAll() {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                application.setSectionId(rs.getInt("section_id"));
                application.setStatus(rs.getString("status"));
                application.setDescription(rs.getString("description"));
                applications.add(application);
            }
        } catch (SQLException e) {
            logger.error("Find all applications error", e);
        }
        return applications;
    }

    public void update(Application application) {
        String sql = "UPDATE applications SET user_id = ?, section_id = ?, status = ?, description = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, application.getUserId());
            ps.setInt(2, application.getSectionId());
            ps.setString(3, application.getStatus());
            ps.setString(4, application.getDescription());
            ps.setInt(5, application.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Update application error", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM applications WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete application error", e);
        }
    }
}