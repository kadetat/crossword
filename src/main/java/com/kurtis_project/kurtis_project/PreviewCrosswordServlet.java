package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;
import com.google.gson.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import sun.jvm.hotspot.utilities.Assert;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;




@WebServlet(name = "PreviewCrosswordServlet", value = "/api/previewcrossword")
public class PreviewCrosswordServlet extends HttpServlet {
    ArrayList<Word> list = new ArrayList<>();
    String[][] puzzle;


    private final static Logger log = Logger.getLogger(CrosswordServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // You can output in any format, text/JSON, text/HTML, etc. We'll keep it simple
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        java.io.BufferedReader in = request.getReader();
        String requestString = new String();
        for (String line; (line = in.readLine()) != null; requestString += line);

        System.out.println(requestString);

        //JsonObject convertedObject = new Gson().fromJson(requestString, JsonObject.class);

        Gson gson = new Gson();

        PuzzleInfo puzzleInfo = gson.fromJson(requestString, PuzzleInfo.class);
        System.out.println(puzzleInfo.getwords());
        list.clear();

        System.out.println("here");
        for (int i = 0; i < puzzleInfo.getwords().size(); i++) {
            list.add(new Word(puzzleInfo.getwords().get(i), puzzleInfo.getclues().get(i)));
        }

        CrosswordCreator puzzle1 = new CrosswordCreator(50, 80, "empty", 2000, list);
        int spins = 20;
        int x = 1;
        boolean complete = false;
        while (x < spins && !complete){
            try {
                puzzle = puzzle1.computeCrossword();

                puzzle1.display(puzzle);
                System.out.println("Number of Spins: " + x);
                complete = true;
                JsonObject jsonObj = new JsonObject();
                Gson jsonString = new Gson();
                Gson jsonStringWords = new Gson();
                JsonArray jsonString1 = jsonString.toJsonTree(puzzle).getAsJsonArray();;
                JsonArray jsonString2 = jsonStringWords.toJsonTree(list).getAsJsonArray();;
                jsonObj.add("jsonArray1", jsonString1);
                jsonObj.add("jsonArray2", jsonString2);
                out.println(jsonObj.toString());
                list.clear();
            } catch (Exception NullPointerException){
                x ++;
                System.out.println("nowhere to put word");
                if (x == spins) {
                    System.out.println("Error: reached spins");
                    out.println("{\"error\": \"Crossword could not be created.\"}");
                }
            }
        }
        // Just print t--x-he data out to confirm we got it.

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get crossword servlet");

//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        JsonObject jsonObj = new JsonObject();
//        Gson jsonString = new Gson();
//        Gson jsonStringWords = new Gson();
//        Gson jsonStringDetails = new Gson();
//        JsonArray jsonString1 = jsonString.toJsonTree(puzzle).getAsJsonArray();;
//        JsonArray jsonString2 = jsonStringWords.toJsonTree(list).getAsJsonArray();;
//        //JsonArray jsonString3 = jsonStringDetails.toJsonTree(infolist).getAsJsonArray();;
//        jsonObj.add("jsonArray1", jsonString1);
//        jsonObj.add("jsonArray2", jsonString2);
//        //jsonObj.add("jsonArray3", jsonString3);
//        out.println(jsonObj.toString());
//        list.clear();

    }
}


