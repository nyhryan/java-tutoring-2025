package week6.game.view;

import week6.game.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

public class GamePanel extends JPanel implements week6.game.Observer {
    private final GameModel model;

    public GamePanel(GameModel model) {
        this.model = model;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;
        g2d.setFont(GameView.GAME_FONT);

        if (!model.isPaused()) {
            drawWords(g2d);
        }

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, GameView.WIDTH, 50);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: %d".formatted(model.getCurrentScore()), 10, 20);
        g2d.drawString("Health: %d".formatted(model.getCurrentHealth()), 10, 40);

        if (model.isPaused()) {
            drawPauseOverlay(g2d);
        }
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(this::repaint);
    }

    private void drawWords(Graphics2D g) {
        for (var fallingWord : model.getCurrentWords()) {
            g.setColor(Color.BLACK);
            float x = fallingWord.getX();
            float y = fallingWord.getY();
            String word = fallingWord.getWord();
            int w = GameView.getWidthOfWord(word);
            int h = GameView.getHeightOfWord(word);
            g.fill(new RoundRectangle2D.Float(x - 5, y - 3f * h / 4, w + 10, h, 10, 10));

            g.setColor(Color.WHITE);
            g.drawString(word, x, y);

//                        g.setColor(Color.GREEN);
//                        g.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    private void drawPauseOverlay(Graphics2D g) {
        // Semi-transparent dark overlay
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, GameView.WIDTH, GameView.HEIGHT);

        // "PAUSED" text
        g.setColor(Color.WHITE);
        g.setFont(new Font("monospaced", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String pausedText = "PAUSED";
        int textWidth = fm.stringWidth(pausedText);
        int textX = (GameView.WIDTH - textWidth) / 2;
        int textY = GameView.HEIGHT / 2;
        g.drawString(pausedText, textX, textY);

        // Instructions
        g.setFont(new Font("monospaced", Font.PLAIN, 16));
        fm = g.getFontMetrics();
        String instruction = "Press ESC to resume";
        textWidth = fm.stringWidth(instruction);
        textX = (GameView.WIDTH - textWidth) / 2;
        g.drawString(instruction, textX, textY + 40);
    }
}
