package TetrisView.Panel;

import TetrisGame.TetrisGame;

import javax.swing.*;
import java.awt.*;

public class NextPiecePanel extends JPanel {
    private static final int BLOCK_SIZE = 30;
    private final TetrisGame facade;

    public NextPiecePanel(TetrisGame facade) {
        this.facade = facade;
        setPreferredSize(new Dimension(5 * BLOCK_SIZE, -10 * BLOCK_SIZE));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 3), "Next Piece"));
        setBorder(BorderFactory.createCompoundBorder(
                getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(160, 182, 149));
        int[][] nextPiece = facade.getNextPiece();
        int nextPieceId = facade.getNextPieceId();
        int startX = (getWidth() - nextPiece[0].length * BLOCK_SIZE) / 2;
        int startY = (getHeight() - nextPiece.length * BLOCK_SIZE) / 2;
        for (int i = 0; i < nextPiece.length; i++) {
            for (int j = 0; j < nextPiece[i].length; j++) {
                if (nextPiece[i][j] != 0) {
                    g.setColor(PieceColors.getColorWithId(nextPieceId));
                    g.fillRect(startX + j * BLOCK_SIZE, startY + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(startX + j * BLOCK_SIZE, startY + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
}