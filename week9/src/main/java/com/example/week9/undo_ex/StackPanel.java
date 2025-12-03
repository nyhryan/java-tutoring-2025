package com.example.week9.undo_ex;

import javax.swing.*;
import java.awt.*;

public class StackPanel extends JPanel {
    private static class DisabledItemSelectionModel extends DefaultListSelectionModel {
        @Override
        public void setSelectionInterval(int index0, int index1) {
            super.setSelectionInterval(-1, -1);
        }
    }

    private final DefaultListModel<String> undoListModel = new DefaultListModel<>();
    private final DefaultListModel<String> redoListModel = new DefaultListModel<>();

    public StackPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Command History"));

        var undoLabel = new JLabel("Undo Stack");
        var undoList = new JList<>(undoListModel);
        undoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        undoList.setSelectionModel(new DisabledItemSelectionModel());
        var undoScrollPane = new JScrollPane(undoList);
        undoScrollPane.setPreferredSize(new Dimension(200, 200));

        var redoLabel = new JLabel("Redo Stack");
        var redoList = new JList<>(redoListModel);
        redoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        redoList.setSelectionModel(new DisabledItemSelectionModel());
        var redoScrollPane = new JScrollPane(redoList);
        redoScrollPane.setPreferredSize(new Dimension(200, 200));

        add(undoLabel);
        add(undoScrollPane);
        add(Box.createVerticalStrut(10));
        add(redoLabel);
        add(redoScrollPane);
    }

    /**
     * Undo/Redo 매니저의 상태를 반영하여 스택 패널을 갱신
     * @param manager UndoRedoManager 인스턴스
     */
    public void updateStacks(UndoRedoManager manager) {
        undoListModel.clear();
        for (String cmd : manager.getUndoCommands()) {
            undoListModel.addElement(cmd);
        }

        redoListModel.clear();
        for (String cmd : manager.getRedoCommands()) {
            redoListModel.addElement(cmd);
        }
    }
}
