package com.conference.Task4.controller;

import com.conference.Task4.entity.User;
import com.conference.Task4.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String lang = req.getParameter("lang");

        HttpSession session = req.getSession();
        session.setAttribute("locale", new Locale(lang != null ? lang : "en"));
        ResourceBundle bundle = ResourceBundle.getBundle("messages", (Locale) session.getAttribute("locale"));

        try {
            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                resp.sendRedirect("/conferences");
            } else {
                req.setAttribute("error", bundle.getString("login.error"));
                doGet(req, resp);
            }
        } catch (Exception e) {
            logger.error("Login error", e);
            req.setAttribute("error", bundle.getString("error.general"));
            doGet(req, resp);
        }
    }
}