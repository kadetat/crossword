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

    public static List<PuzzleInfo> getPuzzledetails(String id) {
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
            String sql = "select id, title, author, date from puzzle where id = ?";
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

    public static String addPuzzle(PuzzleInfo puzzleInfo, String userIDNumber) {
        String neededID = null;
        log.log(Level.FINE, "Add Puzzle");
        System.out.println("At add puzzle");

        // Create an empty linked list to put the people we get from the
        // database into.

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
            if (userIDNumber.equals("0")){
                String sql2 = "INSERT INTO puzzle (title, author, date) " +
                        "VALUES (?, ?, ?);";

                stmt = conn.prepareStatement(sql2);
                stmt.setString(1, puzzleInfo.getTitle());
                stmt.setString(2, puzzleInfo.getAuthor());
                stmt.setString(3, puzzleInfo.getDate());
            } else{
                String sql2 = "INSERT INTO puzzle (title, author, date, userID) " +
                        "VALUES (?, ?, ?, ?);";

                stmt = conn.prepareStatement(sql2);
                stmt.setString(1, puzzleInfo.getTitle());
                stmt.setString(2, puzzleInfo.getAuthor());
                stmt.setString(3, puzzleInfo.getDate());
                stmt.setString(4, userIDNumber);
            }

            stmt.executeUpdate();
            System.out.println("sql1");

            String sql3 = "select id from puzzle where title = ? and author = ? and date = ?";
            
            stmt = conn.prepareStatement(sql3);
            stmt.setString(1, puzzleInfo.getTitle());
            stmt.setString(2, puzzleInfo.getAuthor());
            stmt.setString(3, puzzleInfo.getDate());
            // If you had parameters, they would be set wit something like:
            // stmt.setString(1, "1");

            // Execute the SQL and get the results
            rs = stmt.executeQuery();

            while(rs.next()) {
                neededID = rs.getString("id");
            }
            System.out.println("sql2");

            for (int i = 0; i < puzzleInfo.getwords().size(); i++) {

                String sql = "INSERT INTO wordList (word, clue, id) " +
                        "VALUES (?, ?, ?);";
                // If you had parameters, it would look something like
                // String sql = "select id, first, last, phone from person where id = ?";

                // Create an object with all the info about our SQL statement to run.
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, puzzleInfo.getwords().get(i));
                stmt.setString(2, puzzleInfo.getclues().get(i));
                stmt.setString(3, neededID);


                // If you had parameters, they would be set wit something like:
                // stmt.setString(1, "1");

                // Execute the SQL and get the results
                stmt.executeUpdate();
            }
            System.out.println("sql3");


        } catch (SQLException se) {
            log.log(Level.SEVERE, "SQL Error", se );
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e );
        } finally {
            // Ok, close our result set, statement, and connection
            try { if(stmt != null) stmt.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(conn != null) conn.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
        }
        return neededID;
    }

    public static void allowEmail(String amazonID) {
        log.log(Level.FINE, "Allow Email called");

        // Create an empty linked list to put the people we get from the
        // database into.

        // Declare our variables
        Connection conn = null;
        PreparedStatement stmt = null;

        // Databases are unreliable. Use some exception handling
        try {
            // Get our database connection
            conn = DBHelper.getConnection();

            // This is a string that is our SQL query.
            // Update for all our fields

            String sql = "update userList set allowEmail=1 where amazonID=?;";
            // If you had parameters, it would look something like
            // String sql = "select id, first, last, phone from person where id = ?";

            // Create an object with all the info about our SQL statement to run.
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, amazonID);

            // Execute the SQL and get the results
            stmt.executeUpdate();


        } catch (SQLException se) {
            log.log(Level.SEVERE, "SQL Error", se );
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e );
        } finally {
            // Ok, close our result set, statement, and connection
            try { if(stmt != null) stmt.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(conn != null) conn.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
        }
    }

    public static void declineEmail(String amazonID) {
        log.log(Level.FINE, "Allow Email called");

        // Create an empty linked list to put the people we get from the
        // database into.

        // Declare our variables
        Connection conn = null;
        PreparedStatement stmt = null;

        // Databases are unreliable. Use some exception handling
        try {
            // Get our database connection
            conn = DBHelper.getConnection();

            // This is a string that is our SQL query.
            // Update for all our fields

            String sql = "update userList set allowEmail=0 where amazonID=?;";
            // If you had parameters, it would look something like
            // String sql = "select id, first, last, phone from person where id = ?";

            // Create an object with all the info about our SQL statement to run.
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, amazonID);

            // Execute the SQL and get the results
            stmt.executeUpdate();


        } catch (SQLException se) {
            log.log(Level.SEVERE, "SQL Error", se );
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e );
        } finally {
            // Ok, close our result set, statement, and connection
            try { if(stmt != null) stmt.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(conn != null) conn.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
        }
    }

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

    public static Boolean checkIfUserExists(String AmazonID) {
        log.log(Level.FINE, "Check User Existing");

        // Create an empty linked list to put the people we get from the
        // database into.
        Boolean exists = false;

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

            // If you had parameters, it would look something like
            String sql = "select username from userList where amazonID = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, AmazonID);

            // Execute the SQL and get the results
            rs = stmt.executeQuery();

            // Loop through each record
            while(rs.next()) {
                if (rs.getString("username") != null){
                    exists = true;
                }
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
        return exists;
    }

    public static Boolean addUser(String AmazonID, String username, String email) {
        log.log(Level.FINE, "Add User");

        // Create an empty linked list to put the people we get from the
        // database into.
        Boolean added = false;

        // Declare our variables
        Connection conn = null;
        PreparedStatement stmt = null;

        // Databases are unreliable. Use some exception handling
        try {
            // Get our database connection
            conn = DBHelper.getConnection();

            // This is a string that is our SQL query.
            // Update for all our fields

            //String sql = "select word, clue, from person";

            // If you had parameters, it would look something like
            String sql = "INSERT INTO userList (username, email, amazonID) " +
                    "VALUES (?, ?, ?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, AmazonID);
            // Execute the SQL and get the results
            stmt.executeUpdate();
            added = true;

        } catch (SQLException se) {
            log.log(Level.SEVERE, "SQL Error", se );
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e );
        } finally {
            // Ok, close our result set, statement, and connection
            try { if(stmt != null) stmt.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }

            try { if(conn != null) conn.close(); }
            catch (Exception e) { log.log(Level.SEVERE, "Error", e ); }
        }
        // Done! Return the results
        return added;
    }

    public static List<PuzzleInfo> getPlayedList(String userID) {
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

            //String sql = "select id, title, author, date from puzzle";
            String sql = "SELECT id, title, author, completed FROM cis320.puzzle AS A RIGHT JOIN cis320.playedList AS B ON A.id = B.puzzleID WHERE B.userID = ?";

            // If you had parameters, it would look something like
            // String sql = "select id, first, last, phone from person where id = ?";

            // Create an object with all the info about our SQL statement to run.
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);
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
                puzzle.setCompleted(rs.getString("completed"));

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

    public static String getUserID(String AmazonID) {
        log.log(Level.FINE, "Check User Existing");

        // Create an empty linked list to put the people we get from the
        // database into.
        String IDnumber = "0";

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

            // If you had parameters, it would look something like
            String sql = "select userID from userList where amazonID = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, AmazonID);

            // Execute the SQL and get the results
            rs = stmt.executeQuery();

            // Loop through each record
            while(rs.next()) {
                if (rs.getString("userID") != null){
                    IDnumber = rs.getString("userID");
                }
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
        return IDnumber;
    }

    public static List<PuzzleInfo> getBuiltList(String IDnumber) {
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

            String sql = "select id, title, author, date from puzzle where userID=?";



            // If you had parameters, it would look something like
            // String sql = "select id, first, last, phone from person where id = ?";

            // Create an object with all the info about our SQL statement to run.
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, IDnumber);

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
}
