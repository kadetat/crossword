package com.kurtis_project.kurtis_project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.LinkedList;
import java.sql.PreparedStatement;

/**
 * Data Access Object for the Person table/class
 */
public class PuzzleInfoDAO {
    private final static Logger log = Logger.getLogger(PuzzleInfoDAO.class.getName());

    /**
     * Get a list of the people in the database.
     * @return Returns a list of instances of the People class.
     */
    public static List<PuzzleInfo> getPuzzleInfo(String id) {
        log.log(Level.FINE, "Get Puzzle Info");

        // Create an empty linked list to put the people we get from the
        // database into.
        List<PuzzleInfo> list = new LinkedList<PuzzleInfo>();

        // Declare our variables
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Databases are unreliable. Use some exception handling
        try {
            // Get our database connection
            conn = DBHelper.getConnection();

            // This is a string that is our SQL query.
            // Update for all our fields

            //String sql = "select word, clue, from person";

            System.out.println(id);
            // If you had parameters, it would look something like
            String sql = "select word, clue from wordList where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(id));

            // Execute the SQL and get the results
            rs = stmt.executeQuery();

            // Loop through each record
            while(rs.next()) {
                // Create a new instance of the Person object.
                // You'll need to define that somewhere. Just a simple class
                // with getters and setters on the fields.
                PuzzleInfo puzzle = new PuzzleInfo();

                // Get the data from the result set, and copy it to the Person
                // object.
                puzzle.setOneWord(rs.getString("word"));
                puzzle.setOneClue(rs.getString("clue"));

                // Add this person to the list so we can return it.
                list.add(puzzle);
            }
        } catch (SQLException se) {
            log.log(Level.SEVERE, "SQL Error", se );
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e );
        } finally {
            // Ok, close our result set, statement, and connection
            try { if (rs != null) rs.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(stmt != null) stmt.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(conn != null) conn.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
        }
        // Done! Return the results
        return list;
    }

