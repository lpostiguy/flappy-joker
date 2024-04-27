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

public class WindowView extends Application {
    private Enemy enemy = new Enemy();
    private boolean gameIsRunning = true;
    private long lastTime = 0; // Variable for the animation
    private Coin coin = new Coin();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 640, 440);
        primaryStage.setTitle("Flappy Enemy");

        // Load the background image
        Image backgroundImage = new Image("/assets/bg.png");
        ImageView backgroundView1 = new ImageView(backgroundImage);
        ImageView backgroundView2 = new ImageView(backgroundImage);
        backgroundView2.setTranslateX(backgroundImage.getWidth());

        Pane backgrounds = new Pane();
        backgrounds.getChildren().addAll(backgroundView1, backgroundView2);
        root.getChildren().add(backgrounds);

        HBox bottomGameInfo = new HBox();
        bottomGameInfo.setPadding(new Insets(10, 0, 10, 0));
        bottomGameInfo.setPrefWidth(scene.getWidth());
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        bottomGameInfo.setBackground(new Background(backgroundFill));

        Button pauseButton = new Button("Pause");
        pauseButton.setPadding(new Insets(5, 10, 5, 10));
        pauseButton.setMinWidth(75);
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(e -> {
            gameIsRunning = !gameIsRunning;
            pauseButton.setText(gameIsRunning ? "Pause" : "Resume");
        });

        VBox buttonBox = new VBox(pauseButton);
        buttonBox.setPadding(new Insets(0, 10, 0, 10));

        Label lifeText = new Label("Life: " + enemy.getHealth());
        lifeText.setFont(Font.font("Arial", 16));
        lifeText.setPadding(new Insets(0, 10, 0, 10));

        Label coinText = new Label("Coins:" + enemy.getCoinCollected());
        coinText.setFont(Font.font("Arial", 16));
        coinText.setPadding(new Insets(0, 10, 0, 10));

        bottomGameInfo.getChildren().addAll(buttonBox, new Separator(Orientation.VERTICAL), lifeText, new Separator(Orientation.VERTICAL), coinText);
        bottomGameInfo.setAlignment(Pos.CENTER);
        root.setBottom(bottomGameInfo);

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                enemy.setJumping(true);
            }
        });


        // X Position of the coin
        coin.getImageView().setTranslateX(coin.getPositionX());
        // Y Position of the coin image
        coin.getImageView().setTranslateY(coin.getPositionY());

        // X Position of the joker
        enemy.getImageView().setTranslateX(enemy.getPositionX());

        root.getChildren().add(enemy.getImageView());
        root.getChildren().add(coin.getImageView());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameIsRunning) {
                    if (lastTime != 0) {
                        double deltaTime = (now - lastTime) / 1e9;
                        double moveSpeedPerSecond = enemy.getHorizontalSpeed();
                        double moveSpeedPerFrame = moveSpeedPerSecond * deltaTime;

                        if (enemy.isJumping()) {
                            enemy.jump();
                            enemy.setJumping(false);
                        }
                        enemy.applyGravity(deltaTime);
                        enemy.getImageView().setTranslateY(enemy.getPositionY());

                        backgroundView1.setTranslateX(backgroundView1.getTranslateX() - moveSpeedPerFrame);
                        backgroundView2.setTranslateX(backgroundView2.getTranslateX() - moveSpeedPerFrame);

                        if (backgroundView1.getTranslateX() <= -backgroundImage.getWidth()) {
                            backgroundView1.setTranslateX(backgroundView2.getTranslateX() + backgroundImage.getWidth());
                        }
                        if (backgroundView2.getTranslateX() <= -backgroundImage.getWidth()) {
                            backgroundView2.setTranslateX(backgroundView1.getTranslateX() + backgroundImage.getWidth());
                        }

                        // Move coin
                        coin.setPositionX(coin.getPositionX() - moveSpeedPerFrame);
                        coin.getImageView().setTranslateX(coin.getPositionX());
                    }
                    lastTime = now;
                }
            }
        };
        timer.start();
    }
}
