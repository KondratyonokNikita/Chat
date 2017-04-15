package com.up.client.panel;

import com.up.client.model.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Samsung on 15.04.2017.
 */
public class ChatPanel extends UpdatablePanel implements ActionListener {
    private JTextArea taChat;
    private JTextField tfMessage;

    public ChatPanel(Session session) {
        setLayout(new BorderLayout());
        this.session = session;
        taChat = new JTextArea();
        taChat.setEditable(false);
        JScrollPane sbChat = new JScrollPane(taChat);
        add(sbChat, BorderLayout.CENTER);

        tfMessage = new JTextField();
        tfMessage.setEnabled(false);
        tfMessage.addActionListener(this);
        tfMessage.setActionCommand("message");
        JScrollPane sbMessage = new JScrollPane(tfMessage);
        add(sbMessage, BorderLayout.SOUTH);

        timer = new Timer(2000, this);
        timer.setActionCommand("timer");
    }

    public void start() {
        timer.start();
        tfMessage.setEnabled(true);
    }

    public void stop() {
        timer.stop();
        tfMessage.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "timer": update(); break;
            case "message": sendMessage(tfMessage.getText()); break;
        }
    }

    private void sendMessage(String text) {
        session.sendMessage(text);
        tfMessage.setText("");
    }

    public void update() {
        ArrayList<HashMap<String, String>> messages = session.getNewMessages();
        StringBuilder text = new StringBuilder(taChat.getText());
        for(HashMap<String, String> message: messages) {
            text.append("===========\n");
            text.append("From: " + message.get("\"user\"") + "\n");
            text.append("===========\n");
            text.append(message.get("\"text\"") + "\n");
            text.append("===========\n");
        }
        taChat.setText(text.toString());
    }
}
