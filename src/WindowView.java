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

public class WindowView extends Application {
    private boolean gameIsRunning = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 640, 440);
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

        // Animation logic to move both ImageViews
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameIsRunning) {
                    // Move each background image left
                    double moveSpeed = .5;
                    backgroundView1.setTranslateX(backgroundView1.getTranslateX() - moveSpeed);
                    backgroundView2.setTranslateX(backgroundView2.getTranslateX() - moveSpeed);

                    // Reset position when the background image moves out of
                    // view
                    if (backgroundView1.getTranslateX() <= -backgroundImage.getWidth()) {
                        backgroundView1.setTranslateX(backgroundImage.getWidth());
                    }
                    if (backgroundView2.getTranslateX() <= -backgroundImage.getWidth()) {
                        backgroundView2.setTranslateX(backgroundImage.getWidth());
                    }
                }
            }
        };
        timer.start();


        HBox bottomGameInfo = new HBox();
        bottomGameInfo.setPadding(new Insets(10, 0, 10, 0));

        bottomGameInfo.setPrefWidth(scene.getWidth());

        // Set the background color of the VBox
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        bottomGameInfo.setBackground(background);

        String pauseButtonTextPause = "Pause";
        Button pauseButton = new Button(pauseButtonTextPause);
        pauseButton.setPadding(new Insets(5, 10, 5, 10));
        pauseButton.setMinWidth(75);

        VBox buttonBox = new VBox();
        buttonBox.getChildren().add(pauseButton);
        buttonBox.setPadding(new Insets(0, 10, 0, 10));

        // When button clicked
        pauseButton.setOnAction(e -> {
            gameIsRunning = !gameIsRunning;
            pauseButton.setText(gameIsRunning ? "Pause" : "Resume");
        });

        Label lifeText = new Label("Life:");
        lifeText.setFont(Font.font("Arial", 16));
        lifeText.setPadding(new Insets(0, 10, 0, 10));

        Label coinText = new Label("Coin:");
        coinText.setFont(Font.font("Arial", 16));
        coinText.setPadding(new Insets(0, 10, 0, 10));

        bottomGameInfo.getChildren().add(buttonBox);
        bottomGameInfo.getChildren().add(new Separator(Orientation.VERTICAL));
        bottomGameInfo.getChildren().add(lifeText);
        bottomGameInfo.getChildren().add(new Separator(Orientation.VERTICAL));
        bottomGameInfo.getChildren().add(coinText);

        bottomGameInfo.setAlignment(Pos.CENTER);
        root.getChildren().add(bottomGameInfo);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

