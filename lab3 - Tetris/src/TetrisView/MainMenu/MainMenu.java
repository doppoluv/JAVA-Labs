package TetrisView.MainMenu;

import TetrisGame.TetrisGame;
import TetrisGame.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu(TetrisGame game, GameStateManager stateManager) {
        setTitle("Tetris - Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(160, 182, 149));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton newGameButton = new JButton("New Game");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(newGameButton, gbc);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stateManager.startNewGame();
            }
        });

        JButton highScoresButton = new JButton("High Scores");
        gbc.gridy = 1;
        panel.add(highScoresButton, gbc);
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScores(MainMenu.this, game);
            }
        });

        JButton exitButton = new JButton("Exit");
        gbc.gridy = 2;
        panel.add(exitButton, gbc);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(panel);
    }
}