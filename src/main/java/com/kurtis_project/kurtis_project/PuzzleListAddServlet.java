package com.kurtis_project.kurtis_project;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "PuzzleListAdd", value = "/api/puzzle_add")
public class PuzzleListAddServlet extends HttpServlet {
    private final static Logger log = Logger.getLogger(PuzzleListAddServlet.class.getName());
    private final Pattern titleValidationPattern;
    private final Pattern authorValidationPattern;
    private final Pattern wordValidationPattern;
    private final Pattern clueValidationPattern;
    private final Pattern dateValidationPattern;
    private final Pattern idValidationPattern;


    /**
     * Our constructor
     */
    public PuzzleListAddServlet() {
//        // --- Compile and set up all the regular expression patterns here ---
        titleValidationPattern = Pattern.compile("^[A-Za-z0-9]{1,20}$");
        authorValidationPattern = Pattern.compile("^[A-Za-z0-9]{1,20}$");
        wordValidationPattern = Pattern.compile("^[A-Za-z]{1,20}$");
        clueValidationPattern = Pattern.compile("^[A-Za-z \\-+_,.=0-9]{1,250}$");
//      emailValidationPattern = Pattern.compile("^[\\w.]+@[\\w.]+$");
        dateValidationPattern = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
        idValidationPattern = Pattern.compile("^[0-9]{0,10}$");
//
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Get to doPost");
        log.log(Level.INFO, "doPost for PuzzleListAdd Servlet");

        // You can output in any format, text/JSON, text/HTML, etc. We'll keep it simple
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Open the request for reading. Read in each line, put it into a string.
        // Yes, I think there should be an easier way.
        java.io.BufferedReader in = request.getReader();
        String requestString = new String();
        for (String line; (line = in.readLine()) != null; requestString += line);

        // Log the string we got as a request, just as a check
        log.log(Level.INFO, requestString);

        // Great! Now we want to parse the object, and pop it into our business object. Field
        // names have to match. That's the magic.
        Jsonb jsonb = JsonbBuilder.create();
        PuzzleInfo formTestObject = jsonb.fromJson(requestString, PuzzleInfo.class);

        Matcher mid = idValidationPattern.matcher(formTestObject.getId());
        if (!mid.find()) {
            out.println("{\"error\" : \"Error validating ID.\"}");
            return;
        }

        Matcher mTitle = titleValidationPattern.matcher(formTestObject.getTitle());
        if (!mTitle.find()) {
            out.println("{\"error\": \"Error validating title.\"}");
            return;
        }

        Matcher mAuthor = authorValidationPattern.matcher(formTestObject.getAuthor());
        if (!mAuthor.find()) {
            out.println("{\"error\": \"Error validating author.\"}");
            return;
        }

        for (int i = 0; i < formTestObject.getwords().size(); i++) {
            Matcher mWord = wordValidationPattern.matcher(formTestObject.getwords().get(i));
            if (!mWord.find()) {
                out.println("{\"error\" : \"Error validating a word.\"}");
                return;
            }
            Matcher mClue = clueValidationPattern.matcher(formTestObject.getclues().get(i));
            if (!mClue.find()) {
                out.println("{\"error\" : \"Error validating a clue.\"}");
                return;
            }
        }

        Matcher mDate = dateValidationPattern.matcher(formTestObject.getDate());
        if (!mDate.find()) {
            out.println("{\"error\" : \"Error validating Date.\"}");
            return;
        }

        HttpSession session = request.getSession();
        String loginObject = (String)session.getAttribute("loggedIn");
        if (loginObject!=null){
            String loginID = (String)session.getAttribute("loginID");
            String userID = PuzzleInfoDAO.getUserID(loginID);
            String newID = PuzzleInfoDAO.addPuzzle(formTestObject, userID);
            out.println("{\"success\": \"" + newID + "\"}");
        }else{
            String newID = PuzzleInfoDAO.addPuzzle(formTestObject, "0");
            out.println("{\"success\": \"" + newID + "\"}");
        }
        //}else {
            //PersonDAO.updatePerson(formTestObject);
        //}
        // If we made it this far we have success.
        //out.println("{\"success\": \"" + newID + "\"}");

        // Send something back to the client. Really, we should send a JSON, but
        // we'll keep things simple.
        // out.println("Received Object");

        // Use our DAO to get a list of people



    }
}
