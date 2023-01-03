package com.kurtis_project.kurtis_project;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "SavePuzzleEditsServlet", value = "/api/puzzle_edit")
public class SavePuzzleEditsServlet extends HttpServlet {
    private final static Logger log = Logger.getLogger(PuzzleListAddServlet.class.getName());
    private Pattern titleValidationPattern;
//    private Pattern lastValidationPattern;
//    private Pattern phoneValidationPattern;
//    private Pattern emailValidationPattern;
//    private Pattern birthdayValidationPattern;
//    private Pattern idValidationPattern;


    /**
     * Our constructor
     */
    public SavePuzzleEditsServlet() {
//        // --- Compile and set up all the regular expression patterns here ---
        titleValidationPattern = Pattern.compile("^[A-Za-z]{1,20}$");
//        lastValidationPattern = Pattern.compile("^[A-Za-z]{1,18}$");
//        phoneValidationPattern = Pattern.compile("^[0-9]{3}-?[0-9]{3}-?[0-9]{4}$");
//        emailValidationPattern = Pattern.compile("^[\\w.]+@[\\w.]+$");
//        birthdayValidationPattern = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
//        idValidationPattern = Pattern.compile("^[0-9]{0,10}$");
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
        PuzzleInfo puzzleInfo = jsonb.fromJson(requestString, PuzzleInfo.class);

//        Matcher mid = idValidationPattern.matcher(formTestObject.getId());
//
//        if (!mid.find()) {
//            out.println("{\"error\" : \"Error validating ID.\"}");
//            return;
//        }
//
//        Matcher mTitle = titleValidationPattern.matcher(formTestObject.getTitle());

//        if (mTitle.find()) {
//            PuzzleInfoDAO.addPuzzle(formTestObject);
//            out.println("{\"success\": \"Successful insert.\"}");
//            return;
//        } else {
//            PuzzleInfoDAO.addPuzzle(formTestObject);
//            out.println("{\"success\": \"Successful insert.\"}");
//            return;
//        }
//
//        Matcher mLast = lastValidationPattern.matcher(formTestObject.getLast());
//
//        if (!mLast.find()) {
//            out.println("{\"error\" : \"Error validating last name.\"}");
//            return;
//        }
//
//        Matcher mPhone = phoneValidationPattern.matcher(formTestObject.getPhone());
//
//        if (!mPhone.find()) {
//            out.println("{\"error\" : \"Error validating phone number.\"}");
//            return;
//        }
//
//        Matcher mEmail = emailValidationPattern.matcher(formTestObject.getEmail());
//
//        if (!mEmail.find()) {
//            out.println("{\"error\" : \"Error validating email.\"}");
//            return;
//        }
//
//        Matcher mBirthday = birthdayValidationPattern.matcher(formTestObject.getBirthday());
//
//        if (!mBirthday.find()) {
//            out.println("{\"error\" : \"Error validating Birthday.\"}");
//            return;
//        }
        System.out.println(puzzleInfo.toString());
        //if (formTestObject.getId().equals("")){
        HttpSession session = request.getSession();
        String loginObject = (String)session.getAttribute("loggedIn");
        if (loginObject!=null) {
            String loginID = (String)session.getAttribute("loginID");
            String userID = PuzzleInfoDAO.getUserID(loginID);
            List<PuzzleInfo> puzzleInfoDetails = PuzzleInfoDAO.getPuzzledetails(puzzleInfo.getId());
            String puzzleID = puzzleInfoDetails.get(0).getId();
            String checkedID = puzzleInfoDetails.get(0).getUserID();
            if (checkedID == null) {
                checkedID = "0";
            }
            if (checkedID.equals(userID) || userID.equals("41") || userID.equals("45")) {
                if (checkedID == "0") {
                    checkedID = null;
                }
                PuzzleInfoDAO.updatePuzzleInfo(puzzleInfo, puzzleID, checkedID);
                PuzzleInfoDAO.deletePuzzleContent(puzzleID);
                PuzzleInfoDAO.insertPuzzleContent(puzzleInfo, puzzleInfoDetails.get(0).getId());
                out.println("{\"success\": \"" + puzzleID + "\"}");
            }else {
                out.println("{\"error\": \"Not correctly logged in to lk,m0-++-save edits.\"}");
            }
        } else{
            out.println("{\"error\": \"Must be logged in to save edits.\"}");
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
