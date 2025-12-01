package com.conference.Task4.dao;

import java.sql.Connection;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private DaoFactory() {}

    public UserDao createUserDao(Connection conn) {
        return new UserDao(conn);
    }

    public ConferenceDao createConferenceDao(Connection conn) {
        return new ConferenceDao(conn);
    }

    public SectionDao createSectionDao(Connection conn) {
        return new SectionDao(conn);
    }

    public ApplicationDao createApplicationDao(Connection conn) {
        return new ApplicationDao(conn);
    }

    public QuestionDao createQuestionDao(Connection conn) {
        return new QuestionDao(conn);
    }
}