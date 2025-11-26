package org.week8.font_ex.component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FontSizeSlider extends JPanel {

    public interface FontSizeListener {
        /**
         * 폰트 사이즈가 변경될 때 실행할 콜백 함수
         * @param newSize 변경된 폰트 사이즈
         */
        void OnFontSizeChanged(int newSize);
    }

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 32;
    private int currentSize = 12;
    private final JLabel fontSizeLabel = new JLabel("Size: " + currentSize);
    private final JSlider slider = new JSlider(MIN_SIZE, MAX_SIZE, currentSize);

    // 폰트 사이즈가 변경될 때 실행될 콜백 함수들
    private final List<FontSizeListener> listeners = new ArrayList<>();

    public FontSizeSlider() {
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setSnapToTicks(true);
        // JSlider 값이 변경되었을 때 실행되는 리스너 메서드
        slider.addChangeListener(e -> {
            // 슬라이더의 현재 값 가져오기
            currentSize = slider.getModel().getValue();

            // 라벨 텍스트 업데이트 및 리스너들에게 폰트 사이즈 변경 알림
            fontSizeLabel.setText("Size: " + currentSize);
            listeners.forEach(l -> l.OnFontSizeChanged(currentSize)); // 콜백 함수들 호출
        });
        add(slider);
        add(fontSizeLabel);
    }

    public void addFontSizeListener(FontSizeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeFontsizeListener(FontSizeListener listener) {
        listeners.remove(listener);
    }
}
