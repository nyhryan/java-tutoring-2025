package com.example.week9.font_ex;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExternalFont_Ex {
    public static void main(String[] args) {
        // 기본 Look and Feel 설정 (만일에 대비한 기본값 초기화)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 글로벌 폰트 초기화
        FontHelper.initializeGlobalFonts();

        SwingUtilities.invokeLater(() -> {
            var frame = new JFrame("Noto Sans Font Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            var label = new JLabel("Hello, Noto Sans! 안녕하세요! こんにちは", SwingConstants.CENTER);

            // 글로벌 폰트가 등록된 상태에서
            // deriveFont메서드를 통해 폰트 크기만 변경하여 설정
            label.setFont(label.getFont().deriveFont(Font.PLAIN, 32f));

            frame.add(label);
            frame.setPreferredSize(new Dimension(800, 600));
            frame.pack();
            frame.setVisible(true);
        });
    }
}

class FontHelper {
    // 폰트 스타일(보통, 볼드체, 이탤릭체 등)을 정의하는 열거형
    enum FontStyle {
        REGULAR("NotoSansKR-Regular"),  // 보통체와 폰트 파일명
        BOLD("NotoSansKR-Bold");        // 볼드체와 폰트 파일명

        private final String fontName; // 폰트 파일명
        FontStyle(String fontName) {
            this.fontName = fontName;
        }
        public String getFontName() {
            return fontName;
        }
    }

    private static final float BASE_FONT_SIZE = 14f;
    // 로드된 폰트를 캐싱하기 위한 맵
    private static final Map<FontStyle, Font> fontCache = new HashMap<>();

    /**
     * 글로벌 폰트를 초기화하고 UIManager에 설정합니다.
     */
    public static void initializeGlobalFonts() {
        // 사용하고 싶은 모든 폰트 스타일에 대해서...
        for (var style : FontStyle.values()) {
            try {
                // 폰트를 로드하고 그래픽 환경에 등록
                Font font = loadAndRegisterFont("/fonts/" + style.getFontName() + ".ttf");
                fontCache.put(style, font);
            } catch (Exception e) {
                System.err.println("Error loading fonts: " + e.getMessage());
            }
        }

        // 로드한 폰트들 중 보통 스타일을 기본 폰트로 설정
        Font baseFont = fontCache.get(FontStyle.REGULAR).deriveFont(BASE_FONT_SIZE);
        setUIFont(baseFont);
    }

    /**
     * 주어진 path로부터 폰트를 로드하고 그래픽 환경에 등록합니다.
     * @param path 폰트 파일의 경로
     * @return 로드된 Font 객체
     */
    private static Font loadAndRegisterFont(String path) throws IOException, FontFormatException {
        try (InputStream is = FontHelper.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Font file not found: " + path);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        }
    }

    /**
     * UIManager를 통해 접근할 수 있는 모든 요소들의 폰트를 주어진 폰트로 설정합니다.
     * @param font 설정할 Font 객체
     */
    private static void setUIFont(Font font) {
        var keys = UIManager.getDefaults().keys();
        // 모든 UI 요소에 대해 폰트를 설정
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
}