package week6.game.data;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

public class WordRepository {
    public static final List<String> words = List.of(
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "kiwi", "lemon",
            "mango", "nectarine", "orange", "papaya", "quince",
            "raspberry", "strawberry", "tangerine", "ugli", "voavanga"
    );

    private static final List<String> cachedWords = new LinkedList<>();

    public static List<String> pickRandomWords(int count) {
        if (cachedWords.size() < count) {
            cachedWords.clear();
            var shuffled = new LinkedList<>(words);
            shuffle(shuffled);
            cachedWords.addAll(shuffled);
        }
        List<String> picked = new LinkedList<>(cachedWords.subList(0, count));
        cachedWords.subList(0, count).clear();
        return picked;
    }
}
