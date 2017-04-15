package com.up.client.frame;

import com.up.client.model.Session;
import com.up.client.panel.ChatPanel;
import com.up.client.panel.UpdatablePanel;
import com.up.client.panel.UsersPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Samsung on 15.04.2017.
 */
public class MainFrame extends JFrame implements ActionListener {
    private Session session;
    private JMenuItem menuLogin, menuLogout, menuUpdate, menuExit;
    private UpdatablePanel chatPanel, usersPanel;

    public MainFrame() {
        super("Chat");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        session = new Session();
        initMenu();
        initChat();
        initUsers();
        setSize(300, 400);
        setVisible(true);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Main");

        menuLogin = new JMenuItem("Login");
        menuLogin.setActionCommand("login");
        menuLogin.addActionListener(this);

        menuLogout = new JMenuItem("Logout");
        menuLogout.setActionCommand("logout");
        menuLogout.addActionListener(this);
        menuLogout.setEnabled(false);

        menuUpdate = new JMenuItem("Update");
        menuUpdate.setActionCommand("update");
        menuUpdate.addActionListener(this);
        menuUpdate.setEnabled(false);

        menuExit = new JMenuItem("Exit");
        menuExit.setActionCommand("exit");
        menuExit.addActionListener(this);

        menu.add(menuLogin);
        menu.add(menuLogout);
        menu.add(menuUpdate);
        menu.add(new JSeparator());
        menu.add(menuExit);

        menuBar.add(menu);

        setJMenuBar(menuBar);
    }

    private void initChat() {
        chatPanel = new ChatPanel(session);
        getContentPane().add(chatPanel, BorderLayout.CENTER);
    }

    private void initUsers() {
        usersPanel = new UsersPanel(session);
        getContentPane().add(usersPanel, BorderLayout.EAST);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login": login(); break;
            case "logout": logout(); break;
            case "update": update(); break;
            case "exit": exit(); break;
        }
    }

    private void exit() {
        int resp = JOptionPane.showConfirmDialog(this, "Do you really want to exit?");
        if(resp == JOptionPane.OK_OPTION) {
            session.logout();
            System.exit(0);
        }
    }

    private void update() {
        chatPanel.update();
        usersPanel.update();
    }

    private void login() {
        String name = JOptionPane.showInputDialog("Your nickname:");
        if(name != null) {
            session.login(name);
            menuLogin.setEnabled(false);
            menuLogout.setEnabled(true);
            menuUpdate.setEnabled(true);
            chatPanel.start();
            usersPanel.start();
            this.update();
        }
    }

    private void logout() {
        int resp = JOptionPane.showConfirmDialog(this, "Do you really want to logout?");
        if(resp == JOptionPane.OK_OPTION) {
            chatPanel.stop();
            usersPanel.stop();
            session.logout();
            menuLogin.setEnabled(true);
            menuLogout.setEnabled(false);
            menuUpdate.setEnabled(false);
            this.update();
        }
    }
}
