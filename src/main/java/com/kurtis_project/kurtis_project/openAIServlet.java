package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;


@WebServlet(name = "openAIServlet", value = "/api/askOpenAI")
public class openAIServlet extends HttpServlet {
    private final Pattern wordValidationPattern;


    private final static Logger log = Logger.getLogger(CrosswordServlet.class.getName());

    public openAIServlet(){
        wordValidationPattern = Pattern.compile("^[A-Za-z]{1,15}$");
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
        PuzzleInfo wordInfo = gson.fromJson(requestString, PuzzleInfo.class);
        String word = wordInfo.getOneWord();
        System.out.println(word);
        Matcher mWord = wordValidationPattern.matcher(word);
        if (!mWord.find()) {
            out.println("{\"error\" : \"Error validating a word.\"}");
            return;
        }
        String valueKey = System.getenv("OpenAI_Key");
        URL obj = new URL("https://api.openai.com/v1/completions");
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        //con.setRequestProperty("Authorization", "Bearer " + valueKey);
        con.setDoOutput(true);
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("model", "gpt-3.5-turbo-instruct");
        jsonObj.addProperty("prompt", "Create a crossword clue for the word " + word);
        jsonObj.addProperty("temperature", 0.4);
        jsonObj.addProperty("max_tokens", 10);
        jsonObj.addProperty("top_p", "1");
        jsonObj.addProperty("frequency_penalty", 0);
        jsonObj.addProperty("presence_penalty", 0);
        //jsonObj.addProperty("stop", "\n");
        OutputStream os = con.getOutputStream();
        os.write(jsonObj.toString().getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) { //success
            BufferedReader in2 = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String requestString2 = new String();

            for (String line; (line = in2.readLine()) != null; requestString2 += line) ;
            in2.close();
            Gson gson2 = new Gson();
            PuzzleInfo wordInfo2 = gson2.fromJson(requestString2, PuzzleInfo.class);
            Object[] choicesString = wordInfo2.getChoices();
            Gson gson3 = new Gson();
            PuzzleInfo wordInfo3 = gson3.fromJson(Arrays.stream(choicesString).toArray()[0].toString(), PuzzleInfo.class);
            String generatedClue = wordInfo3.getText();
            out.println("{\"generatedClue\": \"" + generatedClue + "\"}");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get crossword servlet");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();




    }
}

