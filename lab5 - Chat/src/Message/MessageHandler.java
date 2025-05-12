package Message;

import ChatClient.ChatConnection;
import ChatUI.ChatUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MessageHandler {
    private final ChatUI ui;
    private final ChatConnection connection;
    private final Set<String> displayedMessages = Collections.synchronizedSet(new HashSet<>());

    public MessageHandler(ChatUI ui, ChatConnection connection) {
        this.ui = ui;
        this.connection = connection;
    }

    public void handleMessage(Message message) {
        SwingUtilities.invokeLater(() -> {
            if (message.getType().equals("ERROR")) {
                ui.appendToChat("Error: " + message.getContent() + "\n", Color.RED);
            } else if (message.getType().equals("USER_LIST")) {
                ui.updateUserList(message.getContent().split(","));
            } else if (message.getType().equals("PRIVATE")) {
                String messageKey = message.getTimestamp().toString() + message.getSender() + message.getContent();
                if (!displayedMessages.contains(messageKey)) {
                    ui.appendToChat(message + "\n", Color.ORANGE);
                    displayedMessages.add(messageKey);
                }
            } else {
                String messageKey = message.getTimestamp().toString() + message.getSender() + message.getContent();
                if (!displayedMessages.contains(messageKey)) {
                    Color color = message.getSender().equals("Server") ? Color.GRAY : Color.WHITE;
                    ui.appendToChat(message + "\n", color);
                    displayedMessages.add(messageKey);
                }
            }
        });
    }

    public void clearDisplayedMessages() {
        displayedMessages.clear();
    }
}