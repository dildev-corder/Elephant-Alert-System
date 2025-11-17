package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JPanel {

    public DashboardPage() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF4F6F9));

        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0x333333));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(title, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 16, 16));
        cardsPanel.setOpaque(false);

        cardsPanel.add(createStatCard("Active Elephants", "7", "Currently tracked"));
        cardsPanel.add(createStatCard("Open Alerts", "3", "Need immediate action"));
        cardsPanel.add(createStatCard("Safe Routes", "5", "Optimized paths available"));

        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value, String subtitle) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(new Color(0x666666));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(new Color(0x0B6B3A));

        JLabel lblSubtitle = new JLabel(subtitle);
        lblSubtitle.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblSubtitle.setForeground(new Color(0x999999));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblSubtitle, BorderLayout.SOUTH);

        return card;
    }
}
