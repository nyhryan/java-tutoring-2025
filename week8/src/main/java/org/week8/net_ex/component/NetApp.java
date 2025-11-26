package org.week8.net_ex.component;

import org.week8.net_ex.util.GitHubHttpClient;
import org.week8.net_ex.util.GitHubUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetApp extends JFrame {
    // 백그라운드 작업을 처리할 스레드 풀
    public static final ExecutorService executors = Executors.newCachedThreadPool();

    private final JTextField usernameField = new JTextField(20);
    private final JButton fetchButton = new JButton("Fetch GitHub User");
    private final UserPanel userPanel = new UserPanel();

    public NetApp() {
        super("GitHub User Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchPanel.setBackground(new Color(36, 41, 46));
        searchPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel promptLabel = new JLabel("GitHub Username:");
        promptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        promptLabel.setForeground(Color.WHITE);

        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 32));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 100), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        fetchButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        fetchButton.setPreferredSize(new Dimension(90, 32));
        fetchButton.setBackground(new Color(40, 167, 69));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFocusPainted(false);
        fetchButton.setBorderPainted(false);
        fetchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        searchPanel.add(promptLabel);
        searchPanel.add(usernameField);
        searchPanel.add(fetchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(userPanel, BorderLayout.CENTER);

        add(mainPanel);

        fetchButton.addActionListener(this::handleFetchButton);
        usernameField.addActionListener(this::handleFetchButton);

        setPreferredSize(new Dimension(650, 400));
        pack();
        setVisible(true);
    }

    /**
     * Fetch Button 이벤트 핸들러
     * @param e 이벤트 객체
     */
    private void handleFetchButton(ActionEvent e) {
        // usernameField에 적힌 GitHub 사용자 이름 읽기
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a GitHub username", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // GitHub 사용자 정보 가져오기 준비
        fetchButton.setEnabled(false);
        fetchButton.setText("...");
        userPanel.setVisible(false);

        // 백그라운드 스레드에서 GitHub 사용자 정보를 가져옴 (Swing UI 스레드 블록 방지)
        executors.execute(() -> {
            Optional<GitHubUser> gitHubUser = GitHubHttpClient.fetchUser(username);

            // 읽어온 사용자 정보를 Swing GUI에 보여주기
            SwingUtilities.invokeLater(() -> { // UI 업데이트는 반드시 Swing UI 스레드에서 수행해야 함
                fetchButton.setEnabled(true);
                fetchButton.setText("Search");

                // 사용자를 성공적으로 읽었다면 UserPanel에 보여주기
                if (gitHubUser.isPresent()) {
                    userPanel.showUser(gitHubUser.get());
                }
                // 사용자를 읽지 못했다면 오류 메시지(팝업 다이얼로그 창) 보여주기
                else {
                    JOptionPane.showMessageDialog(this, "User not found: " + username, "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}
