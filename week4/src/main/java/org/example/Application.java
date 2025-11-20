package org.example;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Application extends JFrame {

    private final WordLabelManager wordLabelManager;
    private final SouthPanel southPanel;
    private final NorthPanel northPanel;
    private final CenterPanel centerPanel;

    public WordLabelManager getWordLabelManager() {
        return wordLabelManager;
    }

    public SouthPanel getSouthPanel() {
        return southPanel;
    }

    public NorthPanel getNorthPanel() {
        return northPanel;
    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    public Application() {
        this.wordLabelManager = new WordLabelManager(this);
        wordLabelManager.generateLabels();

        setTitle("open challenge 9");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(false);

        this.southPanel = new SouthPanel(this);
        this.northPanel = new NorthPanel(this);
        this.centerPanel = new CenterPanel(this);

        var c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(northPanel, BorderLayout.NORTH);
        c.add(southPanel, BorderLayout.SOUTH);
        c.add(centerPanel, BorderLayout.CENTER);
    }
}

class NorthPanel extends JPanel {

    private final JButton newTextButton =  new JButton("New Text");
    private final Application application;

    public NorthPanel(Application application) {
        this.application = application;

        setBackground(Color.GRAY);

        add(new JLabel("단어 조합 게임! 순서대로 단어를 클릭하세요!~"));
        add(newTextButton);
    }
}

class SouthPanel extends JPanel {

    private final JTextField nameTextField = new JTextField(16);
    private final Application application;

    public SouthPanel(Application application) {
        this.application = application;
        setBackground(Color.YELLOW);
    }

    public void addNewWord(JLabel l) {
        System.out.println("Adding new label to south panel");
        add(l);
        revalidate();
        repaint();
    }
}

class CenterPanel extends JPanel {

    private final WordLabelManager wordLabelManager;
    private final Application application;

    public CenterPanel(Application application) {
        this.application = application;
        this.wordLabelManager = application.getWordLabelManager();

        setBackground(Color.WHITE);
        setLayout(null);

        List<JLabel> wordLabels = wordLabelManager.getWordLabels();
        wordLabels.forEach((JLabel wordLabel) -> this.add(wordLabel));
    }
}

class WordLabelManager {

    class WordLabelListener extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel clickedLabel = (JLabel) e.getSource();

            System.out.println("Clicked : " + clickedLabel.getText());
            clickedLabel.setForeground(Color.RED);

            var l = new JLabel(clickedLabel.getText());
            l.setSize(100, 20);
            application.getSouthPanel().addNewWord(l);
        }
    }

    private final Application application;
    private final List<JLabel> wordLabels = new ArrayList<>();
    private final String sentence = "I can't help falling in love with you";
    private final WordLabelListener listener = new WordLabelListener();

    public WordLabelManager(Application application) {
        this.application =  application;
    }

    public List<JLabel> getWordLabels() {
        return wordLabels;
    }

    public void generateLabels() {
        String[] words = sentence.split(" ");
        for (var word : words) {
            JLabel l = createWordLabel(word);
            wordLabels.add(l);
        }
    }

    private JLabel createWordLabel(String word) {
        var label = new JLabel(word);
        int x = (int) (Math.random() * 350); // 0 ~ 350
        int y = (int) (Math.random() * 180); // 0 ~ 180
        label.setLocation(x, y);

        label.setSize(100, 20);
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createLineBorder(Color.RED));

        label.addMouseListener(listener);

        return label;
    }
}