package com.up.client;

import com.up.client.frame.MainFrame;

import javax.swing.*;

/**
 * Created by Samsung on 15.04.2017.
 */
public class Test {
    public static void main(String[]args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