    public static List<PuzzleInfo> getPuzzleList() {
        log.log(Level.FINE, "Get people");

        // Create an empty linked list to put the people we get from the
        // database into.
        List<PuzzleInfo> list = new LinkedList<PuzzleInfo>();

        // Declare our variables
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Databases are unreliable. Use some exception handling
        try {
            // Get our database connection
            conn = DBHelper.getConnection();

            // This is a string that is our SQL query.
            // Update for all our fields

            String sql = "select id, title, author, date from puzzle";


            // If you had parameters, it would look something like
            // String sql = "select id, first, last, phone from person where id = ?";

            // Create an object with all the info about our SQL statement to run.
            stmt = conn.prepareStatement(sql);

            // If you had parameters, they would be set wit something like:
            // stmt.setString(1, "1");

            // Execute the SQL and get the results
            rs = stmt.executeQuery();

            // Loop through each record
            while(rs.next()) {
                // Create a new instance of the Person object.
                // You'll need to define that somewhere. Just a simple class
                // with getters and setters on the fields.
                PuzzleInfo puzzle = new PuzzleInfo();

                // Get the data from the result set, and copy it to the Person
                // object.
                puzzle.setId(rs.getString("id"));
                puzzle.setTitle(rs.getString("title"));
                puzzle.setAuthor(rs.getString("author"));
                puzzle.setDate(rs.getString("date"));

                // Add this person to the list so we can return it.
                list.add(puzzle);
            }
        } catch (SQLException se) {
            log.log(Level.SEVERE, "SQL Error", se );
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e );
        } finally {
            // Ok, close our result set, statement, and connection
            try { if (rs != null) rs.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(stmt != null) stmt.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(conn != null) conn.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
        }
        // Done! Return the results
        return list;
    }

//    public static void addPerson(Person personObject) {
//        log.log(Level.FINE, "Add Person");
//
//        // Create an empty linked list to put the people we get from the
//        // database into.
//
//        // Declare our variables
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        // Databases are unreliable. Use some exception handling
//        try {
//            // Get our database connection
//            conn = DBHelper.getConnection();
//
//            // This is a string that is our SQL query.
//            // Update for all our fields
//
//            String sql = "INSERT INTO person (first, last, phone, birthday, email) " +
//                    "VALUES (?, ?, ?, ?, ?);";
//            // If you had parameters, it would look something like
//            // String sql = "select id, first, last, phone from person where id = ?";
//
//            // Create an object with all the info about our SQL statement to run.
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, personObject.getFirst());
//            stmt.setString(2, personObject.getLast());
//            stmt.setString(3, personObject.getPhone());
//            stmt.setString(4, personObject.getBirthday());
//            stmt.setString(5, personObject.getEmail());
//
//            // If you had parameters, they would be set wit something like:
//            // stmt.setString(1, "1");
//
//            // Execute the SQL and get the results
//            stmt.executeUpdate();
//
//
//        } catch (SQLException se) {
//            log.log(Level.SEVERE, "SQL Error", se );
//        } catch (Exception e) {
//            log.log(Level.SEVERE, "Error", e );
//        } finally {
//            // Ok, close our result set, statement, and connection
//            try { if(stmt != null) stmt.close(); }
//            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
//
//            try { if(conn != null) conn.close(); }
//            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
//        }
//    }
//
//    public static void updatePerson(Person personObject) {
//        log.log(Level.FINE, "Add Person");
//
//        // Create an empty linked list to put the people we get from the
//        // database into.
//
//        // Declare our variables
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        // Databases are unreliable. Use some exception handling
//        try {
//            // Get our database connection
//            conn = DBHelper.getConnection();
//
//            // This is a string that is our SQL query.
//            // Update for all our fields
//
//            String sql = "update person set first=?, last=?, phone=?, birthday=?, email=? where id=?;";
//            // If you had parameters, it would look something like
//            // String sql = "select id, first, last, phone from person where id = ?";
//
//            // Create an object with all the info about our SQL statement to run.
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, personObject.getFirst());
//            stmt.setString(2, personObject.getLast());
//            stmt.setString(3, personObject.getPhone());
//            stmt.setString(4, personObject.getBirthday());
//            stmt.setString(5, personObject.getEmail());
//            stmt.setString(6, String.valueOf(personObject.getId()));
//
//
//            // If you had parameters, they would be set wit something like:
//            // stmt.setString(1, "1");
//
//            // Execute the SQL and get the results
//            stmt.executeUpdate();
//
//
//        } catch (SQLException se) {
//            log.log(Level.SEVERE, "SQL Error", se );
//        } catch (Exception e) {
//            log.log(Level.SEVERE, "Error", e );
//        } finally {
//            // Ok, close our result set, statement, and connection
//            try { if(stmt != null) stmt.close(); }
//            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
//
//            try { if(conn != null) conn.close(); }
//            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
//        }
//    }
//
//    public static void deletePerson(Person personObject) {
//        log.log(Level.FINE, "Delete Person");
//
//        // Create an empty linked list to put the people we get from the
//        // database into.
//
//        // Declare our variables
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        // Databases are unreliable. Use some exception handling
//        try {
//            // Get our database connection
//            conn = DBHelper.getConnection();
//
//            // This is a string that is our SQL query.
//            // Update for all our fields
//
//            String sql = "delete from person where id = ?";
//            // If you had parameters, it would look something like
//            // String sql = "select id, first, last, phone from person where id = ?";
//
//            // Create an object with all the info about our SQL statement to run.
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, personObject.getId());
//
//            // Execute the SQL and get the results
//            stmt.executeUpdate();
//
//
//        } catch (SQLException se) {
//            log.log(Level.SEVERE, "SQL Error", se );
//        } catch (Exception e) {
//            log.log(Level.SEVERE, "Error", e );
//        } finally {
//            // Ok, close our result set, statement, and connection
//            try { if(stmt != null) stmt.close(); }
//            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
//
//            try { if(conn != null) conn.close(); }
//            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
//        }
//    }
}
