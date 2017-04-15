package com.up.client.panel;

import com.up.client.model.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Samsung on 15.04.2017.
 */
public class UsersPanel extends UpdatablePanel implements ActionListener {
    private JTextArea taUsers;
    private JLabel user;

    public UsersPanel(Session session) {
        setLayout(new BorderLayout());
        this.session = session;

        taUsers = new JTextArea("====================");
        taUsers.setEditable(false);
        JScrollPane spUsers = new JScrollPane(taUsers);
        add(spUsers, BorderLayout.CENTER);

        user = new JLabel("You are: ");
        add(user, BorderLayout.NORTH);
        timer = new Timer(5000, this);
        timer.setActionCommand("timer");
    }

    public void update() {
        StringBuilder stringBuilder = new StringBuilder("====================\n");
        ArrayList<String> users = session.getUsers();
        for(String user: users) {
            stringBuilder.append(user + "\n");
        }
        taUsers.setText(stringBuilder.toString());
        user.setText("You are: " + session.getUser());
    }

    public void actionPerformed(ActionEvent e) {
        update();
    }
}
