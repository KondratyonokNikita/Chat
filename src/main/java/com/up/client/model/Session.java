package com.up.client.model;

import com.up.helper.Helper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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
            URL url = new URL("http://localhost:8080/user/add?name=" + URLEncoder.encode(name, "UTF-8"));
            System.out.println(url.toString());
            URLConnection api = url.openConnection();
            api.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            user = name;
            last = LocalDateTime.now();
            opened = true;
            System.out.println("Login: " + user + LocalDateTime.now().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        System.out.println("Logout: " + user + LocalDateTime.now().toString());
        user = null;
    }

    public void update(JTextArea textArea) {
        System.out.println("Update: " + LocalDateTime.now().toString());
        try {
            URL url = new URL("http://localhost:8080/message/all/after?after=" +
                    URLEncoder.encode(String.valueOf(Helper.toMillis(last)), "UTF-8"));
            URLConnection api = url.openConnection();
            api.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            String inputLine = in.readLine();
            ArrayList<HashMap<String, String>> messages = JSONParseMessages(inputLine);
            for(HashMap<String, String> message: messages) {
                textArea.append("===========\n");
                textArea.append("From: " + message.get("\"user\"") + "\n");
                textArea.append("===========\n");
                textArea.append(message.get("\"text\"") + "\n");
                textArea.append("===========\n");
                last = Helper.toLocalDateTime(Long.parseLong(message.get("\"created\"")));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<HashMap<String, String>> JSONParseMessages(String input) {
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        if("[]".equals(input))
            return res;
        input = input.replace("[{", "");
        input = input.replace("}]", "");
        input = input.replace("[", "");
        input = input.replace("]", "");
        input = input.replace("}", "-");
        input = input.replace("{", "-");
        for(String messageStr: input.split("-,-")) {
            HashMap<String, String> messageMap = new HashMap<>();
            for(String pair: messageStr.split(",")) {
                String[] parts = pair.split(":");
                messageMap.put(parts[0], parts[1]);
            }
            res.add(messageMap);
        }
        return res;
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
