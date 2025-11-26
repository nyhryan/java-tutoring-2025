package org.week8.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FontUtility {
    private static final BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D g2d = image.createGraphics();

    /**
     * 주어진 텍스트와 폰트를 사용하여 문자열의 크기를 계산합니다.
     *
     * @param text 크기를 측정할 문자열
     * @param font 사용할 폰트
     * @return 텍스트의 너비와 높이를 포함하는 Dimension 객체
     */
    public static Dimension getStringDimension(String text, Font font) {
        FontMetrics fm = g2d.getFontMetrics(font);
        int w = fm.stringWidth(text);
        int h = fm.getHeight();
        return new Dimension(w, h);
    }
}
