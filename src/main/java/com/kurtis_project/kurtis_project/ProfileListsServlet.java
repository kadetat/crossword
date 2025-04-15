package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "ProfileListsServlet", value = "/api/profile_Lists")
public class ProfileListsServlet extends HttpServlet {



    private final static Logger log = Logger.getLogger(CrosswordServlet.class.getName());


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // You can output in any format, text/JSON, text/HTML, etc. We'll keep it simple
        //response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        java.io.BufferedReader in = request.getReader();
        String requestString = new String();
        for (String line; (line = in.readLine()) != null; requestString += line);

        System.out.println(requestString);

        //JsonObject convertedObject = new Gson().fromJson(requestString, JsonObject.class);

        Gson gson = new Gson();
        AmazonInfo emailInfo = gson.fromJson(requestString, AmazonInfo.class);
        HttpSession session = request.getSession();
        String loginObject = (String)session.getAttribute("loggedIn");
        String loginID = (String)session.getAttribute("loginID");

        if (loginObject!=null) {
            if (emailInfo.getAmazonID().equals(loginID) && (loginID.equals("117261892525051582026") || loginID.equals("102581916658076323474"))){
                List <PuzzleInfo> puzzleList = PuzzleInfoDAO.getPlayedList("45");
                List <PuzzleInfo> puzzleList2 = PuzzleInfoDAO.getBuiltList("117261892525051582026");
                JsonObject jsonObj = new JsonObject();
                Gson jsonString1 = new Gson();
                Gson jsonString2 = new Gson();
                JsonArray json1 = jsonString1.toJsonTree(puzzleList).getAsJsonArray();;
                JsonArray json2 = jsonString2.toJsonTree(puzzleList2).getAsJsonArray();;
                jsonObj.add("jsonArray1", json1);
                jsonObj.add("jsonArray2", json2);
                out.println(jsonObj.toString());
            }
            else if (emailInfo.getAmazonID().equals(loginID)){
                String userID = PuzzleInfoDAO.getUserID(loginID);
                if (!userID.equals("0")){
                    List <PuzzleInfo> puzzleList = PuzzleInfoDAO.getPlayedList(userID);
                    List <PuzzleInfo> puzzleList2 = PuzzleInfoDAO.getBuiltList(userID);
                    JsonObject jsonObj = new JsonObject();
                    Gson jsonString1 = new Gson();
                    Gson jsonString2 = new Gson();
                    JsonArray json1 = jsonString1.toJsonTree(puzzleList).getAsJsonArray();;
                    JsonArray json2 = jsonString2.toJsonTree(puzzleList2).getAsJsonArray();;
                    jsonObj.add("jsonArray1", json1);
                    jsonObj.add("jsonArray2", json2);
                    out.println(jsonObj.toString());
                    // Write out that string
                    //out.println("{\"playedList\":\"" + jsonString +"\",");
                    //out.println("\"builtList\":\"" + jsonString2 +"\"}");
                }
                else {
                    out.println("{\"error\": \"user doesn't exist.\"}");
                }
            }
            else {
                out.println("{\"error\": \"Can't get data.\"}");
            }
        }else {
            out.println("{\"error\": \"Not logged in.\"}");
        }
    }
}