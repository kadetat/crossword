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
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@WebServlet(name = "CrosswordServlet", value = "/api/crossword")
public class CrosswordServlet extends HttpServlet {
    ArrayList<Word> list = new ArrayList<>();
    ArrayList<String> infolist = new ArrayList<>();
    private Pattern idValidationPattern;
    public CrosswordServlet() {
        idValidationPattern = Pattern.compile("^[0-9]{0,10}$");
    }


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
        Matcher mid = idValidationPattern.matcher(puzzleInfo.getId());

        if (!mid.find()) {
            out.println("{\"error\" : \"Error validating ID.\"}");
            return;
        }
        System.out.println(puzzleInfo.getId());
        list.clear();
        infolist.clear();

        if (!puzzleInfo.getId().equals("null2") ) {
            List <PuzzleInfo> puzzleInfoList = PuzzleInfoDAO.getPuzzleInfo(puzzleInfo.getId());
            for (int i = 0; i < puzzleInfoList.size(); i++) {
                System.out.println(puzzleInfoList.get(i).toString());
                System.out.println(puzzleInfoList.get(i).getOneWord());
                list.add(new Word(puzzleInfoList.get(i).getOneWord(), puzzleInfoList.get(i).getOneClue()));
            }
            System.out.println("herekade");
            List <PuzzleInfo> puzzleInfoDetails = PuzzleInfoDAO.getPuzzledetails(puzzleInfo.getId());
            System.out.println("here2");
            System.out.println(puzzleInfoDetails);
            System.out.println(puzzleInfoDetails.toString());
            System.out.println(puzzleInfoDetails.get(0).getId());
            infolist.add(puzzleInfoDetails.get(0).getId());
            System.out.println("here3");
            System.out.println(puzzleInfoDetails.get(0).getTitle());
            infolist.add(puzzleInfoDetails.get(0).getTitle());
            System.out.println("here4");
            infolist.add(puzzleInfoDetails.get(0).getAuthor());
            infolist.add(puzzleInfoDetails.get(0).getDate());
            System.out.println("here5");

        }else {
            System.out.println("here");
            for (int i = 0; i < puzzleInfo.getwords().size(); i++) {
                list.add(new Word(puzzleInfo.getwords().get(i), puzzleInfo.getclues().get(i)));
            }
            infolist.add(puzzleInfo.getId());
            infolist.add(puzzleInfo.getTitle());
            infolist.add(puzzleInfo.getAuthor());
            infolist.add(puzzleInfo.getDate());
        }

