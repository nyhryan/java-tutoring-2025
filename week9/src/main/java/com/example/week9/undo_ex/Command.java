package com.example.week9.undo_ex;

import javax.swing.*;
import java.util.Collections;

/**
 * Command pattern을 구현한 명령 인터페이스.<br/>
 * JDK 17+에 도입된 sealed interface를 사용하여
 * 구현할 수 있는 클래스들을 제한(permits)함 - AddWord, RemoveWord, ClearWords 3가지
 */
public sealed interface Command permits AddWordCommand, RemoveWordCommand, ClearWordsCommand {
    // 수행할 명령
    void execute();
    // 수행할 명령을 실행 취소
    void undo();
}

/**
 * 단어를 추가하는 명령
 * @param words     추가할 단어
 * @param wordToAdd 단어를 추가할 리스트
 */
record AddWordCommand(
        DefaultListModel<String> words,
        String wordToAdd
) implements Command {
    @Override
    public void execute() {
        // 단어를 추가하는 명령의 로직
        words.addElement(wordToAdd);
    }

    @Override
    public void undo() {
        // 단어를 추가하는 것을 실행 취소하는 로직
        words.removeElement(wordToAdd);
    }
}

/**
 * 단어를 제거하는 명령
 * @param words         단어를 제거할 리스트
 * @param wordToRemove  제거할 단어
 */
record RemoveWordCommand(
        DefaultListModel<String> words,
        String wordToRemove
) implements Command {
    @Override
    public void execute() {
        words.removeElement(wordToRemove);
    }

    @Override
    public void undo() {
        words.addElement(wordToRemove);
    }
}

/**
 * 모든 단어를 제거하는 명령
 * @param words         단어를 제거할 리스트
 * @param backupWords   제거된 단어를 백업할 리스트
 */
record ClearWordsCommand(
        DefaultListModel<String> words,
        DefaultListModel<String> backupWords
) implements Command {
    @Override
    public void execute() {
        backupWords.addAll(Collections.list(words.elements()));
        words.clear();
    }

    @Override
    public void undo() {
        words.addAll(Collections.list(backupWords.elements()));
        backupWords.clear();
    }
}