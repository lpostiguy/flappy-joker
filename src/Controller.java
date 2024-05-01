/*
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameController {
    private WindowView view;
    private Enemy enemy;
    private boolean gameIsRunning;
    private ArrayList<Coin> coins;
    private ArrayList<Character> heroes;
    private Stage primaryStage;

    public GameController(WindowView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.enemy = new Enemy();
        this.coins = new ArrayList<>();
        this.heroes = new ArrayList<>();
        this.gameIsRunning = false;
    }

    public void initialize() {
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.getStartButton().setOnAction(e -> startGame());
        view.getPauseButton().setOnAction(e -> togglePause());
        view.getGameScene().setOnKeyPressed(this::handleKeyPress);
        setupAnimationTimer();
    }

    private void startGame() {
        gameIsRunning = true;
        primaryStage.setScene(view.getGameScene());
        updateMusic();
    }

    private void togglePause() {
        gameIsRunning = !gameIsRunning;
        enemy.setCanShoot(gameIsRunning);
        view.getPauseButton().setText(gameIsRunning ? "Pause" : "Resume");
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            enemy.setJumping(true);
        } else if (event.getCode() == KeyCode.E && enemy.getCanShoot()) {
            enemy.attack();
            // Additional key handling here...
        }
    }

    private void setupAnimationTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameIsRunning) {
                    updateGame(now);
                }
            }
        };
        timer.start();
    }

    private void updateGame(long now) {
        // Game update logic...
    }

    // Additional methods such as showGameOverScreen, resetGame, updateMusic...
}
*/