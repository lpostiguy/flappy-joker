import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;

public class WindowView extends Application {
    private Enemy enemy = new Enemy();
    private boolean gameIsRunning = false;
    private long lastTime = 0; // Variable for the animation

    // Arraylist containing all the coins
    private ArrayList<Coin> coins = new ArrayList<>();
    private long lastCoinTime = 0;

    // Arraylist containing all the heroes
    private ArrayList<Character> heroes = new ArrayList<>();
    private long lastHeroTime = 0;

    // Counter for a new teleportation
    double tankLastTeleportTime = 0;
    private BorderPane rootGame;
    private BorderPane rootMenu;
    private Stage primaryStage;
    private Stage gameOverStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        rootGame = new BorderPane();
        rootMenu = new BorderPane();

        // Initialize the Game scene
        Scene gameScene = new Scene(rootGame, 640, 440);
        // Initialize the Menu scene
        Scene menuScene = new Scene(rootMenu, 640, 440);

        // Set by default the menu Scene
        primaryStage.setScene(menuScene);
        primaryStage.show();
        updateMusic();


        // Load the background image for the Menu scene
        Image menuBackgroundImage = new Image("/assets/images/JokerBg.png");
        ImageView menuImageView = new ImageView(menuBackgroundImage);

        rootMenu.getChildren().add(menuImageView);

        VBox mainMenuBox = new VBox();
        mainMenuBox.setPadding(new Insets(0,0,100,50));
        mainMenuBox.setAlignment(Pos.CENTER_LEFT);

        Label unoReverseTitle = new Label("UNO Reverse Flappy");
        unoReverseTitle.setStyle("-fx-text-fill: #61355C; -fx-font-size: 30px;" +
                " -fx-font-weight: bold");
        unoReverseTitle.setPadding(new Insets(0, 0, 100, 0));


        Button startButton = new Button("Start Game");
        startButton.setPadding(new Insets(10, 20, 10, 20));
        startButton.setStyle("-fx-background-color: #4CAE25; -fx-text-fill: " +
                "black; -fx-background-radius: 10; -fx-font-size: 16px; " +
                "-fx-font-weight: bold");
        startButton.setMinWidth(120);
        startButton.setOnAction(e -> {
            gameIsRunning = true;
            primaryStage.setScene(gameScene);
            updateMusic();
        });

// Add the button and title to the mainMenuBox
        mainMenuBox.getChildren().addAll(unoReverseTitle, startButton);

// Add mainMenuBox to rootMenu
        rootMenu.setCenter(mainMenuBox);



        // Load the background image for the game scene
        Image backgroundImage = new Image("/assets/images/Bg.png");
        ImageView backgroundView1 = new ImageView(backgroundImage);
        ImageView backgroundView2 = new ImageView(backgroundImage);
        backgroundView2.setTranslateX(backgroundImage.getWidth());


        Pane backgrounds = new Pane();
        backgrounds.getChildren().addAll(backgroundView1, backgroundView2);
        rootGame.getChildren().add(backgrounds);

