package TetrisModel;

public class PieceFactory {
    private final int[][][] pieces = {
            { {1, 1, 1, 1} },         // I
            { {1, 1}, {1, 1} },       // O
            { {0, 1, 0}, {1, 1, 1} }, // T
            { {0, 1, 1}, {1, 1, 0} }, // S
            { {1, 1, 0}, {0, 1, 1} }, // Z
            { {1, 0, 0}, {1, 1, 1} }, // J
            { {0, 0, 1}, {1, 1, 1} }  // L
    };

    public int getPieceCount() {
        return pieces.length;
    }

    public int[][] getPiece(int index) {
        return pieces[index];
    }
}