package TetrisController;

import TetrisGame.TetrisGame;
import TetrisGame.GameStateManager;
import TetrisView.TetrisView;

import javax.swing.*;
import java.awt.event.*;

public class TetrisController implements KeyListener {
    private final TetrisGame game;
    private final GameStateManager stateManager;
    private final TetrisView view;

    private final Timer gameTimer;
    private final Timer renderTimer;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private long leftHoldStart = 0;
    private long rightHoldStart = 0;
    private long downHoldStart = 0;
    private Timer moveLeftTimer;
    private Timer moveRightTimer;
    private Timer moveDownTimer;

    public TetrisController(TetrisGame game, GameStateManager stateManager, TetrisView view) {
        this.game = game;
        this.stateManager = stateManager;
        this.view = view;

        view.addKeyListener(this);

        gameTimer = new Timer(300, e -> {
            if (!downPressed && !game.isGameOver()) {
                game.moveDown();
                view.updateView();
            }

            if (game.isGameOver()) {
                game.saveScore();
                stopTimers();
                SwingUtilities.invokeLater(this::showGameOverDialog);
            }
        });

        renderTimer = new Timer(30, e -> {
            if (!game.isGameOver()) {
                view.updateView();
            }
        });

        gameTimer.start();
        renderTimer.start();

        moveLeftTimer = new Timer(70, e -> {
            if (leftPressed && !game.isGameOver()) {
                game.moveLeft();
                if (System.currentTimeMillis() - leftHoldStart > 500) {
                    moveLeftTimer.setDelay(50);
                }
                view.updateView();
            }
        });
        moveLeftTimer.setInitialDelay(140);

        moveRightTimer = new Timer(70, e -> {
            if (rightPressed && !game.isGameOver()) {
                game.moveRight();
                if (System.currentTimeMillis() - rightHoldStart > 500) {
                    moveRightTimer.setDelay(50);
                }
                view.updateView();
            }
        });
        moveRightTimer.setInitialDelay(140);

        moveDownTimer = new Timer(70, e -> {
            if (downPressed && !game.isGameOver()) {
                game.moveDown();
                if (System.currentTimeMillis() - downHoldStart > 500) {
                    moveDownTimer.setDelay(50);
                }
                view.updateView();
            }
        });
        moveDownTimer.setInitialDelay(140);

    }

    private void stopTimers() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (renderTimer != null) {
            renderTimer.stop();
        }
        if (moveLeftTimer != null) {
            moveLeftTimer.stop();
        }
        if (moveRightTimer != null) {
            moveRightTimer.stop();
        }
        if (moveLeftTimer != null) {
            moveLeftTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.isGameOver()) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (!leftPressed) {
                    leftPressed = true;
                    leftHoldStart = System.currentTimeMillis();
                    game.moveLeft();
                    moveLeftTimer.setDelay(50);
                    moveLeftTimer.start();
                    view.updateView();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!rightPressed) {
                    rightPressed = true;
                    rightHoldStart = System.currentTimeMillis();
                    game.moveRight();
                    moveRightTimer.setDelay(50);
                    moveRightTimer.start();
                    view.updateView();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!downPressed) {
                    downPressed = true;
                    downHoldStart = System.currentTimeMillis();
                    game.moveDown();
                    moveDownTimer.setDelay(30);
                    moveDownTimer.start();
                    view.updateView();
                }
                break;
            case KeyEvent.VK_UP:
                game.rotate();
                view.updateView();
                break;
            case KeyEvent.VK_SPACE:
                game.dropPiece();
                view.updateView();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                moveLeftTimer.stop();
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                moveRightTimer.stop();
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                moveDownTimer.stop();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void showGameOverDialog() {
        stopTimers();
        Object[] options = {"New Game", "Exit to Main Menu"};
        int choice = JOptionPane.showOptionDialog(null, "Game Over!\nScore: " + game.getScore(),
                "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                options, options[0]);

        stateManager.closeCurrentView();

        if (choice == JOptionPane.YES_OPTION) {
            stateManager.startNewGame();
        } else if (choice == JOptionPane.NO_OPTION) {
            stateManager.showMainMenu();
        }
    }
}