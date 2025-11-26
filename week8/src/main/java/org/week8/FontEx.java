package org.week8;

import org.week8.component.FontSizeSlider;
import org.week8.component.FlyingBoxPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class FontEx {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FontApp::new);
    }
}

class FontApp extends JFrame {
    // 부착할 컴포넌트들
    private final JComboBox<String> fontComboBox;                                   // 폰트 목록 콤보박스
    private final FontSizeSlider fontSizeSlider  = new FontSizeSlider();            // 폰트 크기 슬라이더
    private final JLabel fontPreviewLabel = new JLabel("Hello World! 헬로 월드!");  // 폰트 프리뷰 라벨
    private final FlyingBoxPanel flyingBoxPanel;                                   // 폰트 테스트 패널

    // 앱의 상태
    private String currentFontName;     // 현재 선택된 폰트 이름
    private int currentFontSize = 12;   // 현재 선택된 폰트 크기

    public FontApp() {
        super("Font App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 시스템에 설치된 폰트 목록 읽어오기
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        currentFontName = fontNames[0];
        fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.setSelectedIndex(0);

        // 폰트 설정 변경 패널 설정
        var fontPanel = new JPanel();
        fontPanel.setLayout(new BoxLayout(fontPanel, BoxLayout.Y_AXIS));
        fontPanel.setBorder(BorderFactory.createTitledBorder("Font Size / 폰트 설정"));
        fontPanel.add(fontComboBox);
        fontPanel.add(fontSizeSlider);
        add(BorderLayout.NORTH, fontPanel);

        // 폰트 프리뷰 라벨 설정
        fontPreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        add(BorderLayout.CENTER, fontPreviewLabel);

        // FlyingBoxPanel 설정
        flyingBoxPanel = new FlyingBoxPanel(makeFont());
        add(BorderLayout.SOUTH, flyingBoxPanel);

        // 폰트 목록 콤보박스의 이벤트 리스너
        fontComboBox.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                return;
            }

            currentFontName = (String) fontComboBox.getSelectedItem();
            updateFont();
        });

        // 폰트 사이즈 슬라이더의 이벤트 리스너
        fontSizeSlider.addFontSizeListener(new FontSizeSlider.FontSizeListener() {
            @Override
            public void OnFontSizeChanged(int size) {
                currentFontSize = size;
                FontApp.this.updateFont();
            }
        });

        updateFont();
        setPreferredSize(new Dimension(600, 600));
        pack();
        setVisible(true);
    }

    public Font makeFont() { return new Font(currentFontName, Font.PLAIN, currentFontSize); }

    /**
     * 현재 선택된 폰트 이름과 크기로 폰트를 생성하여
     * 폰트 프리뷰 라벨과 FlyingBoxPanel에 적용한다.
     */
    private void updateFont() {
        Font newFont = makeFont();
        fontPreviewLabel.setFont(newFont);
        flyingBoxPanel.setDrawingFont(newFont);
    }
}
