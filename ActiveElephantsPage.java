package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;

public class ActiveElephantsPage extends JPanel {

    public ActiveElephantsPage() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF4F6F9));


        JPanel filtersBar = new JPanel(new BorderLayout());
        filtersBar.setOpaque(false);
        filtersBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JLabel sectionTitle = new JLabel("Active Elephant Status");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(0x333333));

        JPanel rightFilters = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightFilters.setOpaque(false);

        JTextField searchField = new JTextField(18);
        searchField.putClientProperty("JComponent.roundRect", true);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xC0C0C0)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setToolTipText("Search elephant by ID / name / zone");

        JButton searchBtn = new JButton("Search");
        DashboardFrame.stylePrimaryButton(searchBtn);

        rightFilters.add(searchField);
        rightFilters.add(searchBtn);

        filtersBar.add(sectionTitle, BorderLayout.WEST);
        filtersBar.add(rightFilters, BorderLayout.EAST);

        // --- Cards grid ---
        JPanel cardGrid = new JPanel(new GridLayout(1, 3, 16, 16));
        cardGrid.setOpaque(false);

        // 3 elephants
        Elephant e1 = new Elephant("E-001", "Raja", "Active", "Galoya",      7.743, 81.609);
        Elephant e2 = new Elephant("E-002", "Kawanthissa", "Active", "Maduru Oya", 7.838, 81.299);
        Elephant e3 = new Elephant("E-003", "Gamunu", "Active", "Mahiyanganaya", 7.333, 81.000);

        // Wrap each card so we can add mouse click action
        cardGrid.add(createClickableCard(e1));
        cardGrid.add(createClickableCard(e2));
        cardGrid.add(createClickableCard(e3));

        add(filtersBar, BorderLayout.NORTH);
        add(cardGrid, BorderLayout.CENTER);
    }

    private JPanel createClickableCard(Elephant elephant) {
        ElephantCard card = new ElephantCard(elephant);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(card, BorderLayout.CENTER);

        // Click opens ElephantDetailsFrame for THIS elephant
        container.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        container.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                SwingUtilities.invokeLater(() ->
                        new ElephantDetailsFrame(elephant).setVisible(true)
                );
            }
        });

        return container;
    }
}
