package week7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

public class LabelEx extends JFrame {
    private final MyLabel bar = new MyLabel(100);

    public LabelEx(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        bar.setBackground(Color.ORANGE);
        bar.setOpaque(true);
        bar.setLocation(20, 50);
        bar.setSize(new Dimension(300, 20));
        add(bar);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                new Thread(bar::fill).start();
                bar.fill();
            }
        });

        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(350, 300));
        setVisible(true);
        setFocusable(true);
        requestFocus();
        pack();

        new Thread(new ConsumerThread(bar), "Consumer-Thread").start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LabelEx("아무거나 빨리 눌러 바 채우기"));
    }
}

class MyLabel extends JLabel {
    private int currentSize = 0;
    private final int maxSize;

    public MyLabel(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = Math.round(1.0f * currentSize * getWidth() / maxSize);
        if (w == 0) return;
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, w, getHeight());
        g.setColor(Color.BLACK);
        g.drawString("current: %d".formatted(w), 0, getHeight());
    }

    synchronized public void fill() {
        while (currentSize == maxSize) {
            try {
                wait(); // 호출된 week6.MyLabel 객체에 대한 락을 해제, 대기 상태로 진입
            } catch (InterruptedException e) {}
            // 다른 스레드에서 currentSize를 줄이고 notify()를 호출하면 대기 상태에서 벗어나고,
            // wait() 호출 직전의 상태로 복원됨 (락 획득)
            finally {
                System.out.printf("[%s] now filling more%n", Thread.currentThread().getName());
            }
        }

        currentSize++;
        SwingUtilities.invokeLater(this::repaint);
        notify();
    }

    synchronized public void clear() {
        while (currentSize == 0) {
            try {
                wait(); // 호출된 week6.MyLabel 객체에 대한 락을 해제, 대기 상태로 진입
            } catch (InterruptedException e) {}
            // 다른 스레드에서 currentSize를 늘리고 notify()를 호출하면 대기 상태에서 벗어나고,
            // wait() 호출 직전의 상태로 복원됨 (락 획득)
            finally {
                System.out.printf("[%s] now clearing more%n", Thread.currentThread().getName());
            }
        }

        currentSize--;
        SwingUtilities.invokeLater(this::repaint);
        notify();
    }
}

class ConsumerThread implements Runnable {
    private final MyLabel bar;

    public ConsumerThread(MyLabel bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        while (true) {
            try {
                bar.clear();
                sleep(200);
            } catch (InterruptedException e) {}
        }
    }
}