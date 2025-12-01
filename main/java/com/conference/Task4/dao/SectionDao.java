package com.conference.Task4.dao;

import com.conference.Task4.entity.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDao {
    private static final Logger logger = LoggerFactory.getLogger(SectionDao.class);
    private final Connection conn;

    public SectionDao(Connection conn) {
        this.conn = conn;
    }

    public void create(Section section) {
        String sql = "INSERT INTO sections (conference_id, name, theme) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, section.getConferenceId());
            ps.setString(2, section.getName());
            ps.setString(3, section.getTheme());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    section.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Create section error", e);
        }
    }

    public Section findById(int id) {
        String sql = "SELECT * FROM sections WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Section section = new Section();
                section.setId(rs.getInt("id"));
                section.setConferenceId(rs.getInt("conference_id"));
                section.setName(rs.getString("name"));
                section.setTheme(rs.getString("theme"));
                return section;
            }
        } catch (SQLException e) {
            logger.error("Find section by id error", e);
        }
        return null;
    }

    public List<Section> findAllByConferenceId(int conferenceId) {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT * FROM sections WHERE conference_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, conferenceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Section section = new Section();
                section.setId(rs.getInt("id"));
                section.setConferenceId(rs.getInt("conference_id"));
                section.setName(rs.getString("name"));
                section.setTheme(rs.getString("theme"));
                sections.add(section);
            }
        } catch (SQLException e) {
            logger.error("Find sections by conference id error", e);
        }
        return sections;
    }

    public List<Section> findAll() {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT * FROM sections";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Section section = new Section();
                section.setId(rs.getInt("id"));
                section.setConferenceId(rs.getInt("conference_id"));
                section.setName(rs.getString("name"));
                section.setTheme(rs.getString("theme"));
                sections.add(section);
            }
        } catch (SQLException e) {
            logger.error("Find all sections error", e);
        }
        return sections;
    }

    public void update(Section section) {
        String sql = "UPDATE sections SET conference_id = ?, name = ?, theme = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, section.getConferenceId());
            ps.setString(2, section.getName());
            ps.setString(3, section.getTheme());
            ps.setInt(4, section.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Update section error", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM sections WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete section error", e);
        }
    }
}