package com.example.week9.undo_ex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WordApp extends JFrame {
    private final UndoRedoManager undoRedoManager = new UndoRedoManager();

    private final StackPanel stackPanel = new StackPanel();

    private final JButton undoButton = new JButton("Undo");
    private final JButton redoButton = new JButton("Redo");

    public WordApp() {
        super("Word application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 명령이 실행될 때 UI를 갱신하는 콜백함수를 매니저에 등록
        undoRedoManager.addOnExecuteCallback(this::updateComponents);

        var wordPanel = new WordPanel(undoRedoManager);
        undoButton.addActionListener(this::undoButtonActionPerformed);
        redoButton.addActionListener(this::redoButtonActionPerformed);

        var buttonPanel = new JPanel();
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        var mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(wordPanel, BorderLayout.CENTER);
        mainContentPanel.add(buttonPanel, BorderLayout.SOUTH);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainContentPanel, stackPanel);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        updateComponents();

        setPreferredSize(new Dimension(600, 500));
        pack();
        setVisible(true);
    }

    // undo 버튼 클릭 시 호출되는 메서드
    private void undoButtonActionPerformed(ActionEvent e) {
        undoRedoManager.undo();
        updateComponents();
    }

    // redo 버튼 클릭 시 호출되는 메서드
    private void redoButtonActionPerformed(ActionEvent e) {
        undoRedoManager.redo();
        updateComponents();
    }

    // UI 컴포넌트들의 상태를 갱신하는 메서드
    private void updateComponents() {
        // undo, redo 버튼의 활성화 여부는 매니저의 상태에 따라 결정
        undoButton.setEnabled(undoRedoManager.canUndo());
        redoButton.setEnabled(undoRedoManager.canRedo());

        // 스택 패널의 상태 갱신
        stackPanel.updateStacks(undoRedoManager);
    }
}