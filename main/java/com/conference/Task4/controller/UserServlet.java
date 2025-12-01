package com.conference.Task4.controller;

import com.conference.Task4.entity.Application;
import com.conference.Task4.entity.Question;
import com.conference.Task4.service.ApplicationService;
import com.conference.Task4.service.QuestionService;
import com.conference.Task4.util.SubmitApplicationCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);
    private final ApplicationService applicationService = new ApplicationService();
    private final QuestionService questionService = new QuestionService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession session = req.getSession();
        com.conference.Task4.entity.User user = (com.conference.Task4.entity.User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        try {
            switch (action) {
                case "/submitApplication":
                    Application app = new Application();
                    app.setUserId(user.getId());
                    app.setSectionId(Integer.parseInt(req.getParameter("sectionId")));
                    app.setDescription(req.getParameter("description"));
                    new SubmitApplicationCommand(app, applicationService).execute();
                    resp.sendRedirect("/applications");
                    break;
                case "/withdrawApplication":
                    int appId = Integer.parseInt(req.getParameter("appId"));
                    applicationService.delete(appId);
                    resp.sendRedirect("/applications");
                    break;
                case "/askQuestion":
                    Question question = new Question();
                    question.setUserId(user.getId());
                    question.setQuestion(req.getParameter("question"));
                    questionService.create(question);
                    resp.sendRedirect("/questions");
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("User action error", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}