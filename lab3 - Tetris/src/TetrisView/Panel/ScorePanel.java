package TetrisView.Panel;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final JLabel scoreLabel;

    public ScorePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(160, 182, 149));
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(scoreLabel);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}