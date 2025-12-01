package com.conference.Task4.controller;

import com.conference.Task4.entity.Conference;
import com.conference.Task4.entity.Section;
import com.conference.Task4.service.ApplicationService;
import com.conference.Task4.service.ConferenceService;
import com.conference.Task4.service.SectionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminServlet.class);
    private final ConferenceService conferenceService = new ConferenceService();
    private final SectionService sectionService = new SectionService();
    private final ApplicationService applicationService = new ApplicationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            switch (action) {
                case "/createConference":
                    Conference conference = new Conference();
                    conference.setName(req.getParameter("name"));
                    conference.setDescription(req.getParameter("description"));
                    conferenceService.create(conference);
                    resp.sendRedirect("/conferences");
                    break;
                case "/createSection":
                    Section section = new Section();
                    section.setConferenceId(Integer.parseInt(req.getParameter("conferenceId")));
                    section.setName(req.getParameter("name"));
                    section.setTheme(req.getParameter("theme"));
                    sectionService.create(section);
                    resp.sendRedirect("/sections?conferenceId=" + section.getConferenceId());
                    break;
                case "/approveApplication":
                    int appId = Integer.parseInt(req.getParameter("appId"));
                    applicationService.approve(appId);
                    resp.sendRedirect("/applications");
                    break;
                case "/rejectApplication":
                    int rejectId = Integer.parseInt(req.getParameter("appId"));
                    applicationService.reject(rejectId);
                    resp.sendRedirect("/applications");
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Admin action error", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}