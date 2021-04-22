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




@WebServlet(name = "CrosswordServlet", value = "/api/crossword")
public class CrosswordServlet extends HttpServlet {
    ArrayList<Word> list = new ArrayList<>();

    public static ArrayList matrix(String filename) throws IOException {
        ArrayList<String> table = new ArrayList<>();
        File reader = new File(filename);
        Scanner in = new Scanner(reader);
        while(in.hasNextLine()) {
            String question = in.nextLine();
            table.add(question);
        }
        in.close();
        return table;
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

        for (int i = 0; i < puzzleInfo.getwords().size(); i++) {
            list.add(new Word(puzzleInfo.getwords().get(i), puzzleInfo.getclues().get(i)));
        }

        // Just print the data out to confirm we got it.
        out.println("{\"success\": \"Successful insert.\"}");

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

        CrosswordCreator puzzle1 = new CrosswordCreator(40, 40, "empty", 2000, list);
        int spins = 10;
        int x = 1;
        boolean complete = false;
        while (x < spins && !complete){
            try {
                String[][] puzzle = puzzle1.computeCrossword();
                JsonObject jsonObj = new JsonObject();
                Gson jsonString = new Gson();
                Gson jsonStringWords = new Gson();

                puzzle1.display(puzzle);
                JsonArray jsonString1 = jsonString.toJsonTree(puzzle).getAsJsonArray();;
                JsonArray jsonString2 = jsonStringWords.toJsonTree(list).getAsJsonArray();;
                jsonObj.add("jsonArray1", jsonString1);
                jsonObj.add("jsonArray2", jsonString2);
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
        list = new ArrayList<>();


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
