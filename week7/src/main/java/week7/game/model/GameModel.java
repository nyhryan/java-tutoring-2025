package week7.game.model;

import lombok.Getter;
import lombok.Setter;
import week7.game.data.FallingWord;
import week7.game.Subject;
import week7.game.data.WordRepository;
import week7.game.view.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class GameModel implements Subject {
    private static final int WORD_SPEED_PPS = 40; // Pixels Per Second
    private static final int MISS_HEALTH_PENALTY = 10;

    private int currentScore = 0;
    private int currentHealth = 100;
    private List<FallingWord> currentWords = new LinkedList<>();
    private boolean isPaused = false;

    private final List<week7.game.Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(week7.game.Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(week7.game.Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(week7.game.Observer::update);
    }

    public List<FallingWord> findMatches(String input) {
        if (input.isBlank()) {
            return List.of();
        }

        return currentWords.stream()
                .filter(s -> s.getWord().equals(input))
                .toList();
    }

    /**
     * Step 1 & 3: Updates positions and removes words that have moved past the floor.
     *
     * @return A list of words that were missed (removed).
     */
    public List<FallingWord> advanceAndCleanWords(float dt) {

        // Step 1: Update positions
        currentWords.forEach(word -> {
            float distanceY = WORD_SPEED_PPS * dt;
            word.setY(word.getY() + distanceY);
        });

        // Step 3 (part 1): Identify words that have missed (reached the ground)
        List<FallingWord> missedWords = currentWords.stream()
                .filter(word -> word.getY() >= GameView.HEIGHT)
                .toList();

        // Step 3 (part 2): Remove missed words
        currentWords.removeAll(missedWords);

        return missedWords;
    }

    /**
     * Step 2: Applies health/score penalties for missed words.
     */
    public void applyMissPenalty(List<FallingWord> missedWords) {
        if (!missedWords.isEmpty()) {
            int penalty = missedWords.size() * MISS_HEALTH_PENALTY;
            currentHealth = Math.max(0, currentHealth - penalty); // Ensure health doesn't go below 0
        }
    }

    /**
     * Step 4: Spawns new words using a non-overlapping algorithm.
     * * NOTE: This method must receive the necessary width data from the View/Controller.
     */
    public void spawnNewWords(int amount) {
        var newWords = WordRepository.pickRandomWords(amount).stream()
                .map(FallingWord::new)
                .toList();

        final int PADDING = 10;
        final int MIN_SPACING = 15; // Minimum horizontal gap between words
        final int Y_VARIATION = 30;
        List<Rectangle> placedBounds = new ArrayList<>();

        for (var fallingWord : newWords) {
            int width = GameView.getWidthOfWord(fallingWord.getWord());
            int height = GameView.getHeightOfWord(fallingWord.getWord());

            int x = -1;
            int baseY = 20;
            int y = baseY + (int) (Math.random() * Y_VARIATION);
            int maxAttempts = 50;

            // Try to find a non-overlapping position
            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                int candidateX = (int) (Math.random() * (GameView.WIDTH - width - PADDING * 2)) + PADDING;
                Rectangle candidateBounds = new Rectangle(
                        candidateX - MIN_SPACING,
                        y - height,
                        width + MIN_SPACING * 2,
                        height + MIN_SPACING
                );

                boolean overlaps = placedBounds.stream()
                        .anyMatch(rect -> rect.intersects(candidateBounds));

                if (!overlaps) {
                    x = candidateX;
                    placedBounds.add(new Rectangle(candidateX, y - height, width, height));
                    break;
                }
            }

            // Fallback: if we couldn't find a spot, place it anyway (rare case)
            if (x == -1) {
                x = (int) (Math.random() * (GameView.WIDTH - width - PADDING * 2)) + PADDING;
            }

            fallingWord.setX(x);
            fallingWord.setY(y);
            fallingWord.setSpeed((int) (Math.random() * 10) - 5 + WORD_SPEED_PPS);
        }

        currentWords.addAll(newWords);
    }
}
