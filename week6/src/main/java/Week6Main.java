import javax.swing.*;
import java.awt.*;

public class Week6Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}

class App extends JFrame {
    private final int initialTime = 1000;
    private int currentTime;
    private final JLabel timeLabel;

    public App() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var button = new JButton("Start");
        timeLabel = new JLabel("Time: %.2f s".formatted(initialTime / 1000.0f));

        var timer = new Timer(10, e -> {
            System.out.println("현재 스레드: "+Thread.currentThread().getName());
            currentTime -= 10;
            timeLabel.setText("Time: %.2f s".formatted(currentTime / 1000.0f));
            if (currentTime <= 0) {
                ((Timer) e.getSource()).stop();
                button.setEnabled(true);
            }
        });

        button.addActionListener(e -> {
            ((JButton) e.getSource()).setEnabled(false);
            currentTime = initialTime;
            timer.start();
        });
        setLayout(new GridLayout(2, 1));
        add(button);
        add(timeLabel);
        setPreferredSize(new Dimension(100, 100));
        pack();
    }
}
