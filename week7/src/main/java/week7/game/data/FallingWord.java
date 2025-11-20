package week7.game.data;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FallingWord {
    @Setter
    private float x, y, speed;
    private final String word;

    public FallingWord(String word) {
        this.word = word;
    }
}
