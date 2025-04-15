package com.kurtis_project.kurtis_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CheckSessionAttributeServlet", value = "/api/check_session_attribute_servlet")
public class CheckSessionAttributeServlet extends HttpServlet {

    /** Method for posts */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set up our response
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String loginObject = (String)session.getAttribute("loggedIn");
        if (loginObject!=null) {
            out.println("{\"name\":\"" + (String)session.getAttribute("loginName") +"\",");
            out.println("\"email\":\"" + (String)session.getAttribute("userEmail") +"\",");
            out.println("\"pictureURL\":\"" + (String)session.getAttribute("pictureURL") +"\",");
            out.println("\"amazonID\":\"" + (String)session.getAttribute("loginID") +"\"}");
        } else {
            out.println("{\"noLogin\": \"Not logged in.\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}