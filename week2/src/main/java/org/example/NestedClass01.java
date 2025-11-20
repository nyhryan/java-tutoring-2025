package org.example;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class NestedClass01 {

    public static void main(String[] args) {
        setTheme();
        SwingUtilities.invokeLater(() -> {
            new AppFrame().setVisible(true);
        });
    }

    private static void setTheme() {
        try {
            for (var info: UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class AppFrame extends JFrame {

    private final JLabel label = new JLabel();
    private final JButton button = new JButton("부자되기");
    private final Random random = new Random();
    private final String labelText = "통장 잔고: ₩ ";
    private int money = 10000;

    public AppFrame() {
        super();

        // 메인 창 설정
        setVisible(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("내 통장");
        setSize(new Dimension(400, 200));
        setLocationRelativeTo(null);

        // 버튼, 라벨 등을 부착할 판때기 꺼내오기
        var contentPane = getContentPane();
        // 판때기의 레이아웃 방식 설정
        contentPane.setLayout(new BorderLayout(5, 5));

        // 라벨 "통장 잔고"를 중앙에 붙이기
        contentPane.add(label, BorderLayout.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText(labelText + money);

        final Font font = new Font(Font.SERIF, Font.BOLD, 20);
        label.setFont(font);

        // 부자되는 버튼은 아래에 붙이기
        var buttonPanel = new JPanel();
        buttonPanel.add(button);
        button.setFont(font);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);

        // 버튼을 눌렀을 때...
        button.addActionListener(new ActionListener() { // Inner class (Anonymous class)
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();

                int randomMoney = random.nextInt(1, 10) * 1000;
                AppFrame.this.money += randomMoney;
                label.setText(labelText + money);
            }
        });
    }

    private AudioInputStream loadAudio() {
        try {
            InputStream is = AppFrame.class.getResourceAsStream("/coin.wav");
            if (is == null) {
                throw new RuntimeException("coin.wav not found");
            }

            return AudioSystem.getAudioInputStream(is);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void playSound() {
        var audioPlayThread = new Thread(() -> {
            try {
                var clip = AudioSystem.getClip();
                clip.open(loadAudio());
                clip.start();
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        audioPlayThread.start();
    }
}