package com.conference.Task4.dao;

import com.conference.Task4.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private static final Logger logger = LoggerFactory.getLogger(QuestionDao.class);
    private final Connection conn;

    public QuestionDao(Connection conn) {
        this.conn = conn;
    }

    public void create(Question question) {
        String sql = "INSERT INTO questions (user_id, question, answer) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getUserId());
            ps.setString(2, question.getQuestion());
            ps.setString(3, question.getAnswer());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Create question error", e);
        }
    }

    public Question findById(int id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setUserId(rs.getInt("user_id"));
                question.setQuestion(rs.getString("question"));
                question.setAnswer(rs.getString("answer"));
                return question;
            }
        } catch (SQLException e) {
            logger.error("Find question by id error", e);
        }
        return null;
    }

    public List<Question> findAllByUserId(int userId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setUserId(rs.getInt("user_id"));
                question.setQuestion(rs.getString("question"));
                question.setAnswer(rs.getString("answer"));
                questions.add(question);
            }
        } catch (SQLException e) {
            logger.error("Find questions by user id error", e);
        }
        return questions;
    }

    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setUserId(rs.getInt("user_id"));
                question.setQuestion(rs.getString("question"));
                question.setAnswer(rs.getString("answer"));
                questions.add(question);
            }
        } catch (SQLException e) {
            logger.error("Find all questions error", e);
        }
        return questions;
    }

    public void update(Question question) {
        String sql = "UPDATE questions SET user_id = ?, question = ?, answer = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, question.getUserId());
            ps.setString(2, question.getQuestion());
            ps.setString(3, question.getAnswer());
            ps.setInt(4, question.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Update question error", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete question error", e);
        }
    }
}