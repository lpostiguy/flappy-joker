import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Controller class responsible for managing game logic and interactions.
 */
public class Controller {
    private final WindowView windowView;
    private Enemy enemy;
    private boolean gameIsRunning = false;
    private long lastTime = 0;

    // ArrayList containing all the coins
    private ArrayList<Coin> coins = new ArrayList<>();
    private long lastCoinTime = 0;

    // ArrayList containing all the heroes
    private ArrayList<Character> heroes = new ArrayList<>();
    private long lastHeroTime = 0;

    // Counter for a new teleportation
    double tankLastTeleportTime = 0;
    private Character lastMatchingCharacter;
    private double moveSpeedPerFrame;
    AnimationTimer timer;

    /**
     * Constructs a Controller object with a reference to the WindowView.
     *
     * @param windowView The WindowView associated with the game.
     */
    public Controller(WindowView windowView) {
        this.windowView = windowView;
    }

    /**
     * Initializes the game state.
     */
    public void initializeGame() {
        updateMusic();
        // Initialize the game
        gameIsRunning = true;
        lastTime = System.nanoTime();
        lastCoinTime = System.nanoTime();
        lastHeroTime = System.nanoTime();
        enemy = new Enemy();
        coins = new ArrayList<>();
        heroes = new ArrayList<>();
        gameIsRunning = false;
        windowView.showMenuScreen();
    }

    /**
     * Gets the current state of the game.
     *
     * @return true if the game is running, false otherwise.
     */
    public boolean getGameIsRunning() {
        return gameIsRunning;
    }

    /**
     * Gets the health of the enemy.
     *
     * @return the health of the enemy.
     */
    public int getEnemyHealth() {
        return enemy.getHealth();
    }

    /**
     * Gets the number of coins collected by the enemy.
     *
     * @return the number of coins collected by the enemy.
     */
    public int getEnemyCoinCollected() {
        return enemy.getCoinCollected();
    }

    /**
     * Gets the X position of the enemy.
     *
     * @return the X position of the enemy.
     */
    public double getEnemyPositionX() {
        return enemy.getPositionX();
    }

    /**
     * Gets the Y position of the enemy.
     *
     * @return the Y position of the enemy.
     */
    public double getEnemyPositionY() {
        return enemy.getPositionY();
    }

    /**
     * Gets the radius of the enemy.
     *
     * @return the radius of the enemy.
     */
    public double getEnemyRadius() {
        return enemy.getRadius();
    }

    /**
     * Gets the ImageView representing the enemy.
     *
     * @return the ImageView representing the enemy.
     */
    public ImageView getEnemyImageView() {
        return enemy.getImageView();
    }

    /**
     * Gets the message displayed when the game is over.
     *
     * @return the game over message.
     */
    public String getGameOverMessage() {
        // Retrieve game over message from the model
        return "You died collecting: " + enemy.getCoinCollected() + " coins!";
    }

    /**
     * Retrieves the ImageView associated with the last matching character.
     *
     * @return the ImageView associated with the last matching character.
     */
    public ImageView getHeroImageView() {
        return lastMatchingCharacter.getImageView();
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        enemy.resetEnemyStats();
        coins.clear();
        heroes.clear();
        windowView.getRootGame().getChildren().clear();
        windowView.closeGameOverPopUp();
        windowView.showMenuScreen();
        timer.stop();
        timer.start();
        updateMusic();
    }

    /**
     * Changes the state of the game run. If the game is currently running, it
     * stops it; otherwise, it starts it. Also updates the ability of the enemy
     * to shoot based on the game state.
     */
    public void changeGameRunState() {
        gameIsRunning = !gameIsRunning;
        enemy.setCanShoot(gameIsRunning);
    }

    /**
     * Starts the game when the start button is clicked.
     */
    public void startButtonClick() {
        gameIsRunning = true;
        windowView.showGameScreen();
        updateMusic();
        animationTimer();
    }

