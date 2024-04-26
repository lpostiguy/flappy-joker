import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class WindowView extends Application {
    Enemy enemy = new Enemy();
    private boolean gameIsRunning = true;

    long lastTime = 0;  // Variable for the animation
    private Canvas canvas;// TODO : DELETE

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //VBox root = new VBox();
        BorderPane root = new BorderPane();

        int canvasHeight = 640;
        int canvasWidth = 440;

        // TODO : DELETE
        canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
        // End of delete

        Scene scene = new Scene(root, canvasHeight, canvasWidth);
        primaryStage.setTitle("Flappy Enemy");
        // Load the background image
        Image backgroundImage = new Image("/assets/bg.png");

        // Create two ImageView instances for the looping background
        ImageView backgroundView1 = new ImageView(backgroundImage);
        ImageView backgroundView2 = new ImageView(backgroundImage);

        // Position the second ImageView immediately to the right of the
        // first one
        backgroundView2.setTranslateX(backgroundImage.getWidth());

        Pane backgrounds = new Pane();
        backgrounds.getChildren().addAll(backgroundView1, backgroundView2);
        root.getChildren().add(backgrounds);

        // Menu box at the bottom of the screen :p
        HBox bottomGameInfo = new HBox();
        //BorderPane bottomGameInfo = new BorderPane();
        bottomGameInfo.setPadding(new Insets(10, 0, 10, 0));

        bottomGameInfo.setPrefWidth(scene.getWidth());

        // Set the background color of the VBox
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        bottomGameInfo.setBackground(background);

        // Pause button
        String pauseButtonTextPause = "Pause";
        Button pauseButton = new Button(pauseButtonTextPause);
        pauseButton.setPadding(new Insets(5, 10, 5, 10));
        pauseButton.setMinWidth(75);
        // Set the focus traversable property of the pause button to false
        pauseButton.setFocusTraversable(false);

        VBox buttonBox = new VBox();
        buttonBox.getChildren().add(pauseButton);
        buttonBox.setPadding(new Insets(0, 10, 0, 10));

        // When button clicked
        pauseButton.setOnAction(e -> {
            gameIsRunning = !gameIsRunning;
            pauseButton.setText(gameIsRunning ? "Pause" : "Resume");
        });

        // Life counter
        Label lifeText = new Label("Life: " + enemy.getHealth());
        lifeText.setFont(Font.font("Arial", 16));
        lifeText.setPadding(new Insets(0, 10, 0, 10));

        // Coin counter
        Label coinText = new Label("Coins:" + enemy.getCoinCollected());
        coinText.setFont(Font.font("Arial", 16));
        coinText.setPadding(new Insets(0, 10, 0, 10));


        bottomGameInfo.getChildren().add(buttonBox);
        bottomGameInfo.getChildren().add(new Separator(Orientation.VERTICAL));
        bottomGameInfo.getChildren().add(lifeText);
        bottomGameInfo.getChildren().add(new Separator(Orientation.VERTICAL));
        bottomGameInfo.getChildren().add(coinText);

        bottomGameInfo.setAlignment(Pos.CENTER);


        root.setBottom(bottomGameInfo);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Load the enemy image
        // TODO : Make the enemy an instance of Enemy
        Image enemyImage = new Image("/assets/Joker.png");

        // Calculate the new dimensions to maintain a 30 pixel radius
        double enemyRadius = enemy.getRadius();
        double enemyWidth = enemyRadius * 2;
        double enemyHeight = enemyRadius * 2;

        // Create an ImageView with the image
        ImageView enemyView = new ImageView(enemyImage);

        // Set the adjusted size of the ImageView
        enemyView.setFitWidth(enemyWidth);
        enemyView.setFitHeight(enemyHeight);

        // Set the position of the image
        double xPositionEnemy = 50; // Specify your desired X position
        double yPositionEnemy = 320; // Specify your desired Y position
        enemyView.setTranslateX(xPositionEnemy);
        enemyView.setTranslateY(yPositionEnemy);

        // Create a Pane for the enemy image
        Pane enemyPane = new Pane(enemyView);

        // Add the enemy pane to the root VBox
        root.getChildren().add(enemyPane);


        Coin coin = new Coin();

// Set the position of the coin image
        coin.getImageView().setTranslateX(coin.getPositionX()); // Specify your desired X
        // position
        coin.getImageView().setTranslateY(coin.getPositionY()); // Specify
        // your desired Y position

// Add the coin ImageView to the root BorderPane
        root.getChildren().add(coin.getImageView());


        // Set focus on the scene to receive key events
        scene.setOnKeyPressed(event -> {
            // Check if the pressed key is Space Bar
            if (event.getCode() == KeyCode.SPACE) {
                enemy.jump();
            }
        });
        //GraphicsContext gc = canvas.getGraphicsContext2D();
        // Animation logic to move both ImageViews
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameIsRunning) {
                    if (lastTime != 0) {
                        // Calculate time elapsed since the last frame in
                        // seconds
                        double deltaTime = (now - lastTime) / 1e9;

                        // Calculate the movement per frame to achieve
                        double moveSpeedPerSecond = enemy.getHorizontalSpeed();
                        double moveSpeedPerFrame =
                                moveSpeedPerSecond * deltaTime;

                        // Move coin ImageView
                        //coinView.setTranslateX(coinView.getTranslateX() -
                        // moveSpeedPerFrame);

                        // Move each background image left by moveSpeedPerFrame
                        backgroundView1.setTranslateX(backgroundView1.getTranslateX() - moveSpeedPerFrame);
                        backgroundView2.setTranslateX(backgroundView2.getTranslateX() - moveSpeedPerFrame);

                        // Check if the background images have moved
                        // completely off the screen
                        if (backgroundView1.getTranslateX() <= -backgroundImage.getWidth()) {
                            // Reset position of backgroundView1 to the
                            // right of backgroundView2
                            backgroundView1.setTranslateX(backgroundView2.getTranslateX() + backgroundImage.getWidth() - moveSpeedPerFrame);
                        }
                        if (backgroundView2.getTranslateX() <= -backgroundImage.getWidth()) {
                            // Reset position of backgroundView2 to the
                            // right of backgroundView1
                            backgroundView2.setTranslateX(backgroundView1.getTranslateX() + backgroundImage.getWidth() - moveSpeedPerFrame);
                        }

                        // TODO : DELETE
                        // In order to get a red circle instead of enemy
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        enemy.update(deltaTime, canvasHeight);
                        gc.setFill(enemy.getColor());
                        gc.fillOval(enemy.getPositionX(),
                                enemy.getPositionY(), enemy.getRadius(),
                                enemy.getRadius());
                    }
                    // Update lastTime
                    lastTime = now;
                }
            }
        };
        timer.start();
        // Set focus on the scene
        scene.getRoot().requestFocus();

        primaryStage.setScene(scene);

    }
}