        CrosswordCreator puzzle1 = new CrosswordCreator(50, 80, "empty", 2000, list);
        int spins = 100;
        int x = 1;
        boolean complete = false;
        while (x < spins && !complete){
            try {
                String[][] puzzle = puzzle1.computeCrossword();
                JsonObject jsonObj = new JsonObject();
                Gson jsonString = new Gson();
                Gson jsonStringWords = new Gson();
                Gson jsonStringDetails = new Gson();

                puzzle1.display(puzzle);
                JsonArray jsonString1 = jsonString.toJsonTree(puzzle).getAsJsonArray();;
                JsonArray jsonString2 = jsonStringWords.toJsonTree(list).getAsJsonArray();;
                JsonArray jsonString3 = jsonStringDetails.toJsonTree(infolist).getAsJsonArray();;
                jsonObj.add("jsonArray1", jsonString1);
                jsonObj.add("jsonArray2", jsonString2);
                jsonObj.add("jsonArray3", jsonString3);
                out.println(jsonObj.toString());
                System.out.println(jsonString1);
                System.out.println("Number of Spins: " + x);
                complete = true;
            } catch (Exception NullPointerException){
                x ++;
                System.out.println("nowhere to put word");
                if (x == spins) {
                    System.out.println("Error: reached spins");
                }
            }
        }
        // Just print t--x-he data out to confirm we got it.
        //out.println("{\"success\": \"Successful insert.\"}");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get crossword servlet");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

//        //ArrayList<Word> list = new ArrayList<>();
//        list.add(new Word("basketballgoal", "where you shoot a basketball."));
//        list.add(new Word("football", "A sport that uses a football."));
//        list.add(new Word("incredible", "Mr. .... (A superhero)"));
//        list.add(new Word("baseballbat", "What you use to hit a baseball."));
//        list.add(new Word("glove", "What you use to catch a baseball"));
//        list.add(new Word("those", "things over there"));
//        list.add(new Word("kid", "people who go to elementary school."));
//        list.add(new Word("ball", "A thing you can shoot, kick, or catch."));
//        list.add(new Word("lie", "Not truthful"));
//        list.add(new Word("goat", "Greatest Of All Time"));
//        list.add(new Word("kurtis", "Kade's twin"));
//        list.add(new Word("golf", "sport that uses golfballs."));
//        list.add(new Word("dad", "A father"));
//        list.add(new Word("money", "What you use to buy what you want."));
//        list.add(new Word("mom", "A mother"));
//        list.add(new Word("hello", "Opposite of goodbye."));
//        list.add(new Word("bedtime", "When you go to bed."));
//        list.add(new Word("numbers", "123456789"));
//        list.add(new Word("words", "Things you say or write"));
//        list.add(new Word("baseball", "Sport that uses baseballs"));
//        list.add(new Word("ballboy", "Boy that gets balls"));
//        list.add(new Word("softball", "Sport that uses softballs."));
//        list.add(new Word("november", "Month after October"));
//        list.add(new Word("october", "Month after September"));
//        list.add(new Word("buckets", "Kade's favorite catch-phrase"));
//        list.add(new Word("horse", "A common animal a cowboy rides"));
//        list.add(new Word("company", "A place of employment"));
//        list.add(new Word("teacher", "Someone who teaches"));
//        list.add(new Word("sammy", "Kade's roommate"));
//        list.add(new Word("tatkenhorst", "Kade's lastname"));

        CrosswordCreator puzzle1 = new CrosswordCreator(50, 80, "empty", 2000, list);
        int spins = 100;
        int x = 1;
        boolean complete = false;
        while (x < spins && !complete){
            try {
                String[][] puzzle = puzzle1.computeCrossword();
                JsonObject jsonObj = new JsonObject();
                Gson jsonString = new Gson();
                Gson jsonStringWords = new Gson();
                Gson jsonStringDetails = new Gson();

                puzzle1.display(puzzle);
                JsonArray jsonString1 = jsonString.toJsonTree(puzzle).getAsJsonArray();;
                JsonArray jsonString2 = jsonStringWords.toJsonTree(list).getAsJsonArray();;
                JsonArray jsonString3 = jsonStringDetails.toJsonTree(infolist).getAsJsonArray();;
                jsonObj.add("jsonArray1", jsonString1);
                jsonObj.add("jsonArray2", jsonString2);
                jsonObj.add("jsonArray3", jsonString3);
                out.println(jsonObj.toString());
                System.out.println(jsonString1);
                System.out.println("Number of Spins: " + x);
                complete = true;
            } catch (Exception NullPointerException){
                x ++;
                System.out.println("nowhere to put word");
                if (x == spins) {
                    System.out.println("Error: reached spins");
                }
            }
        }


        // Write out that string
        // out.println(jsonString);
//        try {
//            JsonArray jsonArray1 = new Gson().toJsonTree(matrix(
//                    "C:\\Users\\kadet\\OneDrive\\Desktop\\kurtis_project\\src\\main\\java\\com\\kurtis_project\\kurtis_project\\text.txt")
//                    ).getAsJsonArray();
//            JsonArray jsonArray2 = new Gson().toJsonTree(matrix(
//                    "C:\\Users\\kadet\\OneDrive\\Desktop\\kurtis_project\\src\\main\\java\\com\\kurtis_project\\kurtis_project\\answers")
//            ).getAsJsonArray();
//            jsonObj.add("jsonArray1", jsonArray1);
//            jsonObj.add("jsonArray2", jsonArray2);
//            // Write out that string
//            out.println(jsonObj.toString());
//            System.out.println(jsonObj.toString());
//        }catch (IOException FileNotFoundException) {
//            System.out.println("File Not Found");
//        }


    }
}

