package ChatUI;

import ChatClient.ChatConnection;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class ChatUI {
    private JFrame frame;
    private JTextPane chatArea;
    private JTextField messageField;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextField nicknameField;
    private JTextField hostField;
    private JTextField portField;
    private ChatConnection connection;

    public ChatUI() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);

        Color bgColor = new Color(80, 80, 80);
        UIManager.put("Panel.background", bgColor);
        UIManager.put("TextPane.background", new Color(90, 90, 90));
        UIManager.put("TextPane.foreground", Color.WHITE);
        UIManager.put("TextField.background", new Color(100, 100, 100));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(110, 110, 110));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("List.background", new Color(90, 90, 90));
        UIManager.put("List.foreground", Color.WHITE);

        JPanel connectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        connectPanel.setBackground(bgColor);
        connectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameLabel.setForeground(Color.WHITE);
        nicknameField = new JTextField(16); // Ограничение ника на 16 символов
        nicknameField.setDocument(new JTextFieldLimit(16));
        JLabel hostLabel = new JLabel("Host:");
        hostLabel.setForeground(Color.WHITE);
        hostField = new JTextField("", 15);
        JLabel portLabel = new JLabel("Port:");
        portLabel.setForeground(Color.WHITE);
        portField = new JTextField("", 5);
        connectButton = new JButton("\uD83D\uDD17 Connect");
        disconnectButton = new JButton("\uD83D\uDD15 Disconnect");
        disconnectButton.setEnabled(false);

        connectPanel.add(nicknameLabel);
        connectPanel.add(nicknameField);
        connectPanel.add(hostLabel);
        connectPanel.add(hostField);
        connectPanel.add(portLabel);
        connectPanel.add(portField);
        connectPanel.add(connectButton);
        connectPanel.add(disconnectButton);

        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        chatArea.setBackground(new Color(90, 90, 90));
        chatArea.setForeground(Color.WHITE);
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userList.setBackground(new Color(90, 90, 90));
        userList.setForeground(Color.WHITE);
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        userScroll.setPreferredSize(new Dimension(150, 0));
        userList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null && value.equals(connection.getNickname())) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    c.setForeground(Color.GREEN);
                }
                return c;
            }
        });
        userList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selectedUser = userList.getSelectedValue();
                    if (selectedUser != null && !selectedUser.equals(connection.getNickname())) {
                        messageField.setText("@" + selectedUser + " ");
                        messageField.requestFocus();
                    }
                }
            }
        });

        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBackground(bgColor);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        messageField = new JTextField();
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageField.setBackground(new Color(100, 100, 100));
        messageField.setForeground(Color.WHITE);
        JButton sendButton = new JButton("\uD83D\uDCE8 Send");
        sendButton.setBackground(new Color(0, 120, 215));

        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        frame.add(connectPanel, BorderLayout.NORTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatScroll, userScroll);
        splitPane.setDividerLocation(750);
        splitPane.setBackground(bgColor);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(messagePanel, BorderLayout.SOUTH);

        connection = new ChatConnection(this);

        connectButton.addActionListener(e -> {
            String nickname = nicknameField.getText().trim();
            String host = hostField.getText().trim();
            String portText = portField.getText().trim();

            connection.connectToServer(host, portText, nickname);
            connectButton.setBackground(new Color(0, 255, 0));
            javax.swing.Timer timer = new javax.swing.Timer(100, evt -> connectButton.setBackground(new Color(110, 110, 110)));
            timer.setRepeats(false);
            timer.start();
        });

        disconnectButton.addActionListener(e -> {
            connection.disconnect();
        });

        sendButton.addActionListener(e -> connection.sendMessage(messageField.getText().trim()));
        messageField.addActionListener(e -> connection.sendMessage(messageField.getText().trim()));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connection.disconnect();
            }
        });

        frame.setVisible(true);
    }

    // Класс для ограничения длины текста
    private static class JTextFieldLimit extends PlainDocument {
        private final int limit;

        JTextFieldLimit(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    public void appendToChat(String message, Color color) {
        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = chatArea.getStyledDocument();
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, color);

            try {
                if (doc.getLength() > 0) {
                    doc.insertString(doc.getLength(), "\n", attr);
                }

                FontMetrics fm = chatArea.getFontMetrics(chatArea.getFont());
                int maxWidth = chatArea.getWidth() - 20;
                chatArea.revalidate();
                chatArea.repaint();

                String[] words = message.split(" ");
                StringBuilder line = new StringBuilder();
                int lineWidth = 0;

                for (String word : words) {
                    while (!word.isEmpty()) {
                        String subWord = word;
                        int subWordWidth = fm.stringWidth(subWord + " ");
                        int charsToFit = word.length();

                        if (subWordWidth > maxWidth) {
                            charsToFit = 0;
                            int currentWidth = 0;
                            for (int i = 0; i < word.length(); i++) {
                                currentWidth += fm.charWidth(word.charAt(i));
                                if (currentWidth > maxWidth) {
                                    break;
                                }
                                charsToFit++;
                            }
                            if (charsToFit == 0) {
                                charsToFit = 1;
                            }
                            subWord = word.substring(0, charsToFit);
                            word = word.substring(charsToFit);
                        } else {
                            word = "";
                        }

                        subWordWidth = fm.stringWidth(subWord + " ");
                        if (lineWidth + subWordWidth > maxWidth && !line.toString().isEmpty()) {
                            doc.insertString(doc.getLength(), line.toString().trim() + "\n", attr);
                            line = new StringBuilder(subWord + " ");
                            lineWidth = subWordWidth;
                        } else {
                            line.append(subWord).append(" ");
                            lineWidth += subWordWidth;
                        }
                    }
                }
                if (!line.toString().isEmpty()) {
                    doc.insertString(doc.getLength(), line.toString().trim(), attr);
                }
                chatArea.setCaretPosition(doc.getLength());
                chatArea.revalidate();
                chatArea.repaint();
            } catch (BadLocationException e) {
                chatArea.setText(message);
            }
        });
    }

    public void updateUserList(String[] users) {
        SwingUtilities.invokeLater(() -> {
            userListModel.clear();
            for (String user : users) {
                if (!user.isEmpty()) {
                    userListModel.addElement(user);
                }
            }
        });
    }

    public void clearChat() {
        SwingUtilities.invokeLater(() -> chatArea.setText(""));
    }

    public void clearUserList() {
        SwingUtilities.invokeLater(() -> userListModel.clear());
    }

    public void setConnectionEnabled(boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            connectButton.setEnabled(enabled);
            disconnectButton.setEnabled(!enabled);
            nicknameField.setEditable(enabled);
            hostField.setEditable(enabled);
            portField.setEditable(enabled);
            if (enabled) {
                clearUserList();
            }
            if (!enabled) {
                disconnectButton.setBackground(new Color(110, 110, 110));
            }
        });
    }

    public JTextField getMessageField() {
        return messageField;
    }
}