package week6.game;

import week6.game.view.GameView;

import javax.swing.*;

public class WordGameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameView::new);
    }
}

