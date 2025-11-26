package org.week8.font_ex.component;

import org.week8.font_ex.util.FontUtility;
import org.week8.font_ex.util.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

public class FlyingBoxPanel extends JPanel {
    private static final int DELTA_TIME = 16; // 화면을 갱신하는 시간 간격(밀리초)

    private int speed = 100;                // 박스의 속도(픽셀/초)
    private Rectangle2D.Float boxBound;     // 박스의 위치, 크기 정보
    private Color boxColor = Color.RED;     // 박스 색상
    private Vector2D currentDirection;      // 박스의 현재 이동 방향 벡터
    private Font drawingFont;               // 그릴 폰트
    private String msg = "Test Panel!";     // 박스 내 메시지
    private Color textColor = Color.BLACK;  // 텍스트 색상

    public FlyingBoxPanel(Font drawingFont) {
        this.drawingFont = drawingFont;

        // msg 문자열을 기반으로 초기 박스 크기 설정
        Dimension dim = FontUtility.getStringDimension(msg, drawingFont);
        int w = dim.width;
        int h = dim.height;
        this.boxBound = new Rectangle2D.Float(0, 0, w, h);

        // 박스가 날라갈 방향을 랜덤으로 결정
        this.currentDirection = new Vector2D(
                ((float) Math.random() - 0.5f) *2f, // -1 ~ 1 사이
                ((float) Math.random() - 0.5f) *2f
        ).normalize();

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(100, 200));

        // 매 DELTA_TIME마다 updateBox 메서드 호출
        new Timer(DELTA_TIME, this::updateBox).start();
    }

    public void setDrawingFont(Font drawingFont) {
        this.drawingFont = drawingFont;

        // 폰트가 바뀌었으므로 박스 크기 다시 계산
        Dimension dim = FontUtility.getStringDimension(msg, drawingFont);
        int w = dim.width;
        int h = dim.height;
        boxBound.setFrame(boxBound.getX(), boxBound.getY(), w, h);

        // 화면 갱신
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        // 현재 폰트와 그릴 문자열을 기반으로 문자열을 감싸는 박스의 크기 계산
        FontMetrics fm = g2d.getFontMetrics(drawingFont);
        int w = fm.stringWidth(msg);
        int h = fm.getHeight();
        // 1. 박스를 먼저 그리고
        g2d.setColor(boxColor);
        g2d.fillRect((int) boxBound.getX(), (int) boxBound.getY(), w + 2, h + 2);
        // 2. 그 위에 텍스트를 그린다.
        g2d.setColor(textColor);
        g2d.setFont(drawingFont);
        g2d.drawString(msg, ((int) boxBound.getX()), ((int) boxBound.getY() + fm.getAscent()));
    }

    private void updateBox(ActionEvent e) {
        // 1.(x, y 방향의 속도 성분 * 박스의 픽셀 속도) * 시간 = 이동 거리를 계산
        var dx = currentDirection.x * speed * DELTA_TIME / 1000;
        //              x 방향      *  px/s *  프레임 시간(s)
        var dy = currentDirection.y * speed * DELTA_TIME / 1000;

        // 2. 박스의 위치를 갱신
        var x = boxBound.getX() + dx;
        var y = boxBound.getY() + dy;

        // 3. 만약 벽에 부딪혔다면 방향을 반대로 바꾼다. (x 축 방향)
        if (x < 0 || x + boxBound.getWidth() > getWidth()) {
            currentDirection = new Vector2D(-currentDirection.x, currentDirection.y).normalize();
            // 박스가 이미 벽을 뚫고 나갔다면 벽 안쪽으로 위치 보정
            x = Math.max(0, Math.min(x, getWidth() - boxBound.getWidth()));
            changeColor(); // 벽에 튕길 때 색상 랜덤으로 변경
        }

        // 3. 만약 벽에 부딪혔다면 방향을 반대로 바꾼다. (y 축 방향)
        if (y < 0 || y + boxBound.getHeight() > getHeight()) {
            currentDirection = new Vector2D(currentDirection.x, -currentDirection.y).normalize();
            y = Math.max(0, Math.min(y, getHeight() - boxBound.getHeight()));
            changeColor();
        }

        // 4. 박스 위치 최종 갱신 및 화면 재렌더링
        boxBound.setFrame(x, y, boxBound.getWidth(), boxBound.getHeight());
        repaint();
    }

    private void changeColor() {
        // 박스 색상을 랜덤 색상으로 변경
        boxColor = new Color(
                (float) Math.random(),
                (float) Math.random(),
                (float) Math.random()
        );

        // 텍스트 색상은 박스 색상의 밝기에 따라 흰색 또는 검은색으로 설정
        textColor = calculateBrightness(boxColor) < 0.5f ? Color.WHITE : Color.BLACK;
    }

    private float calculateBrightness(Color color) {
        // Get RGB values normalized to 0-1
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;

        // Perceived luminance (ITU-R BT.709 standard)
        return 0.2126f * r + 0.7152f * g + 0.0722f * b;
    }
}
