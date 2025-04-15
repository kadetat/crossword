package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
        else if (emailInfo.getAmazonID().contains(" stop")){
            PuzzleInfoDAO.declineEmail(emailInfo.getAmazonID().substring (0,emailInfo.getAmazonID().length () - 5));
            out.println("{\"set\": \"good set.\"}");
        }


    }
}
