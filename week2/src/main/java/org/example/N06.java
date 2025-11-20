package org.example;

public class N06 {
    public static void main(String[] args) {
        PositivePoint p = new PositivePoint(10, 10);
        p.move(5, 5);
        System.out.println(p.toString() + "입니다.");

        p.move(2, -2);
        System.out.println(p.toString() + "입니다.");

        PositivePoint q = new PositivePoint(-10 , 10);
        System.out.println(q.toString() + "입니다.");
    }
}

class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class PositivePoint extends Point {
    public PositivePoint(int x, int y) {
        super(x, y);

        if (x <= 0 || y <= 0) {
            move(1, 1);
        }
    }

    @Override
    protected void move(int x, int y) {
        if (x <= 0 || y <= 0) {
            return;
        }

        super.move(x, y);
    }

    @Override
    public String toString() {
        return "(%d, %d)의 점".formatted(getX(), getY());
    }
}