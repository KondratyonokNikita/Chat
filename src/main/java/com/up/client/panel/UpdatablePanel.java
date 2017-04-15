package com.up.client.panel;

import com.up.client.model.Session;

import javax.swing.*;

/**
 * Created by Samsung on 15.04.2017.
 */
public abstract class UpdatablePanel extends JPanel {
    protected Timer timer;
    protected Session session;

    public abstract void update();

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
