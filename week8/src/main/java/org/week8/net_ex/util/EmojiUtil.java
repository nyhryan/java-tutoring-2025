package org.week8.net_ex.util;

import javax.swing.*;
import java.awt.*;

public class EmojiUtil {
    /**
     * {@code src/main/resources/icons} 안의 이미지 파일을 읽어서 지정된 크기로 조정한 후 ImageIcon으로 반환하는 유틸리티 메서드
     * @param filename 이미지 파일 이름
     * @param size 조정할 크기
     * @return {@code size} 크기로 조정된 이미지 파일의 ImageIcon 객체
     */
    public static ImageIcon loadEmojiAsIcon(String filename, Dimension size) {
        // src/main/resources/icons/ 안에 있는 이미지 파일 읽기
        var url = EmojiUtil.class.getResource("/icons/" + filename);
        if (url == null) {
            System.err.println("EmojiUtil loadEmojiAsIcon: Resource not found: " + filename);
            return null;
        }

        // 이미지 파일을 읽어서 크기 조정 후 ImageIcon으로 반환
        ImageIcon icon = new ImageIcon(url);
        Image scaledImage = icon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
