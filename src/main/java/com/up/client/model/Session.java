package com.up.client.model;

import com.up.helper.Helper;

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
    private String user;
    private LocalDateTime last;

    public Session() {
        user = null;
        last = LocalDateTime.MIN;
    }

    public void login(String name) {
        try {
            URL url = new URL("http://localhost:8080/user/add?name=" + URLEncoder.encode(name, "UTF-8"));
            URLConnection api = url.openConnection();
            api.connect();
            api.getInputStream();
            user = name;
            last = LocalDateTime.now();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        try {
            URL url = new URL("http://localhost:8080/user/remove?name=" + URLEncoder.encode(user, "UTF-8"));
            URLConnection api = url.openConnection();
            api.connect();
            api.getInputStream();
            user = null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            URL url = new URL("http://localhost:8080/message/add?name=" +
                    URLEncoder.encode(this.user, "UTF-8") + "&text=" +
                    URLEncoder.encode(message, "UTF-8"));
            URLConnection api = url.openConnection();
            api.connect();
            api.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, String>> getNewMessages() {
        try {
            URL url = new URL("http://localhost:8080/message/all/after?after=" +
                    URLEncoder.encode(String.valueOf(Helper.toMillis(last)), "UTF-8"));
            URLConnection api = url.openConnection();
            api.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            String inputLine = in.readLine();
            return JSONParseMessages(inputLine);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<String> getUsers() {
        try {
            URL url = new URL("http://localhost:8080/user/all");
            URLConnection api = url.openConnection();
            api.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
            String inputLine = in.readLine();
            return JSONParseUsers(inputLine);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private String normalize(String input) {
        input = input.replace("[{", "");
        input = input.replace("}]", "");
        input = input.replace("[", "");
        input = input.replace("]", "");
        input = input.replace("}", "-");
        input = input.replace("{", "-");
        return input;
    }

    private ArrayList<String> JSONParseUsers(String input) {
        ArrayList<String> res = new ArrayList<>();
        if("[]".equals(input))
            return res;
        input = normalize(input);
        for(String userStr: input.split("-,-")) {
            res.add(userStr.split(",")[1].split(":")[1].replace("\"", ""));
        }
        return res;
    }

    private ArrayList<HashMap<String, String>> JSONParseMessages(String input) {
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        if("[]".equals(input))
            return res;
        input = normalize(input);
        for(String messageStr: input.split("-,-")) {
            HashMap<String, String> messageMap = new HashMap<>();
            for(String pair: messageStr.split(",")) {
                String[] parts = pair.split(":");
                messageMap.put(parts[0], parts[1]);
            }
            res.add(messageMap);
            last = Helper.toLocalDateTime(Long.parseLong(messageMap.get("\"created\"")));
        }
        return res;
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