        HBox bottomGameInfo = new HBox();
        bottomGameInfo.setPadding(new Insets(10, 0, 10, 0));
        bottomGameInfo.setPrefWidth(gameScene.getWidth());
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);
        bottomGameInfo.setBackground(new Background(backgroundFill));

        Button pauseButton = new Button("Pause");
        pauseButton.setPadding(new Insets(5, 10, 5, 10));
        pauseButton.setMinWidth(75);
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(e -> {
            gameIsRunning = !gameIsRunning;
            enemy.setCanShoot(gameIsRunning);
            pauseButton.setText(gameIsRunning ? "Pause" : "Resume");
        });

        VBox pauseButtonBox = new VBox(pauseButton);
        pauseButtonBox.setPadding(new Insets(0, 10, 0, 10));

        Label lifeText = new Label("Life: " + enemy.getHealth());
        lifeText.setFont(Font.font("Arial", 16));
        lifeText.setPadding(new Insets(0, 10, 0, 10));

        Label coinText = new Label("Coins:" + enemy.getCoinCollected());
        coinText.setFont(Font.font("Arial", 16));
        coinText.setPadding(new Insets(0, 10, 0, 10));

        bottomGameInfo.getChildren().addAll(pauseButtonBox,
                new Separator(Orientation.VERTICAL), lifeText,
                new Separator(Orientation.VERTICAL), coinText);
        bottomGameInfo.setAlignment(Pos.CENTER);
        rootGame.setBottom(bottomGameInfo);


        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                enemy.setJumping(true);
            } else if (event.getCode() == KeyCode.E && enemy.getCanShoot()) {
                enemy.attack();
                enemy.setCanShoot(false);
                Line redLine =
                        new Line(enemy.getPositionX() + 2 * enemy.getRadius(),
                                enemy.getPositionY() + enemy.getRadius(), 640,
                                enemy.getPositionY() + enemy.getRadius());
                redLine.setStroke(Color.RED);
                redLine.setStrokeWidth(2);

                if (!rootGame.getChildren().contains(redLine)) {
                    rootGame.getChildren().add(redLine);
                }

                // Create a PauseTransition for a 0.05 second delay
                PauseTransition pauseShoot =
                        new PauseTransition(Duration.seconds(0.05));
                pauseShoot.setOnFinished(e -> {
                    // Remove the red line after the delay
                    rootGame.getChildren().remove(redLine);
                });

                PauseTransition pauseCanShoot =
                        new PauseTransition(Duration.seconds(1));
                pauseCanShoot.setOnFinished(e -> {
                    enemy.setCanShoot(true);
                });

                // TODO: Maybe their is a way to add this to the enemy class
                // Killing heroes
                Iterator<Character> characterIterator = heroes.iterator();
                while (characterIterator.hasNext()) {
                    Character character = characterIterator.next();
                    System.out.println(character);
                    if (enemy.getPositionY() <= character.getPositionY() + character.getRadius() &&
                            enemy.getPositionY() >= character.getPositionY() - character.getRadius()) {
                        // Remove the hero from the game
                        rootGame.getChildren().remove(character.getImageView());

                        // Give the loot to the enemy
                        enemy.setCoinCollected(character.getCoinDropAmount());
                        coinText.setText("Coins: " + enemy.getCoinCollected());

                        characterIterator.remove();
                    }
                }

                // Start the PauseTransition
                pauseShoot.play();
                // Start the PauseTransition
                pauseCanShoot.play();
            }
        });


        // X Position of the joker
        enemy.getImageView().setTranslateX(enemy.getPositionX());

        rootGame.getChildren().add(enemy.getImageView());


        AnimationTimer timer = new AnimationTimer() {
            double yOffset = 0; // For the sinusoidal movement of the
            // furtive heroes

            @Override
            public void handle(long now) {
                if (gameIsRunning) {
                    if (lastTime != 0) {

                        // Frame and speed calculations
                        double deltaTime = (now - lastTime) / 1e9;
                        double moveSpeedPerSecond = enemy.getHorizontalSpeed();
                        double moveSpeedPerFrame =
                                moveSpeedPerSecond * deltaTime;


                        // Enemy jumping mechanic
                        if (enemy.isJumping()) {
                            enemy.jump();
                            enemy.setJumping(false);
                        }
                        enemy.applyGravity(deltaTime);
                        enemy.getImageView().setTranslateY(enemy.getPositionY());

                        // Continuous background
                        backgroundView1.setTranslateX(backgroundView1.getTranslateX() - moveSpeedPerFrame);
                        backgroundView2.setTranslateX(backgroundView2.getTranslateX() - moveSpeedPerFrame);

                        if (backgroundView1.getTranslateX() <= -backgroundImage.getWidth()) {
                            backgroundView1.setTranslateX(backgroundView2.getTranslateX() + backgroundImage.getWidth());
                        }
                        if (backgroundView2.getTranslateX() <= -backgroundImage.getWidth()) {
                            backgroundView2.setTranslateX(backgroundView1.getTranslateX() + backgroundImage.getWidth());
                        }

                        // Spawn a coin every 2 seconds, adding it to the
                        // coins Arraylist
                        if ((now - lastCoinTime) / 1e9 >= 2.0) {
                            Coin coin = new Coin();
                            // X Position of the coin
                            coin.getImageView().setTranslateX(coin.getPositionX());
                            // Y Position of the coin image
                            coin.getImageView().setTranslateY(coin.getPositionY());
                            rootGame.getChildren().add(coin.getImageView()); //
                            // Add it to the rootGame

                            coins.add(coin);
                            lastCoinTime = now; // Update the last coin time
                            //System.out.println("Length of coin array : " +
                            // coins.size());
                        }

                        // Iterator to be able to remove coins from the
                        // Arraylist, while cycling through all the coins
                        Iterator<Coin> iterator = coins.iterator();
                        while (iterator.hasNext()) {
                            Coin coin = iterator.next();
                            coin.setPositionX(coin.getPositionX() - moveSpeedPerFrame);
                            coin.getImageView().setTranslateX(coin.getPositionX());

                            // If the coin gets out of the screen to the
                            // left, remove it from the ArrayList
                            if (coin.getPositionX() + coin.getRadius() * 2 <= 0) {
                                rootGame.getChildren().remove(coin.getImageView()); // Remove from the scene graph
                                iterator.remove(); // Remove from the list
                            }

                            // If the enemy gets the coin, remove it from
                            // the screen and add it to the coin counter
                            // All the conditions to verify if the coins is
                            // in the enemy's hitbox
                            if (coin.getPositionX() <= enemy.getPositionX() + enemy.getRadius() * 2 &&
                                    coin.getPositionX() >= enemy.getPositionX() - enemy.getRadius() * 2 &&
                                    coin.getPositionY() <= enemy.getPositionY() + enemy.getRadius() * 2 &&
                                    coin.getPositionY() >= enemy.getPositionY() - enemy.getRadius() * 2) {

                                rootGame.getChildren().remove(coin.getImageView()); // Remove from the scene graph
                                iterator.remove(); // Remove from the list

                                // Add the coin to the counter and update it
                                enemy.setCoinCollected(1);
                                coinText.setText("Coins: " + enemy.getCoinCollected());
                            }
                        }
                        //*
                        // Spawns a Hero every 3 seconds, adding it to the
                        // hero Arraylist

                        if ((now - lastHeroTime) / 1e9 >= 3.0) {
                            // Random number. If i == 0 => Melee. If i == 1
                            // => Furtif. If i == 2 => Tank
                            int randomHeroType =
                                    (int) Math.floor((Math.random() * 3));

                            if (randomHeroType == 0) {
                                HeroMelee melee = new HeroMelee();
                                // X position of the melee Hero
                                melee.getImageView().setTranslateX(melee.getPositionX());
                                // Y Position of the melee Hero
                                melee.getImageView().setTranslateY(melee.getPositionY());
                                rootGame.getChildren().add(melee.getImageView()); // Add it to the root

                                heroes.add(melee);
                            } else if (randomHeroType == 1) {
                                HeroFurtif furtif = new HeroFurtif();

                                // X position of the melee Hero
                                furtif.getImageView().setTranslateX(furtif.getPositionX());
                                // Y Position of the melee Hero
                                furtif.getImageView().setTranslateY(furtif.getPositionY());
                                rootGame.getChildren().add(furtif.getImageView()); // Add it to the root

                                heroes.add(furtif);
                            } else {
                                HeroTank tank = new HeroTank();
                                // X position of the melee Hero
                                tank.getImageView().setTranslateX(tank.getPositionX());
                                // Y Position of the melee Hero
                                tank.getImageView().setTranslateY(tank.getPositionY());
                                rootGame.getChildren().add(tank.getImageView()); // Add it to the rootGame

                                heroes.add(tank);
                            }

                            lastHeroTime = now; // Update lastHeroTime

                        }
                        yOffset = 0.5 * Math.sin(now * 1e-9); // 50 pixels
                        // of amplitude


                        Iterator<Character> characterIterator =
                                heroes.iterator();

                        while (characterIterator.hasNext()) {
                            Character character = characterIterator.next();
                            character.setPositionX(character.getPositionX() - moveSpeedPerFrame);
                            character.getImageView().setTranslateX(character.getPositionX());

                            // Sinusoidal movement for furtive heroes
                            if (character instanceof HeroFurtif) {
                                ((HeroFurtif) character).updatePosition(now);


                            } else if (character instanceof HeroTank) {

                                // If the elasped time is greater than
                                // 500000000 ns = 0.5 seconds
                                if ((now - tankLastTeleportTime) >= 500000000) {

                                    ((HeroTank) character).updatePosition();
                                    tankLastTeleportTime = now; // Update
                                    // last teleportation time
                                }
                            }

                            // If the Hero gets out of the screen to the left,
                            // remove it from the ArrayList
                            if (character.getPositionX() + character.getRadius() * 2 <= 0) {
                                rootGame.getChildren().remove(character.getImageView()); // Remove from the scene graph
                                characterIterator.remove(); // Remove from
                                // the list
                            }

                            // All the conditions to verify if the coins is
                            // in the enemy's hitbox
                            if (character.getPositionX() <= enemy.getPositionX() + enemy.getRadius() * 2 &&
                                    character.getPositionX() >= enemy.getPositionX() - enemy.getRadius() * 2 &&
                                    character.getPositionY() <= enemy.getPositionY() + enemy.getRadius() * 2 &&
                                    character.getPositionY() >= enemy.getPositionY() - enemy.getRadius() * 2) {

                                // If the hero is melee type, the enemy
                                // looses all HP
                                if (character.getType().equals("melee")) {
                                    enemy.setHealth(-character.getAttackDamage());
                                    lifeText.setText("Life: " + enemy.getHealth());
                                    System.out.println("Flash: " + enemy.getHealth());

                                } else if (character.getType().equals(
                                        "furtif")) {
                                    // Furtif type
                                    enemy.setCoinCollected(-character.getCoinStealAmount());
                                    coinText.setText("Coins: " + enemy.getCoinCollected());
                                    System.out.println("Arrow: " + enemy.getCoinCollected());
                                } else {
                                    // Tank type
                                    enemy.setHealth(-character.getAttackDamage());
                                    lifeText.setText("Life: " + enemy.getHealth());
                                    System.out.println("SUPERMAN: " + enemy.getHealth());
                                }

                                rootGame.getChildren().remove(character.getImageView()); // Remove from the scene graph
                                characterIterator.remove(); // Remove from
                                // the list
                            }
                            // Check if the game should end
                            if (!enemy.getIsAlive()) {
                                gameIsRunning = false;
                                SoundPlayer.playSound("src/assets" +
                                        "/soundEffects" +
                                        "/gameOverSound" +
                                        ".mp3");
                                showGameOverScreen();
                                this.stop(); // Stop the animation timer
                            }
                        }
                    }
                    lastTime = now;
                }
            }
        };
        timer.start();
    }


    private void showGameOverScreen() {
        gameIsRunning = false; // Stop the game loop or any other updates

        Platform.runLater(() -> {
            VBox gameOverScreen = new VBox(20);
            gameOverScreen.setAlignment(Pos.CENTER);

            // Game Over label
            Label gameOverLabel = new Label("Game Over!");
            gameOverLabel.setFont(Font.font("Arial", 30));
            gameOverLabel.setTextFill(Color.RED);

            // Coin collected label
            Label coinCollectedLabel =
                    new Label("You died collecting: " + enemy.getCoinCollected() + " coins!");
            coinCollectedLabel.setFont(Font.font("Arial", 18));
            coinCollectedLabel.setTextFill(Color.WHITE);

            // Back to Menu button
            Button backToMenuButton = new Button("Back to Menu");
            backToMenuButton.setPrefWidth(150);
            backToMenuButton.setPrefHeight(50);
            backToMenuButton.setStyle("-fx-font-size: 15px;");
            backToMenuButton.setOnAction(e -> {
                resetGame();
                updateMusic(); // Stop music when game is over
            });

            gameOverScreen.getChildren().addAll(gameOverLabel,
                    coinCollectedLabel, backToMenuButton);
            gameOverScreen.setStyle("-fx-background-color: rgb(0, 0, 0)");

            Scene gameOverScene = new Scene(gameOverScreen, 400, 300);
            gameOverStage = new Stage();
            gameOverStage.initModality(Modality.APPLICATION_MODAL);
            gameOverStage.setScene(gameOverScene);
            gameOverStage.showAndWait();
        });
    }

    private void resetGame() {
        enemy.resetEnemyStats();
        coins.clear();
        heroes.clear();
        rootGame.getChildren().clear();
        start(primaryStage);

        // Close the game over pop-up
        if (gameOverStage != null) {
            gameOverStage.close();
        }
    }

    private void updateMusic() {
        if (gameIsRunning) {
            SoundPlayer.stopSound("src/assets/soundEffects/creepyMusic.mp3");
            System.out.println("Music stopped because the game is running.");
        } else {
            SoundPlayer.playSound("src/assets/soundEffects/creepyMusic.mp3");
            System.out.println("Music playing because the game is not running.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
