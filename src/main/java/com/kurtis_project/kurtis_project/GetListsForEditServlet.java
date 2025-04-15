package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@WebServlet(name = "GetListsForEditServlet", value = "/api/edit_Crossword")
public class GetListsForEditServlet extends HttpServlet {
    ArrayList<Word> list = new ArrayList<>();
    ArrayList<String> infolist = new ArrayList<>();
    private Pattern idValidationPattern;
    public GetListsForEditServlet() {
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

        HttpSession session = request.getSession();
        String loginObject = (String)session.getAttribute("loggedIn");
        String loginID = (String)session.getAttribute("loginID");

        list.clear();
        infolist.clear();

        if (loginObject!=null) {
            String userID = PuzzleInfoDAO.getUserID(loginID);
            List <PuzzleInfo> puzzleInfoDetails = PuzzleInfoDAO.getPuzzledetails(puzzleInfo.getId());
            String checkedID = puzzleInfoDetails.get(0).getUserID();
            if (checkedID == null){
                checkedID = "0";
            }
            String title = puzzleInfoDetails.get(0).getTitle();
            String id = puzzleInfoDetails.get(0).getId();
            String author = puzzleInfoDetails.get(0).getAuthor();
            String date = puzzleInfoDetails.get(0).getDate();
            if (checkedID.equals(userID) || userID.equals("41") || userID.equals("45")) {
                List<PuzzleInfo> puzzleInfoList = PuzzleInfoDAO.getPuzzleInfo(puzzleInfo.getId());
                for (int i = 0; i < puzzleInfoList.size(); i++) {
                    list.add(new Word(puzzleInfoList.get(i).getOneWord(), puzzleInfoList.get(i).getOneClue()));
                }
                infolist.add(id);
                infolist.add(title);
                infolist.add(author);
                infolist.add(date);

                JsonObject jsonObj = new JsonObject();
                Gson jsonStringWords = new Gson();
                Gson jsonStringDetails = new Gson();
                JsonArray jsonString2 = jsonStringWords.toJsonTree(list).getAsJsonArray();
                JsonArray jsonString3 = jsonStringDetails.toJsonTree(infolist).getAsJsonArray();
                jsonObj.add("jsonArray2", jsonString2);
                jsonObj.add("jsonArray3", jsonString3);
                out.println(jsonObj.toString());
            }else {
                    out.println("{\"error\": \"Can't get data.\"}");
            }
        }else {
                out.println("{\"error\": \"Not logged in.\"}");
        }
    }


}

