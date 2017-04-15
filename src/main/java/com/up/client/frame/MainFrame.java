package com.up.client.frame;

import com.up.client.model.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Samsung on 15.04.2017.
 */
public class MainFrame extends JFrame implements ActionListener {
    private Session session;
    private JMenuItem menuLogin, menuLogout, menuUpdate, menuExit;

    public MainFrame() {
        super("Chat");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        session = new Session();
        initMenu();
        setSize(100, 100);
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
//        menuLogout.setEnabled(false);

        menuUpdate = new JMenuItem("Update");
        menuUpdate.setActionCommand("update");
        menuUpdate.addActionListener(this);
//        menuUpdate.setEnabled(false);

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
            System.exit(0);
        }
    }

    private void update() {
        session.update();
    }

    private void login() {
        String name = JOptionPane.showInputDialog("Your nickname:");
        if(name != null) {
            session.login(name);
            menuLogin.setEnabled(false);
            menuLogout.setEnabled(true);
            menuUpdate.setEnabled(true);
        }
    }

    private void logout() {
        int resp = JOptionPane.showConfirmDialog(this, "Do you really want to logout?");
        if(resp == JOptionPane.OK_OPTION) {
            session.logout();
            menuLogin.setEnabled(true);
            menuLogout.setEnabled(false);
            menuUpdate.setEnabled(false);
        }
    }
}
