package com.kurtis_project.kurtis_project;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "CrosswordListGetServlet", value = "/api/crossword_get")
public class CrosswordListGetServlet extends HttpServlet {
    private final static Logger log = Logger.getLogger(CrosswordListGetServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get people servlet");

        // Get setup up to output JSON text
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Use our DAO to get a list of people
        List <PuzzleInfo> puzzleList = PuzzleInfoDAO.getPuzzleList();

        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(puzzleList);

        // Write out that string
        out.println(jsonString);
    }
}
