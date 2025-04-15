package com.kurtis_project.kurtis_project;

import com.google.gson.Gson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.util.Map;




@WebServlet(name = "AmazonServlet", value = "/api/amazonHelp")
public class AmazonServlet extends HttpServlet {



    private final static Logger log = Logger.getLogger(CrosswordServlet.class.getName());


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // You can output in any format, text/JSON, text/HTML, etc. We'll keep it simple
        //response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        java.io.BufferedReader in = request.getReader();
        String requestString = new String();
        for (String line; (line = in.readLine()) != null; requestString += line);

        System.out.println(requestString);

        //JsonObject convertedObject = new Gson().fromJson(requestString, JsonObject.class);

        Gson gson = new Gson();
        AmazonInfo amazonInfo = gson.fromJson(requestString, AmazonInfo.class);
        //out.println("{\"success\":" + amazonInfo.getCode());
        URL obj = new URL("https://api.amazon.com/auth/o2/token");
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        con.setDoOutput(true);
        String postData = "grant_type=authorization_code&code=" + amazonInfo.getCode() +"&client_id=amzn1.application-oa2-client.502697efea7f490fb123f130c00bd2cf&client_secret=d10c740527f8d6704e54d0c1461bacfc68ad429d99526e468906a7863465069e";
        OutputStream os = con.getOutputStream();
        os.write(postData.getBytes());

        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) { //success
            BufferedReader in2 = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String requestString2 = new String();

            for (String line; (line = in2.readLine()) != null; requestString2 += line);
            in2.close();
            Gson gson2 = new Gson();
            AmazonInfo amazonInfo2 = gson2.fromJson(requestString2, AmazonInfo.class);
            String accessToken = amazonInfo2.getAccess_token();
            String refreshToken = amazonInfo2.getRefresh_token();

            // verify that the access token belongs to us
            Content c = Request.Get("https://api.amazon.com/auth/o2/tokeninfo?access_token=" + URLEncoder.encode(accessToken, "UTF-8"))
                    .execute()
                    .returnContent();

            Map m = new ObjectMapper().readValue(c.toString(), new TypeReference<Map>(){});

            if (!"amzn1.application-oa2-client.502697efea7f490fb123f130c00bd2cf".equals(m.get("aud"))) {
            // the access token does not belong to us
                throw new RuntimeException("Invalid token");
            }


            String pictureURL = "https://mdbootstrap.com/img/Photos/Avatars/avatar-5.webp";
            // exchange the access token for user profile
            c = Request.Get("https://api.amazon.com/user/profile?access_token="+ URLEncoder.encode(accessToken, "UTF-8"))
                    .execute()
                    .returnContent();
            Map m2 = new ObjectMapper().readValue(c.toString(), new TypeReference<Map>(){});
            //out.println("{\"success\":" + m2.get("name") + " " + m2.get("email") + " " + m2.get("user_id"));
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", "true");
            session.setAttribute("loginID", m2.get("user_id"));
            session.setAttribute("loginName", m2.get("name"));
            session.setAttribute("userEmail", m2.get("email"));
            session.setAttribute("pictureURL", pictureURL);
            Boolean exists = PuzzleInfoDAO.checkIfUserExists((String) m2.get("user_id"));

            if (!exists){
                Boolean added = PuzzleInfoDAO.addUser((String) m2.get("user_id"), (String) m2.get("name"), (String) m2.get("email"));
                if (added) {
                    out.println("{\"added\": \"good insert.\",");
                    out.println("\"name\": \"" + m2.get("name") +"\",");
                    out.println("\"pictureURL\":\"" + pictureURL + "\",");
                    out.println("\"email\": \"" + m2.get("email") +"\"}");
                }
            }else {
                out.println("{\"exists\": \"good exists.\",");
                out.println("\"name\": \"" + m2.get("name") +"\",");
                out.println("\"pictureURL\":\"" + pictureURL + "\",");
                out.println("\"email\": \"" + m2.get("email") +"\"}");
            }
        }
        else {
            out.println("{\"error\": \"bad insert.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Get crossword servlet");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();




    }
}

