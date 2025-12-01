package com.conference.Task4.dao;

import com.conference.Task4.entity.Conference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDao {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceDao.class);
    private final Connection conn;

    public ConferenceDao(Connection conn) {
        this.conn = conn;
    }

    public void create(Conference conference) {
        String sql = "INSERT INTO conferences (name, description) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, conference.getName());
            ps.setString(2, conference.getDescription());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conference.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Create conference error", e);
        }
    }

    public Conference findById(int id) {
        String sql = "SELECT * FROM conferences WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Conference conference = new Conference();
                conference.setId(rs.getInt("id"));
                conference.setName(rs.getString("name"));
                conference.setDescription(rs.getString("description"));
                return conference;
            }
        } catch (SQLException e) {
            logger.error("Find conference by id error", e);
        }
        return null;
    }

    public List<Conference> findAll() {
        List<Conference> conferences = new ArrayList<>();
        String sql = "SELECT * FROM conferences";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Conference conference = new Conference();
                conference.setId(rs.getInt("id"));
                conference.setName(rs.getString("name"));
                conference.setDescription(rs.getString("description"));
                conferences.add(conference);
            }
        } catch (SQLException e) {
            logger.error("Find all conferences error", e);
        }
        return conferences;
    }

    public void update(Conference conference) {
        String sql = "UPDATE conferences SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, conference.getName());
            ps.setString(2, conference.getDescription());
            ps.setInt(3, conference.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Update conference error", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM conferences WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete conference error", e);
        }
    }
}