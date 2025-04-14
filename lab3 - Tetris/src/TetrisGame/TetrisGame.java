package TetrisGame;

import TetrisModel.TetrisModel;
import java.util.ArrayList;

public class TetrisGame {
    private final TetrisModel model;

    public TetrisGame() {
        this.model = new TetrisModel();
    }

    public int[][] getBoard() {
        return model.getBoard();
    }

    public int[][] getNextPiece() {
        return model.getNextPiece();
    }

    public int getNextPieceId() {
        return model.getNextPieceId();
    }

    public int[][] getCurrentPiece() {
        return model.getCurrentPiece();
    }

    public int getCurrentX() {
        return model.getCurrentX();
    }

    public int getShadowPosition() {
        return model.getShadowPosition();
    }

    public int getScore() {
        return model.getScore();
    }

    public boolean isGameOver() {
        return model.isGameOver();
    }

    public ArrayList<Integer> getHighScores() {
        return model.getHighScores();
    }

    public void moveDown() {
        model.moveDown();
    }

    public void moveLeft() {
        model.moveLeft();
    }

    public void moveRight() {
        model.moveRight();
    }

    public void rotate() {
        model.rotate();
    }

    public void dropPiece() {
        model.dropPiece();
    }

    public void saveScore() {
        try {
            model.saveScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startNewGame() {
        model.reset();
    }
}