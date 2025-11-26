package org.week8.net_ex.component;

import org.week8.net_ex.util.EmojiUtil;
import org.week8.net_ex.util.GitHubUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

public class UserPanel extends JPanel {
    private final JLabel avatar;
    private final JLabel username;
    private final JLabel githubUrl;
    private final JLabel publicRepos;
    private final JLabel createdAt;

    public UserPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(250, 250, 250));

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new BorderLayout());
        avatarPanel.setBackground(Color.WHITE);
        avatarPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        avatar = new JLabel();
        avatar.setHorizontalAlignment(JLabel.CENTER);
        avatar.setBorder(new LineBorder(new Color(200, 200, 200), 2));
        avatarPanel.add(avatar, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        username = new JLabel();
        username.setFont(new Font("Segoe UI", Font.BOLD, 28));
        username.setForeground(new Color(36, 41, 46));
        username.setAlignmentX(Component.LEFT_ALIGNMENT);

        githubUrl = new JLabel();
        githubUrl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        githubUrl.setForeground(new Color(3, 102, 214));
        githubUrl.setAlignmentX(Component.LEFT_ALIGNMENT);

        publicRepos = new JLabel();
        publicRepos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        publicRepos.setForeground(new Color(88, 96, 105));
        publicRepos.setAlignmentX(Component.LEFT_ALIGNMENT);

        createdAt = new JLabel();
        createdAt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        createdAt.setForeground(new Color(88, 96, 105));
        createdAt.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(username);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(githubUrl);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(publicRepos);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(createdAt);
        infoPanel.add(Box.createVerticalGlue());

        add(avatarPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);

        setVisible(false);
    }

    /**
     * 사용자 정보를 현재 패널에 표시합니다.
     * @param user 표시할 GitHub 사용자 정보 객체
     */
    public void showUser(GitHubUser user) {
        username.setText(user.login());

        githubUrl.setIcon(EmojiUtil.loadEmojiAsIcon("link.png", new Dimension(32, 32)));
        githubUrl.setText(user.htmlUrl());
        githubUrl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Remove old listeners
        for (MouseListener ml : githubUrl.getMouseListeners()) {
            githubUrl.removeMouseListener(ml);
        }

        // 프로필 URL을 클릭하면 기본 웹 브라우저에서 열리도록 설정 (마우스 클릭 이벤트 리스너)
        githubUrl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(URI.create(user.htmlUrl()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        publicRepos.setIcon(EmojiUtil.loadEmojiAsIcon("package.png", new Dimension(32, 32)));
        publicRepos.setText("Public Repositories: " + user.publicRepos());

        if (user.createdAt() != null && !user.createdAt().isEmpty()) {
            String date = user.createdAt().substring(0, 10);
            createdAt.setIcon(EmojiUtil.loadEmojiAsIcon("calendar.png", new Dimension(32, 32)));
            createdAt.setText("Joined: " + date);
        }

        // 아바타 URL을 이용해 아바타 이미지를 불러와 표시
        try {
            var url = URI.create(user.avatarUrl()).toURL();
            var imgIcon = new ImageIcon(url);
            var scaledIcon = new ImageIcon(imgIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
            avatar.setIcon(scaledIcon);
        } catch (Exception e) {
            avatar.setIcon(null);
        }

        setVisible(true);
        revalidate(); // Layout 갱신
        repaint();    // 다시 그리기
    }
}
