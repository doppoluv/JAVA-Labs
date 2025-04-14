package TetrisGame;

import TetrisController.TetrisController;
import TetrisView.TetrisView;
import TetrisView.MainMenu.MainMenu;

import javax.swing.*;

public class GameStateManager {
    private final TetrisGame game;
    private JFrame currentView;

    public GameStateManager(TetrisGame game) {
        this.game = game;
    }

    public void startNewGame() {
        game.startNewGame();
        TetrisView view = new TetrisView(game);
        new TetrisController(game, this, view);
        setCurrentView(view);
        view.setVisible(true);
        view.requestFocusInWindow();
    }

    public void showMainMenu() {
        MainMenu menu = new MainMenu(game, this);
        setCurrentView(menu);
        menu.setVisible(true);
    }

    public void closeCurrentView() {
        if (currentView != null) {
            currentView.dispose();
            currentView = null;
        }
    }

    private void setCurrentView(JFrame view) {
        closeCurrentView();
        this.currentView = view;
    }
}