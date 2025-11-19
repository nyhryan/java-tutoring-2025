package week6.game.view;

import lombok.Setter;

import javax.swing.*;
import java.util.function.Function;

public class InputTextBox extends JTextField {
    @Setter
    private Function<String, Void> onInputSubmitted;

    public InputTextBox() {
        addActionListener(e -> {
            String input = getText().trim();
            if (onInputSubmitted != null) {
                onInputSubmitted.apply(input);
                setText("");
            }
        });

        setFont(GameView.GAME_FONT);

        requestFocus();
        setFocusable(true);
    }

    public void toggleEnable() {
        setEnabled(!isEnabled());
        if (isEnabled()) {
            requestFocusInWindow();
        }
    }
}
