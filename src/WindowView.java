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
import javafx.util.Duration;

/**
 * This class represents the main window view of the game application. It
 * handles the display of the menu screen, game screen, and game over screen,
 * as well as various UI elements and interactions within these screens.
 */
public class WindowView extends Application {
    private BorderPane rootMenu;
    private BorderPane rootGame;
    private Stage primaryStage;
    private Stage gameOverStage;
    private Line redLine;
    ImageView backgroundView1;
    ImageView backgroundView2;
    Image backgroundImage;
    Label coinText;
    Label lifeText;
    private Controller controller;

    /**
     * Initializes the primary stage and sets up the menu screen.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        rootGame = new BorderPane();
        rootMenu = new BorderPane();

        Scene menuScene = new Scene(rootMenu, 640, 440);

        Image menuBackgroundImage = new Image("/assets/images/JokerBg.png");
        ImageView menuImageView = new ImageView(menuBackgroundImage);
        rootMenu.getChildren().add(menuImageView);

        VBox mainMenuBox = new VBox();
        mainMenuBox.setPadding(new Insets(0, 0, 100, 50));
        mainMenuBox.setAlignment(Pos.CENTER_LEFT);

        Label unoReverseTitle = new Label("UNO Reverse Flappy");
        unoReverseTitle.setStyle("-fx-text-fill: #61355C; -fx-font-size: " +
                "30px;" +
                " -fx-font-weight: bold");
        unoReverseTitle.setPadding(new Insets(0, 0, 100, 0));

        Button startButton = new Button("Start Game");
        startButton.setPadding(new Insets(10, 20, 10, 20));
        startButton.setStyle("-fx-background-color: #4CAE25; -fx-text-fill: " +
                "black; -fx-background-radius: 10; -fx-font-size: 16px; " +
                "-fx-font-weight: bold");
        startButton.setMinWidth(120);
        startButton.setOnAction(e -> {
            showGameScreen();
            controller.startButtonClick();
        });

        mainMenuBox.getChildren().addAll(unoReverseTitle, startButton);
        rootMenu.setCenter(mainMenuBox);

        rootMenu.setUserData(menuScene);

        controller = new Controller(this);
        controller.initializeGame();

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    /**
     * Displays the menu screen.
     */
    public void showMenuScreen() {
        // Retrieve the scene from the member variable
        Scene menuScene = (Scene) rootMenu.getUserData();

        // Set the scene to the primary stage
        primaryStage.setScene(menuScene);
    }

