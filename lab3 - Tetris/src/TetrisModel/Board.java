package TetrisModel;

public class Board {
    private final int[][] grid;
    private final int height;
    private final int width;
    private int score;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new int[height][width];
        this.score = 0;
    }

    public void setCell(int row, int col, int value) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            grid[row][col] = value;
        }
    }

    public int getCell(int row, int col) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            return grid[row][col];
        }
        return 0;
    }

    public void removeFullLines() {
        int linesCleared = 0;
        for (int i = height - 1; i >= 0; i--) {
            boolean isFull = true;
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                linesCleared++;
                for (int k = i; k > 0; k--) {
                    if (width >= 0) {
                        System.arraycopy(grid[k - 1], 0, grid[k], 0, width);
                    }
                }
                for (int j = 0; j < width; j++) {
                    grid[0][j] = 0;
                }
                i++;
            }
        }
        if (linesCleared > 0) {
            score += calculateScore(linesCleared);
        }
    }

    private int calculateScore(int linesCleared) {
        return switch (linesCleared) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 600;
            case 4 -> 900;
            default -> 0;
        };
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}