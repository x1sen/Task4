package com.conference.Task4.controller;

import com.conference.Task4.service.ApplicationService;
import com.conference.Task4.service.ConferenceService;
import com.conference.Task4.service.QuestionService;
import com.conference.Task4.service.SectionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet({"/conferences", "/sections", "/applications", "/questions"})
public class AppServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AppServlet.class);
    private final ConferenceService conferenceService = new ConferenceService();
    private final SectionService sectionService = new SectionService();
    private final ApplicationService applicationService = new ApplicationService();
    private final QuestionService questionService = new QuestionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            switch (path) {
                case "/conferences":
                    req.setAttribute("conferences", conferenceService.findAll());
                    req.getRequestDispatcher("/conferences.jsp").forward(req, resp);
                    break;
                case "/sections":
                    int confId = Integer.parseInt(req.getParameter("conferenceId"));
                    req.setAttribute("sections", sectionService.findAllByConferenceId(confId));
                    req.getRequestDispatcher("/sections.jsp").forward(req, resp);
                    break;
                case "/applications":
                    req.setAttribute("applications", applicationService.findAll());
                    req.getRequestDispatcher("/applications.jsp").forward(req, resp);
                    break;
                case "/questions":
                    req.setAttribute("questions", questionService.findAll());
                    req.getRequestDispatcher("/questions.jsp").forward(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("App list error", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}