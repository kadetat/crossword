package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;



@WebServlet(name = "GoogleServlet", value = "/api/googleHelp")
public class GoogleServlet extends HttpServlet {



    private final static Logger log = Logger.getLogger(CrosswordServlet.class.getName());

    HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList("161771154815-4t5i72diha2hkfl0nb2pjgv547e276hc.apps.googleusercontent.com"))
            .build();

    public GoogleServlet() throws GeneralSecurityException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // You can output in any format, text/JSON, text/HTML, etc. We'll keep it simple
        //response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        java.io.BufferedReader in = request.getReader();
        String requestString = new String();
        for (String line; (line = in.readLine()) != null; requestString += line);

        Gson gson = new Gson();
        AmazonInfo googleInfo = gson.fromJson(requestString, AmazonInfo.class);
        GoogleIdToken idToken = null;
        try {
            idToken = googleIdTokenVerifier.verify(googleInfo.getCode());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", "true");
            session.setAttribute("loginID", userId);
            session.setAttribute("loginName", name);
            session.setAttribute("userEmail", email);
            Boolean exists = PuzzleInfoDAO.checkIfUserExists(userId);

            if (!exists){
                Boolean added = PuzzleInfoDAO.addUser(userId, name, email);
                if (added) {
                    out.println("{\"added\":\"good insert.\",");
                    out.println("\"name\":\"" + name + "\",");
                    out.println("\"email\":\"" + email + "\"}");
                }
            }else {
                out.println("{\"exists\":\"good exists.\",");
                out.println("\"name\":\"" + name +"\",");
                out.println("\"email\":\"" + email +"\"}");
            }

        } else {
            out.println("{\"error\": \"bad insert.\"}");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get crossword servlet");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();




    }
}

