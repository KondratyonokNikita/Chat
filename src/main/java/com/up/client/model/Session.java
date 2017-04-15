package com.up.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;

/**
 * Created by Samsung on 15.04.2017.
 */
public class Session {
    private boolean opened;
    private String user;
    private LocalDateTime last;

    public Session() {
        user = null;
        last = LocalDateTime.MIN;
        opened = false;
    }

    public void login(String name) {
        try {
            System.out.println("Login: " + name + LocalDateTime.now().toString());
            URL myURL = new URL("http://localhost:8080/user/add?name=" + name);
            URLConnection api = myURL.openConnection();
            // Call the API (ASYNCHRONOUS)
            api.connect();
            // Set up input stream reader
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            String inputLine;
            // Read from input steam
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        System.out.println("Logout: " + user + LocalDateTime.now().toString());
    }

    public void update() {
        System.out.println("Update: " + LocalDateTime.now().toString());
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getLast() {
        return last;
    }

    public void setLast(LocalDateTime last) {
        this.last = last;
    }
}
