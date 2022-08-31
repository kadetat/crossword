package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "AllowEmailServlet", value = "/api/set_Email_Settings")
public class AllowEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // --- Sessions ---

        // - This example uses a session to keep count of client requests.
        HttpSession session = request.getSession();
        String amazonID = (String)session.getAttribute("loginID");

        java.io.BufferedReader in = request.getReader();
        String requestString = new String();
        for (String line; (line = in.readLine()) != null; requestString += line);

        Gson gson = new Gson();
        AmazonInfo emailInfo = gson.fromJson(requestString, AmazonInfo.class);
        if (emailInfo.getAmazonID().equals(amazonID)){
            PuzzleInfoDAO.allowEmail(amazonID);
            out.println("{\"set\": \"good set.\"}");
        }


    }
}
