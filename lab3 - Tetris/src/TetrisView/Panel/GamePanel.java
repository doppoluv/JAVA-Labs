package TetrisView.Panel;

import TetrisGame.TetrisGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int BLOCK_SIZE = 30;
    private final TetrisGame facade;

    public GamePanel(TetrisGame facade) {
        this.facade = facade;
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        setBackground(new Color(181, 181, 180));

        // Отрисовка поля
        int[][] board = facade.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    g2d.setColor(PieceColors.getColorWithId(board[i][j]));
                    g2d.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // Отрисовка тени
        if (!facade.isGameOver()) {
            int shadowY = facade.getShadowPosition();
            int currentX = facade.getCurrentX();
            int[][] currentPiece = facade.getCurrentPiece();

            Color pieceColor = Color.DARK_GRAY;
            Color shadowColor = new Color(pieceColor.getRed(), pieceColor.getGreen(), pieceColor.getBlue(), 100);

            for (int i = 0; i < currentPiece.length; i++) {
                for (int j = 0; j < currentPiece[i].length; j++) {
                    if (currentPiece[i][j] != 0 && shadowY + i >= 0) {
                        int x = (currentX + j) * BLOCK_SIZE;
                        int y = (shadowY + i) * BLOCK_SIZE;

                        g2d.setColor(shadowColor);
                        g2d.fillRect(x, y, BLOCK_SIZE + 1, BLOCK_SIZE + 1);
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10 * BLOCK_SIZE + 6, 21 * BLOCK_SIZE + 10);
    }
}