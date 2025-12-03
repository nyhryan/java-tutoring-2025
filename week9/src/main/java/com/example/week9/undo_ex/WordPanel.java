package com.example.week9.undo_ex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WordPanel extends JPanel {
    // 단어의 추가, 제거, 전체 삭제 시 Undo/Redo 기능을 제공하는 매니저
    private final UndoRedoManager undoRedoManager;

    private final JButton addButton = new JButton("Add");
    private final JButton removeButton = new JButton("Remove");
    private final JButton clearButton = new JButton("Clear");

    private final JTextField textArea = new JTextField(20);

    private final DefaultListModel<String> wordListModel = new DefaultListModel<>();
    private final JList<String> wordList = new JList<>(wordListModel);

    public WordPanel(UndoRedoManager undoRedoManager) {
        this.undoRedoManager = undoRedoManager;

        setBorder(BorderFactory.createTitledBorder("Word List Management"));

        addButton.addActionListener(this::addWord);
        removeButton.addActionListener(this::removeWord);
        clearButton.addActionListener(this::clearWords);

        setLayout(new BorderLayout(5, 5));

        var inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        inputPanel.add(new JLabel("Word: "));
        inputPanel.add(textArea);
        inputPanel.add(addButton);

        var actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        actionPanel.add(removeButton);
        actionPanel.add(clearButton);

        var controlsPanel = new JPanel(new GridLayout(2, 1));
        controlsPanel.add(inputPanel);
        controlsPanel.add(actionPanel);
        add(controlsPanel, BorderLayout.NORTH);

        var listScrollPane = new JScrollPane(wordList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Word List"));
        add(listScrollPane, BorderLayout.CENTER);

        wordList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                removeButton.setEnabled(wordList.getSelectedIndex() != -1);
            }
        });
        removeButton.setEnabled(false);
        setPreferredSize(new Dimension(400, 450));
    }

    /**
     * 단어를 입력하고 addButton을 클릭했을 때 호출되는 메서드
     * @param e 액션 이벤트
     */
    private void addWord(ActionEvent e) {
        String word = textArea.getText().trim();
        if (!word.isEmpty()) {
            // 단어를 추가하는 명령을 UndoRedoManager를 통해 실행
            undoRedoManager.executeCommand(new AddWordCommand(wordListModel, word));
            textArea.setText("");
        }
    }

    /**
     * 단어를 선택하고 removeButton을 클릭했을 때 호출되는 메서드
     * @param e 액션 이벤트
     */
    private void removeWord(ActionEvent e) {
        String selectedWord = wordList.getSelectedValue();
        if (selectedWord != null) {
            // 단어를 제거하는 명령을 UndoRedoManager를 통해 실행
            undoRedoManager.executeCommand(new RemoveWordCommand(wordListModel, selectedWord));
        }
    }

    /**
     * clearButton을 클릭했을 때 호출되는 메서드
     * @param e 액션 이벤트
     */
    private void clearWords(ActionEvent e) {
        var backupWords = new DefaultListModel<String>();
        // 단어 목록을 싹 지우는 명령을 UndoRedoManager를 통해 실행
        undoRedoManager.executeCommand( new ClearWordsCommand(wordListModel, backupWords));
    }
}