    /**
     * Displays the game screen.
     */
    public void showGameScreen() {
        rootGame = new BorderPane();
        // Initialize the Game scene
        Scene gameScene = new Scene(rootGame, 640, 440);
        // Load the background image for the game scene
        backgroundImage = new Image("/assets/images/Bg.png");
        backgroundView1 = new ImageView(backgroundImage);
        backgroundView2 = new ImageView(backgroundImage);
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
            controller.changeGameRunState();
            pauseButton.setText(controller.getGameIsRunning() ? "Pause" :
                    "Resume");
        });

        VBox pauseButtonBox = new VBox(pauseButton);
        pauseButtonBox.setPadding(new Insets(0, 10, 0, 10));

        lifeText = new Label("Life: " + controller.getEnemyHealth());
        lifeText.setFont(Font.font("Arial", 16));
        lifeText.setPadding(new Insets(0, 10, 0, 10));

        coinText =
                new Label("Coins:" + controller.getEnemyCoinCollected());
        coinText.setFont(Font.font("Arial", 16));
        coinText.setPadding(new Insets(0, 10, 0, 10));

        bottomGameInfo.getChildren().addAll(pauseButtonBox,
                new Separator(Orientation.VERTICAL), lifeText,
                new Separator(Orientation.VERTICAL), coinText);
        bottomGameInfo.setAlignment(Pos.CENTER);
        rootGame.setBottom(bottomGameInfo);
        // X Position of the joker
        controller.getEnemyImageView().setTranslateX(controller.getEnemyPositionX());

        rootGame.getChildren().add(controller.getEnemyImageView());

        gameScene.setOnKeyPressed(event -> controller.handleKeyPress(event.getCode()));
        // Set the scene to the primary stage
        primaryStage.setScene(gameScene);

        // Instantiation of the gun shot red line
        redLine = new Line();
        redLine.setStroke(Color.RED);
        redLine.setStrokeWidth(2);
        redLine.setVisible(false); // Initially invisible
        rootGame.getChildren().add(redLine);
    }

    /**
     * Displays the game over screen.
     */
    public void showGameOverScreen() {
        Platform.runLater(() -> {
            VBox gameOverScreen = new VBox(20);
            gameOverScreen.setAlignment(Pos.CENTER);

            // Game Over label
            Label gameOverLabel = new Label("Game Over!");
            gameOverLabel.setFont(Font.font("Arial", 30));
            gameOverLabel.setTextFill(Color.RED);

            // Coin collected label
            Label coinCollectedLabel =
                    new Label(controller.getGameOverMessage());
            coinCollectedLabel.setFont(Font.font("Arial", 18));
            coinCollectedLabel.setTextFill(Color.WHITE);

            // Back to Menu button
            Button backToMenuButton = new Button("Back to Menu");
            backToMenuButton.setPrefWidth(150);
            backToMenuButton.setPrefHeight(50);
            backToMenuButton.setStyle("-fx-font-size: 15px;");
            backToMenuButton.setOnAction(e -> {
                controller.resetGame();
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

    /**
     * Updates the displayed coin count.
     */
    public void updateCoinText() {
        coinText.setText("Coins:" + controller.getEnemyCoinCollected());
    }

    /**
     * Updates the displayed health points.
     */
    public void updateLifeText() {
        lifeText.setText("Life:" + controller.getEnemyHealth());
    }


    /**
     * Retrieves the root node of the game scene.
     *
     * @return The root node (rootGame) of the game scene.
     */
    public BorderPane getRootGame() {
        return rootGame;
    }

    /**
     * Closes the game over popup window.
     */
    public void closeGameOverPopUp() {
        gameOverStage.close();
    }

    /**
     * Displays a visual representation of a gunshot.
     */
    public void gunShot() {
        // Updates the position of the existing line
        redLine.setStartX(controller.getEnemyPositionX() + 2 * controller.getEnemyRadius());
        redLine.setEndX(640);
        redLine.setStartY(controller.getEnemyPositionY() + controller.getEnemyRadius());
        redLine.setEndY(controller.getEnemyPositionY() + controller.getEnemyRadius());
        redLine.setVisible(true);
        removeGunShotLineWithDelay();
    }

    /**
     * Removes the visuale representation of a gunshot after a short delay.
     */
    public void removeGunShotLineWithDelay() {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.05));
        delay.setOnFinished(e -> redLine.setVisible(false));
        delay.play();
    }

    /**
     * Removes the hero from the game scene.
     */
    public void removeHero() {
        rootGame.getChildren().remove(controller.getHeroImageView());
    }

    /**
     * Moves the background images to create a parallax effect.
     *
     * @param moveSpeedPerFrame The speed at which the background should move
     *                          per frame.
     */
    public void moveBackground(double moveSpeedPerFrame) {

        backgroundView1.setTranslateX(backgroundView1.getTranslateX() - moveSpeedPerFrame);
        backgroundView2.setTranslateX(backgroundView2.getTranslateX() - moveSpeedPerFrame);

        if (backgroundView1.getTranslateX() <= -backgroundImage.getWidth()) {
            backgroundView1.setTranslateX(backgroundView2.getTranslateX() + backgroundImage.getWidth());
        }
        if (backgroundView2.getTranslateX() <= -backgroundImage.getWidth()) {
            backgroundView2.setTranslateX(backgroundView1.getTranslateX() + backgroundImage.getWidth());
        }
    }
}
