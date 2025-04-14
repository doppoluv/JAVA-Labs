package TetrisModel;

import java.util.Random;
import java.util.ArrayList;

public class TetrisModel {
    private final Board board;
    private final Random random;
    private final PieceFactory pieceFactory;
    private final int width = 10;
    private final int height = 20;
    private boolean isGameOver;

    private int currentX;
    private int currentY;
    private int[][] currentPiece;
    private int currentPieceId;
    private int[][] nextPiece;
    private int nextPieceId;

    private boolean isOnGround;
    private long lockDelayTimer;
    private static final long LOCK_DELAY = 500;

    public TetrisModel() {
        board = new Board(height, width);
        random = new Random();
        pieceFactory = new PieceFactory();
        reset();
    }

    public void reset() {
        currentY = 0;
        isGameOver = false;
        isOnGround = false;
        lockDelayTimer = 0;
        int pieceIndex = random.nextInt(pieceFactory.getPieceCount());
        currentPiece = pieceFactory.getPiece(pieceIndex);
        currentPieceId = pieceIndex + 1;
        centerPiece();
        pieceIndex = random.nextInt(pieceFactory.getPieceCount());
        nextPiece = pieceFactory.getPiece(pieceIndex);
        nextPieceId = pieceIndex + 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board.setCell(i, j, 0);
            }
        }
        board.setScore(0);
    }

    public int getShadowPosition() {
        int shadowY = currentY;
        while (canMove(0, shadowY - currentY + 1)) {
            shadowY++;
        }
        return shadowY;
    }

    public void moveDown() {
        if (isGameOver) {
            return;
        }

        if (canMove(0, 1)) {
            currentY++;
            isOnGround = false;
            lockDelayTimer = 0;
        } else {
            if (!isOnGround) {
                isOnGround = true;
                lockDelayTimer = System.currentTimeMillis();
            } else {
                if (System.currentTimeMillis() - lockDelayTimer >= LOCK_DELAY) {
                    placePiece();
                    board.removeFullLines();
                    resetPiece();
                    isOnGround = false;
                    lockDelayTimer = 0;
                }
            }
        }
    }

    public void moveLeft() {
        if (isGameOver) {
            return;
        }

        if (canMove(-1, 0)) {
            currentX--;
            if (isOnGround) {
                lockDelayTimer = System.currentTimeMillis();
            }
        }
    }

    public void moveRight() {
        if (isGameOver) {
            return;
        }

        if (canMove(1, 0)) {
            currentX++;
            if (isOnGround) {
                lockDelayTimer = System.currentTimeMillis();
            }
        }
    }

    public void rotate() {
        if (isGameOver) {
            return;
        }

        if (currentPieceId == 2) {
            return;
        }

        int[][] rotatedPiece = new int[currentPiece[0].length][currentPiece.length];
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                rotatedPiece[j][currentPiece.length - 1 - i] = currentPiece[i][j];
            }
        }

        if (canRotate(rotatedPiece, 0)) {
            currentPiece = rotatedPiece;
            if (isOnGround) {
                lockDelayTimer = System.currentTimeMillis();
            }
            return;
        }

        if (currentPieceId == 1) {
            int originalX = currentX;
            if (currentPiece.length > currentPiece[0].length && currentX + currentPiece[0].length > width - 4) {
                int neededSpace = 4 - (width - currentX);
                currentX = Math.max(0, currentX - neededSpace);
                if (canRotate(rotatedPiece, 0)) {
                    currentPiece = rotatedPiece;
                    if (isOnGround) {
                        lockDelayTimer = System.currentTimeMillis();
                    }
                    return;
                }
            }
            currentX = originalX;
        }

        int offset = 1;
        int maxOffset = 4;
        while (offset <= maxOffset) {
            if (canRotate(rotatedPiece, -offset)) {
                currentX -= offset;
                currentPiece = rotatedPiece;
                if (isOnGround) {
                    lockDelayTimer = System.currentTimeMillis();
                }
                break;
            }
            if (canRotate(rotatedPiece, offset)) {
                currentX += offset;
                currentPiece = rotatedPiece;
                if (isOnGround) {
                    lockDelayTimer = System.currentTimeMillis();
                }
                break;
            }
            offset++;
        }
    }

    public void dropPiece() {
        if (isGameOver) {
            return;
        }

        while (canMove(0, 1)) {
            currentY++;
        }

        placePiece();
        board.removeFullLines();
        resetPiece();
        isOnGround = false;
        lockDelayTimer = 0;
    }

    private boolean canMove(int dx, int dy) {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                if (currentPiece[i][j] == 0) continue;
                int newX = currentX + j + dx;
                int newY = currentY + i + dy;
                if (newX < 0 || newX >= width || newY >= height) {
                    return false;
                }
                if (newY >= 0 && board.getCell(newY, newX) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canRotate(int[][] rotatedPiece, int offsetX) {
        for (int i = 0; i < rotatedPiece.length; i++) {
            for (int j = 0; j < rotatedPiece[i].length; j++) {
                if (rotatedPiece[i][j] == 0) continue;
                int newX = currentX + j + offsetX;
                int newY = currentY + i;
                if (newX < 0 || newX >= width || newY >= height) {
                    return false;
                }
                if (newY >= 0 && board.getCell(newY, newX) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placePiece() {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                if (currentPiece[i][j] != 0) {
                    int y = currentY + i;
                    int x = currentX + j;
                    if (y >= 0 && y < height && x >= 0 && x < width) {
                        board.setCell(y, x, currentPieceId);
                    }
                }
            }
        }
    }

    private void centerPiece() {
        int pieceWidth = currentPiece[0].length;
        currentX = (width - pieceWidth) / 2;
    }

    private void resetPiece() {
        if (isGameOver) return;
        currentY = 0;
        currentPiece = nextPiece;
        currentPieceId = nextPieceId;
        centerPiece();
        int pieceIndex = random.nextInt(pieceFactory.getPieceCount());
        nextPiece = pieceFactory.getPiece(pieceIndex);
        nextPieceId = pieceIndex + 1;
        if (!canMove(0, 0)) {
            isGameOver = true;
        }
        isOnGround = false;
        lockDelayTimer = 0;
    }

    public int[][] getBoard() {
        int[][] displayBoard = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                displayBoard[i][j] = board.getCell(i, j);
            }
        }
        if (!isGameOver) {
            for (int i = 0; i < currentPiece.length; i++) {
                for (int j = 0; j < currentPiece[i].length; j++) {
                    if (currentPiece[i][j] != 0 && currentY + i >= 0) {
                        displayBoard[currentY + i][currentX + j] = currentPieceId;
                    }
                }
            }
        }
        return displayBoard;
    }

    public int[][] getNextPiece() {
        return nextPiece;
    }

    public int getNextPieceId() {
        return nextPieceId;
    }

    public int[][] getCurrentPiece() {
        return currentPiece;
    }

    public int getCurrentX() {
        return currentX;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getScore() {
        return board.getScore();
    }

    public void saveScore() {
        HighScoresManager.saveScore(getScore());
    }

    public ArrayList<Integer> getHighScores() {
        return HighScoresManager.getHighScores();
    }
}