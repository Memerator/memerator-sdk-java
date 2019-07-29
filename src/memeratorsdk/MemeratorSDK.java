/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memeratorsdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Kyle Henry
 */
public class MemeratorSDK {
    
    private static HttpURLConnection con;
    private static String authorization;
    
    /**
     * @param username Memerator Username
     * @param password Memerator Password
     * Attempts to login to Memerator
     * @return returns true if login is sucessful, false otherwise
     * @throws IOException
     */
    
    /**
     * Sets Authorization for User
     * @param auth 
     */
    public static void setAuth(String auth)
    {
        authorization = auth;
    }
    
    public static boolean login(String username, String password) throws IOException
    {
        System.setProperty("http.agent", "Chrome");
        URL myurl = new URL("https://memerator.me/api/v1/login");
        con = (HttpURLConnection) myurl.openConnection();

        con.setRequestMethod("POST");
//                System.out.println(con.getHeaderFields());

        con.setDoOutput(true);

        try (OutputStream dos = con.getOutputStream()) {
            dos.write(("username=" + username + "&password=" + password).getBytes());
            dos.flush();
        }
        
        
        JSONObject jsonObject;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()))) {
            jsonObject = new JSONObject(in.readLine());
        }
        
        if(con.getResponseCode() == 200)
            authorization = jsonObject.getString("key");
        else
            return false;
        return true;
    }
    
    /**
     * Retrieves random Meme from Memerator
     * @return JSONObject containing Meme Data
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException 
     */
    public static JSONObject getRandomMeme() throws MalformedURLException,
        ProtocolException, IOException {
        System.setProperty("http.agent", "Chrome");
        
        URL myurl = new URL("https://memerator.me/api/v1/meme/random");
        con = (HttpURLConnection) myurl.openConnection();

        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json");
        con.addRequestProperty(authorization, authorization);
        con.setRequestProperty("Authorization", authorization);

        JSONObject jsonObject;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()))) {
            jsonObject = new JSONObject(in.readLine());
        }
        
        return jsonObject;
        
    }
    
    /**
     * Retrieves profile information for User
     * @return JSONObject containing Meme Data
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException 
     */
    public static JSONObject getProfile() throws MalformedURLException,
        ProtocolException, IOException {
        System.setProperty("http.agent", "Chrome");
        
        URL myurl = new URL("https://memerator.me/api/v1/profile/me");
        con = (HttpURLConnection) myurl.openConnection();

        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json");
        con.addRequestProperty(authorization, authorization);
        con.setRequestProperty("Authorization", authorization);

        JSONObject jsonObject;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()))) {
            jsonObject = new JSONObject(in.readLine());
        }
        
        return jsonObject;
        
    }
    
    
    
}
