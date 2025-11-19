package week6.game.controller;

import week6.game.data.FallingWord;
import week6.game.model.GameModel;
import week6.game.view.GameView;

import javax.swing.*;
import java.util.List;

public class GameController {
    public static final float DELTA_TIME = 1f / 60; // how many seconds per frame
    private static final int SPAWN_INTERVAL_MS = 2000;
    private static final int SPAWN_RATE = 2; // words per spawn

    private final GameModel model;

    public GameController(GameModel model, GameView view) {
        this.model = model;

        view.setOnPause(unused -> {
            togglePause();
            return null;
        });

        view.getInputTextBox().setOnInputSubmitted(word -> {
            processInput(word);
            return null;
        });
    }

    public void startTimers() {
        Timer gameTimer = new Timer((int) (DELTA_TIME * 1000), e -> updateGame());
        gameTimer.setInitialDelay(0);
        gameTimer.start();

        Timer spawnTimer = new Timer(SPAWN_INTERVAL_MS, e -> {
            if (model.isPaused()) {
                return;
            }

            int amount = (int) (Math.random() * SPAWN_RATE) + 1;
            model.spawnNewWords(amount);
        });
        spawnTimer.setInitialDelay(0);
        spawnTimer.start();
    }

    public void processInput(String input) {
        if (model.isPaused()) {
            return;
        }

        var matchedWords = model.findMatches(input);
        if (!matchedWords.isEmpty()) {
            model.setCurrentScore(model.getCurrentScore() + matchedWords.size() * 10);
            model.getCurrentWords().removeAll(matchedWords);
        }
        else {
            // Handle incorrect input (e.g., reduce health)
            model.setCurrentScore(model.getCurrentScore() - 5);
            model.setCurrentHealth(model.getCurrentHealth() - 5);
        }

        model.notifyObservers();
    }

    public void updateGame() {
        if (model.isPaused()) {
            return;
        }

        // 1. & 3. Update position and remove missed words.
        List<FallingWord> missedWords = model.advanceAndCleanWords(DELTA_TIME);

        // 2. Update HP and score based on missed words.
        model.applyMissPenalty(missedWords);

        // 3. Notify observers to update the view.
        model.notifyObservers();
    }

    public void togglePause() {
        model.setPaused(!model.isPaused());
        model.notifyObservers();
    }
}