    /**
     * Updates the game music based on the game state.
     */
    public void updateMusic() {
        if (gameIsRunning) {
            SoundPlayer.stopSound("src/assets/soundEffects/creepyMusic.mp3");
        } else {
            SoundPlayer.playSound("src/assets/soundEffects/creepyMusic.mp3");
        }
    }


    /**
     * Updates the characters in the game, checking for collisions with the
     * enemy. If a character collides with the enemy, it is considere killed,
     * removed from the game, and its associated rewards are given to the
     * enemy.
     */
    public void updateCharacters() {
        Iterator<Character> characterIterator = heroes.iterator();
        while (characterIterator.hasNext()) {
            Character character = characterIterator.next();
            if (enemy.getPositionY() <= character.getPositionY() + character.getRadius() &&
                    enemy.getPositionY() >= character.getPositionY() - character.getRadius()) {
                lastMatchingCharacter = character; // Stores the character
                // that is hitten by the enemy
                windowView.removeHero();
                characterIterator.remove();
                // Give coin to the enemy after killing a hero
                giveEnemyCoins();
            }
        }
    }

    /**
     * Handles key press events in the game.
     *
     * @param code the KeyCode corresponding to the pressed key.
     */
    public void handleKeyPress(KeyCode code) {
        if (code == KeyCode.SPACE) {
            enemy.setJumping(true);
        }
        if (code == KeyCode.E && enemy.getCanShoot()) {
            enemy.attackSound();
            windowView.gunShot();
            enemy.setCanShoot(false);
            PauseTransition pauseCanShoot =
                    new PauseTransition(Duration.seconds(1));
            pauseCanShoot.setOnFinished(e -> {
                enemy.setCanShoot(true);
            });
            // Start the PauseTransition
            pauseCanShoot.play();
            // Killing heroes
            updateCharacters();
        }
    }

    /**
     * Adds a coins to the game.
     */
    public void addCoin() {
        Coin coin = new Coin();

        // X Position of the coin
        coin.getImageView().setTranslateX(coin.getPositionX());
        // Y Position of the coin image
        coin.getImageView().setTranslateY(coin.getPositionY());
        windowView.getRootGame().getChildren().add(coin.getImageView());
        // Add it to the rootGame

        coins.add(coin);

    }

