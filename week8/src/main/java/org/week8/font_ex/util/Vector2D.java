package org.week8.font_ex.util;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 벡터의 길이를 1로 정규화합니다.
     * @return 정규화된 벡터
     */
    public Vector2D normalize() {
        float length = (float) Math.sqrt(x * x + y * y);
        if (length != 0) {
            return new Vector2D(x / length, y / length);
        }
        else {
            return new Vector2D(0, 0);
        }
    }
}
