package com.example.week9.undo_ex;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class UndoRedoManager {
    private final Deque<Command> undoStack = new LinkedList<>();
    private final Deque<Command> redoStack = new LinkedList<>();

    // undo, redo 실행 후 호출할 콜백 함수
    private final List<Runnable> onExecuteCallbacks = new ArrayList<>();

    // 명령어 실행 - undo, redo, clear 명령 셋 중 하나
    public void executeCommand(Command command) {
        // 1. 전달받은 명령을 실행
        command.execute();

        // 2. undo 스택에 명령을 추가
        undoStack.push(command);

        // 3. redo 스택은 초기화
        redoStack.clear();

        // 4. 콜백함수가 있다면 실행
        onExecuteCallbacks.forEach(callback -> callback.run());
    }

    public void addOnExecuteCallback(Runnable callback) {
        if (!onExecuteCallbacks.contains(callback)) {
            onExecuteCallbacks.add(callback);
        }
    }

    public void removeOnExecuteCallback(Runnable callback) {
        onExecuteCallbacks.remove(callback);
    }

    // 명령 실행 취소
    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }

        // undo 스택에서 명령을 꺼내어 실행 취소
        Command command = undoStack.pop();
        command.undo();

        // redo 스택에 명령을 추가
        redoStack.push(command);
    }

    // 명령 재실행
    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }

        // redo 스택에서 명령을 꺼내어 다시 실행
        Command command = redoStack.pop();
        command.execute();

        // undo 스택에 명령을 추가
        undoStack.push(command);
    }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }

    public List<String> getUndoCommands() {
        return undoStack.stream()
                .map(this::commandToString)
                .toList();
    }

    public List<String> getRedoCommands() {
        return redoStack.stream()
                .map(this::commandToString)
                .toList();
    }

    /**
     * 명령을 포맷된 문자열로 변환
     * @param command 변환할 명령
     * @return 포맷된 문자열
     */
    private String commandToString(Command command) {
        String className = command.getClass().getSimpleName();
        return switch (command) {
            case AddWordCommand cmd -> className + ": " + cmd.wordToAdd();
            case RemoveWordCommand cmd -> className + ": " + cmd.wordToRemove();
            case ClearWordsCommand ignored -> className;
        };
    }
}