    /**
     * Updates the position and behavior of coins in the game.
     *
     * @param moveSpeedPerFrame the movement speed of coins per frame.
     */
    public void coinUpdate(double moveSpeedPerFrame) {
        Iterator<Coin> iterator = coins.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.setPositionX(coin.getPositionX() - moveSpeedPerFrame);
            coin.getImageView().setTranslateX(coin.getPositionX());

            // Check collision with the enemy
            if (enemy.coinIntersect(coin)) {
                // If the enemy gets the coin, remove it from the screen.
                windowView.getRootGame().getChildren().remove(coin.getImageView());
                iterator.remove(); // Remove from the list

                // Add the coin to the counter and update UI
                enemy.setCoinCollected(enemy.getCoinCollected() + 1);
                windowView.updateCoinText();
            } else if (coin.getPositionX() + coin.getRadius() * 2 <= 0) {
                // If the coin gets out of the screen to the left, remove it
                windowView.getRootGame().getChildren().remove(coin.getImageView());
                iterator.remove();
            }
        }
    }

    /**
     * Gives coins to the enemy based on the coin drop amount of the last
     * matching character, updates the coin text in the window view, and resets
     * the last matchin character reference.
     */
    public void giveEnemyCoins() {
        enemy.setCoinCollected(enemy.getCoinCollected() + lastMatchingCharacter.getCoinDropAmount());
        windowView.updateCoinText();
        lastMatchingCharacter = null;
    }

    /**
     * Generates new hero characters in the game.
     *
     * @param now the current time in nanoseconds.
     */
    public void generateHero(long now) {
        // Spawns a Hero every 3 seconds, adding it to the
        // hero Arraylist

        if ((now - lastHeroTime) / 1e9 >= 3.0) {
            // Random number. If i == 0 => Melee. If i == 1
            // => Furtif. If i == 2 => Tank
            int randomHeroType =
                    (int) Math.floor((Math.random() * 3));

            // For Melee Hero
            if (randomHeroType == 0) {
                HeroMelee melee = new HeroMelee();
                melee.getImageView().setTranslateX(melee.getPositionX());
                melee.getImageView().setTranslateY(melee.getPositionY());
                windowView.getRootGame().getChildren().add(melee.getImageView());
                heroes.add(melee);

                // For Furtif Hero
            } else if (randomHeroType == 1) {
                HeroFurtif furtif = new HeroFurtif();
                furtif.getImageView().setTranslateX(furtif.getPositionX());
                furtif.getImageView().setTranslateY(furtif.getPositionY());
                windowView.getRootGame().getChildren().add(furtif.getImageView());
                heroes.add(furtif);
            }

            // For Tank Hero
            else {
                HeroTank tank = new HeroTank();
                tank.getImageView().setTranslateX(tank.getPositionX());
                tank.getImageView().setTranslateY(tank.getPositionY());
                windowView.getRootGame().getChildren().add(tank.getImageView());
                heroes.add(tank);
            }

            lastHeroTime = now;

        }
    }

    /**
     * Updates the position and behavior of hero characters in the game.
     *
     * @param now the current time in nanoseconds.
     */
    public void updateHero(long now) {
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
                    tankLastTeleportTime = now;
                }
            }

            // If the Hero gets out of the screen to the left,
            // remove it from the ArrayList
            if (character.getPositionX() + character.getRadius() * 2 <= 0) {
                windowView.getRootGame().getChildren().remove(character.getImageView());
                characterIterator.remove();
            }
            // If the hero is in the enemy's hitbox
            if (enemy.characterIntersect(character)) {

                // If the hero is melee type, the enemy
                // looses all HP
                if (character instanceof HeroMelee) {
                    enemy.setHealth(-character.getAttackDamage());
                    windowView.updateLifeText();

                } else if (character instanceof HeroFurtif) {
                    // Furtif type
                    enemy.setCoinCollected(getEnemyCoinCollected() - character.getCoinStealAmount());
                    windowView.updateCoinText();
                } else {
                    // Tank type
                    enemy.setHealth(-character.getAttackDamage());
                    windowView.updateLifeText();
                }

                windowView.getRootGame().getChildren().remove(character.getImageView());
                characterIterator.remove();
            }
        }
    }

    /**
     * Handles the game over scenario.
     */
    public void gameOver() {
        gameIsRunning = false;
        SoundPlayer.playSound("src/assets" +
                "/soundEffects" +
                "/gameOverSound" +
                ".mp3");
        windowView.showGameOverScreen();
    }

    /**
     * Initiates the animation timer for the game loop.
     */
    public void animationTimer() {
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (gameIsRunning) {
                    if (lastTime != 0) {

                        // Frame and speed calculations
                        double deltaTime = (now - lastTime) / 1e9;
                        double moveSpeedPerSecond = enemy.getHorizontalSpeed();
                        moveSpeedPerFrame = moveSpeedPerSecond * deltaTime;


                        // Enemy jumping mechanic
                        if (enemy.isJumping()) {
                            enemy.jump();
                            enemy.setJumping(false);
                        }
                        enemy.applyGravity(deltaTime);
                        enemy.getImageView().setTranslateY(enemy.getPositionY());

                        windowView.moveBackground(moveSpeedPerFrame);

                        // Spawn a coin every 2 seconds, adding it to the
                        // coins Arraylist
                        if ((now - lastCoinTime) / 1e9 >= 2.0) {
                            addCoin();
                            lastCoinTime = now; // Update the last coin time
                        }

                        // Update the coins
                        coinUpdate(moveSpeedPerFrame);

                    }

                    // Spawns a Hero every 3 seconds
                    generateHero(now);
                    updateHero(now);

                    if (!enemy.getIsAlive()) {
                        gameOver();
                        this.stop(); // Stop the animation timer
                    }
                }
                lastTime = now;
            }
        };
        timer.start();
    }
}
