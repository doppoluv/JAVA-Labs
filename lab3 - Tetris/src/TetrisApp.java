import javax.swing.*;
import TetrisGame.TetrisGame;
import TetrisGame.GameStateManager;

public class TetrisApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TetrisGame game = new TetrisGame();
            GameStateManager stateManager = new GameStateManager(game);
            stateManager.showMainMenu();
        });
    }
}