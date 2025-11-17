package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ElephantCard extends JPanel {

    private final Elephant elephant;
    private final Color normalBg = new Color(0xDFF5E1);
    private final Color hoverBg  = new Color(0xC5EBD0);
    private Color currentBg = normalBg;

    public ElephantCard(Elephant elephant) {
        this.elephant = elephant;

        setLayout(new BorderLayout(10, 0));
        setPreferredSize(new Dimension(260, 110));
        setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        setOpaque(false); // we draw rounded background ourselves

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        JLabel avatarLabel = new JLabel();
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setVerticalAlignment(SwingConstants.CENTER);
        avatarLabel.setPreferredSize(new Dimension(70, 70));


        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource("/icons/elephant.png"));
        } catch (Exception ignored) {}

        if (icon != null && icon.getIconWidth() > 0) {
            // scale nicely
            Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            avatarLabel.setIcon(new ImageIcon(scaled));
        } else {
            // Fallback: emoji if image not found
            avatarLabel.setText("🐘");
            avatarLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        }

        JPanel avatarWrapper = new JPanel(new GridBagLayout());
        avatarWrapper.setOpaque(false);
        avatarWrapper.add(avatarLabel);


        JLabel nameLabel = new JLabel(elephant.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(new Color(0x1B5E20));

        JLabel statusLabel = new JLabel("Status: " + elephant.getStatus());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(0x33691E));

        JLabel locationLabel = new JLabel("Location: " + elephant.getLocation());
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        locationLabel.setForeground(new Color(0x4E342E));


        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(statusLabel);
        textPanel.add(locationLabel);


        JLabel badge = new JLabel(elephant.getStatus().toUpperCase());
        badge.setOpaque(true);
        badge.setBackground(new Color(0x2E7D32));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));


        JLabel hint = new JLabel("View details");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hint.setForeground(new Color(0x558B2F));

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(badge);
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(hint);
        rightPanel.add(Box.createVerticalGlue());


        add(avatarWrapper, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentBg = hoverBg;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentBg = normalBg;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() ->
                        new ElephantDetailsFrame(elephant).setVisible(true)
                );
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 20;
            int shadowOffset = 3;


            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(shadowOffset, shadowOffset,
                    getWidth() - shadowOffset * 2,
                    getHeight() - shadowOffset * 2,
                    arc, arc);


            g2.setColor(currentBg);
            g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc);
        } finally {
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
