package com.conference.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String path = req.getServletPath();

        if (path.equals("/login") || path.startsWith("/register")) {
            chain.doFilter(request, response);
            return;
        }

        com.conference.entity.User user = (com.conference.entity.User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        if (path.startsWith("/admin") && !"admin".equals(user.getRole())) {
            resp.sendError(403);
            return;
        }

        chain.doFilter(request, response);
    }
}