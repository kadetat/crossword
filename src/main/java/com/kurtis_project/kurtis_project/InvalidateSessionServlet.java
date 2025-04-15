package com.kurtis_project.kurtis_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "InvalidateSessionServlet", value = "/api/invalidate_session_servlet")
public class InvalidateSessionServlet extends HttpServlet {

    /** Method for posts */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set up our response
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        if (session!=null) {
            session.invalidate();
            out.println("{\"success\": \"Invalidated Session.\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
