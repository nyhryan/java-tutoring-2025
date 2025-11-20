package week7.game;

import week7.game.view.GameView;

import javax.swing.*;

public class WordGameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameView::new);
    }
}

