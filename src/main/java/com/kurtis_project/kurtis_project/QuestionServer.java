package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

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


@WebServlet(name = "CrosswordServlet", value = "/api/crossword-servlet")
public class QuestionServer extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get crossword servlet");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        ArrayList<Word> list = new ArrayList<>();
        list.add(new Word("basketballgoal", "1"));
        list.add(new Word("football", "2"));
        list.add(new Word("incredible", "3"));
        list.add(new Word("baseballbat", "4"));
        list.add(new Word("glove", "5"));
        list.add(new Word("those", "6"));
        list.add(new Word("kid", "7"));
        list.add(new Word("ball", "8"));
        list.add(new Word("lie", "9"));
        list.add(new Word("goat", "10"));
        list.add(new Word("kurtis", "11"));
        list.add(new Word("golf", "12"));
        list.add(new Word("dad", "13"));
        list.add(new Word("money", "14"));
        list.add(new Word("mom", "15"));
        list.add(new Word("hello", "16"));
        list.add(new Word("bedtime", "17"));
        list.add(new Word("numbers", "18"));
        list.add(new Word("words", "19"));
        list.add(new Word("baseball", "20"));
        list.add(new Word("ballboy", "21"));
        list.add(new Word("softball", "22"));
        list.add(new Word("account", "23"));
        list.add(new Word("october", "24"));
        list.add(new Word("company", "25"));
        list.add(new Word("horse", "26"));
        list.add(new Word("company", "27"));
        list.add(new Word("teacher", "28"));
        list.add(new Word("sammy", "29"));
        list.add(new Word("tatkenhorst", "30"));

        CrosswordCreator puzzle1 = new CrosswordCreator(40, 40, "empty", 2000, list);
        //JsonArray jsonString2 = jsonString.toJsonTree(list).getAsJsonArray();;



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


