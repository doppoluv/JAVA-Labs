package TetrisView.MainMenu;

import TetrisGame.TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HighScores extends JDialog {
    public HighScores(JFrame parent, TetrisGame facade) {
        super(parent, "High Scores", true);
        setSize(300, 420);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(160, 182, 149));

        JLabel title = new JLabel("Top 10 High Scores");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(10));

        ArrayList<Integer> scores = facade.getHighScores();

        for (int i = 0; i < 10; i++) {
            String scoreText = (i < scores.size()) ? (i + 1) + ". " + scores.get(i) : (i + 1) + ". ---";
            JLabel scoreLabel = new JLabel(scoreText);
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 27));
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(scoreLabel);
        }

        panel.add(Box.createVerticalGlue());

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(closeButton);

        add(panel);
        setVisible(true);
    }
}