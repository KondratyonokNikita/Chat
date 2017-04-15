package com.up.client.panel;

import com.up.client.model.Session;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Samsung on 15.04.2017.
 */
public class ChatPanel extends UpdatablePanel implements ActionListener {
    private JTextPane taChat;
    private StyledDocument messages;
    private Style messageStyle, senderStyle, separatorStyle;
    private JTextField tfMessage;

    public ChatPanel(Session session) {
        setLayout(new BorderLayout());
        this.session = session;
        taChat = new JTextPane();
        taChat.setEditable(false);
        JScrollPane sbChat = new JScrollPane(taChat);
        add(sbChat, BorderLayout.CENTER);

        messages = taChat.getStyledDocument();
        initStyles();

        tfMessage = new JTextField();
        tfMessage.setEnabled(false);
        tfMessage.addActionListener(this);
        tfMessage.setActionCommand("message");
        add(tfMessage, BorderLayout.SOUTH);

        timer = new Timer(2000, this);
        timer.setActionCommand("timer");
    }

    private void initStyles() {
        messageStyle = taChat.addStyle("message", null);
        StyleConstants.setForeground(messageStyle, Color.red);
        StyleConstants.setFontSize(messageStyle, 20);

        senderStyle = taChat.addStyle("sender", null);
        StyleConstants.setForeground(senderStyle, Color.blue);
        StyleConstants.setFontSize(senderStyle, 14);

        separatorStyle = taChat.addStyle("sender", null);
        StyleConstants.setForeground(separatorStyle, Color.green);
        StyleConstants.setFontSize(separatorStyle, 10);
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
        ArrayList<HashMap<String, String>> messagesArray = session.getNewMessages();
        for(HashMap<String, String> message: messagesArray) {
            try {
                messages.insertString(messages.getLength(),
                        "=======================================================================\n",
                        separatorStyle);
                messages.insertString(messages.getLength(),
                        "From: " + message.get("\"user\"").replace("\"", "") + "\n",
                        senderStyle);
                messages.insertString(messages.getLength(),
                        "=======================================================================\n",
                        separatorStyle);
                messages.insertString(messages.getLength(),
                        message.get("\"text\"").replace("\"", "") + "\n",
                        messageStyle);
                messages.insertString(messages.getLength(),
                        "=======================================================================\n",
                        separatorStyle);
            }
            catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
