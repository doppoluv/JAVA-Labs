package TetrisView;

import TetrisView.Panel.*;
import TetrisGame.TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class TetrisView extends JFrame {
    private static final int BLOCK_SIZE = 30;
    private final TetrisGame game;
    private final GamePanel gamePanel;
    private final NextPiecePanel nextPiecePanel;
    private final ScorePanel scorePanel;

    public TetrisView(TetrisGame game) {
        this.game = game;
        setTitle("Tetris");
        setSize(16 * BLOCK_SIZE + 4, 21 * BLOCK_SIZE + 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        gamePanel = new GamePanel(game);
        add(gamePanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(160, 182, 149));
        rightPanel.setPreferredSize(new Dimension(5 * BLOCK_SIZE + 20, 20 * BLOCK_SIZE));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nextPiecePanel = new NextPiecePanel(game);
        rightPanel.add(nextPiecePanel);

        rightPanel.add(Box.createVerticalStrut(20));

        scorePanel = new ScorePanel();
        rightPanel.add(scorePanel);

        rightPanel.add(Box.createVerticalGlue());
        add(rightPanel, BorderLayout.EAST);

        setFocusable(true);
        requestFocusInWindow();
    }

    public void updateView() {
        scorePanel.updateScore(game.getScore());
        gamePanel.repaint();
        nextPiecePanel.repaint();
    }

    @Override
    public void addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        setFocusable(true);
        requestFocusInWindow();
    }
}