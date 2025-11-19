package week6.game.view;

import lombok.Getter;
import lombok.Setter;
import week6.game.data.WordRepository;
import week6.game.controller.GameController;
import week6.game.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GameView extends JFrame {
    public static int WIDTH = 400;
    public static int HEIGHT = 600;
    public static final Font GAME_FONT = new Font("sansserif", Font.BOLD, 14);

    private static final Map<String, Point2D> widthByWord = new HashMap<>();

    @Getter
    private final InputTextBox inputTextBox = new InputTextBox();

    /**
     * Callback function when pause is toggled
     */
    @Setter
    private Function<Void, Void> onPause;

    public GameView() {
        // Precompute game word dimensions(width, height)
        computeWordDimensions();

        setTitle("Word Falling Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        setupKeyBindings();

        // Window resize callback
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var frame = (JFrame) e.getSource();
                WIDTH = frame.getContentPane().getWidth();
                HEIGHT = frame.getContentPane().getHeight();
            }
        });

        // setup components
        GameModel model = new GameModel();
        GamePanel gamePanel = new GamePanel(model);
        // updates model when there are changes in view
        model.registerObserver(gamePanel);
        add(gamePanel, BorderLayout.CENTER);

        add(inputTextBox, BorderLayout.SOUTH);

        // Start game
        GameController gameController = new GameController(model, this);
        gameController.startTimers();
    }

    public static int getWidthOfWord(String word) {
        if (widthByWord.containsKey(word)) {
            return (int) widthByWord.get(word).getX();
        }
        return 0;
    }

    public static int getHeightOfWord(String word) {
        if (widthByWord.containsKey(word)) {
            return (int) widthByWord.get(word).getY();
        }
        return 0;
    }

    private void setupKeyBindings() {
        var rootPane = getRootPane();
        var inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        var actionMap = rootPane.getActionMap();

        final String TOGGLE_PAUSE_ACTION = "togglePause";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), TOGGLE_PAUSE_ACTION);
        actionMap.put(TOGGLE_PAUSE_ACTION, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onPause != null) {
                    onPause.apply(null);
                }
                inputTextBox.toggleEnable();
            }
        });
    }

    /**
     * GAME_FONT의 크기를 기반으로 단어들의 너비와 높이를 미리 계산하여 저장합니다.
     */
    private void computeWordDimensions() {
        var g2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        {
            g2d.setFont(GAME_FONT);
            FontMetrics metrics = g2d.getFontMetrics();

            // Precompute dimensions for all words in the repository
            for (String word : WordRepository.words) {
                int width = metrics.stringWidth(word);
                int height = metrics.getHeight();
                widthByWord.put(word, new Point2D.Float(width, height));
            }
        }
        g2d.dispose();
    }
}
