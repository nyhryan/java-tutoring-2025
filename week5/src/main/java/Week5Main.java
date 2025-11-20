import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

public class Week5Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordApp().setVisible(true));
    }
}

class WordApp extends JFrame {
    private final WordListPanel wordListPanel = new WordListPanel();
    private final WordManager wordManager = new WordManagerImpl();
//    private final WordManager wordManager = new FakeWordManager();

    public WordApp() {
        setTitle("단어장");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setVisible(false);
        setLayout(new BorderLayout());

        var c = getContentPane();

        c.add(wordListPanel, BorderLayout.CENTER);

        var northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        var textField = new JTextField(60);
        // 텍스트 필드에 단어를 치고 엔터키를 누르면 wordListPanel에 단어 추가
        textField.addActionListener(e -> {
            // 1. JTextField의 단어 읽어오기
            String currentWord = textField.getText();
            // 2. 읽어온 단어를 wordListPanel.addWord()에 넘기기
            wordListPanel.addWord(currentWord);
            // 3. JTextField 문자 지우기
            textField.setText("");
        });
        northPanel.add(textField);

        var buttonPanel = new JPanel();

        var addButton = new JButton("add");
        // 텍스트 필드에 단어를 치고 add 버튼을 누르면 wordListPanel에 단어 추가
        addButton.addActionListener(e -> {
            // 1. JTextField의 단어 읽어오기
            String currentWord = textField.getText();
            // 2. 읽어온 단어를 wordListPanel.addWord()에 넘기기
            wordListPanel.addWord(currentWord);
            // 3. JTextField 문자 지우기
            textField.setText("");
        });

        buttonPanel.add(addButton);

        var saveButton = new JButton("save");
        // save 버튼을 누르면 현재 프로젝트 폴더에 words.txt로 단어들 저장
        saveButton.addActionListener(e -> {
            var path = System.getProperty("user.dir");
            var filename = "word.txt";
            wordManager.saveWords(Path.of(path, filename),  wordListPanel.getWords());
        });
        buttonPanel.add(saveButton);
        northPanel.add(buttonPanel);

        c.add(northPanel, BorderLayout.NORTH);

        // 창이 처음 뜨면 현재 프로젝트 폴더의 words.txt를 읽어서 wordListPanel에 띄우기
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                var path = System.getProperty("user.dir");
                var filename = "word.txt";
                List<String> words = wordManager.loadWords(Path.of(path, filename));
                System.out.println("Loaded words: "+ words.size() + "개");
                wordListPanel.setWords(new Vector<>(words));
            }
        });
    }
}

interface WordManager {
    void saveWords(Path path, List<String> words);

    List<String> loadWords(Path path);
}

class FakeWordManager implements WordManager {

    @Override
    public void saveWords(Path path, List<String> words) {
        System.out.printf("단어 %d개를 저장하였습니다.\n", words.size());
        System.out.println("저장한 단어 파일 경로: " + path);
    }

    @Override
    public List<String> loadWords(Path path) {
        System.out.println("읽을 단어 파일 경로: " + path);
        return List.of("Apple", "Banana", "Carrot");
    }
}

class WordManagerImpl implements WordManager {

    @Override
    public void saveWords(Path path, List<String> words) {
        try (var bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            String wordsConcat = String.join("\n", words);
            bufferedWriter.write(wordsConcat);
            System.out.printf("%s에 성공적으로 저장하였습니다\n", path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> loadWords(Path path) {
        try (var bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return bufferedReader.lines().toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class WordListPanel extends JPanel {
    private Vector<String> words = new Vector<>();
    private JList<String> wordList = new JList<>(words);

    public WordListPanel() {
        setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(wordList));
    }

    public Vector<String> getWords() {
        return words;
    }

    public void setWords(Vector<String> words) {
        this.words = words;
        wordList.setListData(words);
        repaint();
        revalidate();
    }

    public void addWord(String word) {
        if (word.isBlank()) {
            return;
        }

        word = word.trim();
        words.add(word);
        wordList.setListData(words);
    }
}