package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DashboardFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel center;

    public DashboardFrame() {
        setTitle("Human–Elephant Conflict System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(0xE5ECEF));


        JPanel topBar = new TopBarPanel();
        topBar.setLayout(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Predictive Early-Warning & Response System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Human–Elephant Conflict Monitoring Dashboard • Sri Lanka");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(0xD7FBE8));

        titleBlock.add(titleLabel);
        titleBlock.add(Box.createVerticalStrut(2));
        titleBlock.add(subtitleLabel);


        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightTop.setOpaque(false);


        JLabel avatar = new JLabel("SD", SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(30, 30));
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        avatar.setForeground(new Color(0x0B6B3A));
        avatar.setOpaque(true);
        avatar.setBackground(Color.WHITE);
        avatar.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        JLabel userLabel = new JLabel("<html><b>Analyst:</b> Sahan Devinda</html>");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userLabel.setForeground(new Color(0xF5FFFA));

        JButton logoutBtn = new JButton("Logout");
        styleTopButton(logoutBtn);

        rightTop.add(avatar);
        rightTop.add(userLabel);
        rightTop.add(logoutBtn);

        topBar.add(titleBlock, BorderLayout.WEST);
        topBar.add(rightTop, BorderLayout.EAST);


        cardLayout = new CardLayout();
        center = new JPanel(cardLayout);
        center.setBackground(new Color(0xF4F6F9));
        center.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));


        JPanel pageElephants = new ActiveElephantsPage();
        JPanel pageAlerts = new AlertsPage();
        JPanel pageRoutes = new SafeRoutesPage();
        JPanel pageSettings = new SettingsPage();

        center.add(pageElephants, "elephants");
        center.add(pageAlerts, "alerts");
        center.add(pageRoutes, "routes");
        center.add(pageSettings, "settings");


        JPanel mainCenter = new JPanel(new BorderLayout());
        mainCenter.setBackground(new Color(0xE5ECEF));
        mainCenter.setBorder(BorderFactory.createEmptyBorder(14, 16, 16, 16));


        JPanel statsRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statsRow.setOpaque(false);

        statsRow.add(createStatCard(
                "Active Elephants",
                "3",
                "/images/elephant_white.png",
                new Color(0x10A35A)
        ));

        statsRow.add(createStatCard(
                "High-Risk Alerts",
                "5",
                "/images/alert_white.png",
                new Color(0xF57C00)
        ));

        statsRow.add(createStatCard(
                "Safe Routes Generated",
                "12",
                "/images/map_white.png",
                new Color(0x1565C0)
        ));

        mainCenter.add(statsRow, BorderLayout.NORTH);
        mainCenter.add(center, BorderLayout.CENTER);

        // ================= SIDE NAVBAR =================
        JPanel sideNav = new JPanel();
        sideNav.setLayout(new BoxLayout(sideNav, BoxLayout.Y_AXIS));
        sideNav.setBackground(new Color(0x06321E)); // deep green
        sideNav.setPreferredSize(new Dimension(240, getHeight()));
        sideNav.setBorder(BorderFactory.createEmptyBorder(18, 16, 18, 16));

        JLabel appTitle = new JLabel("MENU");
        appTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appTitle.setForeground(Color.WHITE);


        sideNav.add(appTitle);
        sideNav.add(Box.createVerticalStrut(14));

        JSeparator sepTop = new JSeparator();
        sepTop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sepTop.setForeground(new Color(0x1C5A3A));
        sideNav.add(sepTop);
        sideNav.add(Box.createVerticalStrut(12));

        sideNav.add(Box.createVerticalStrut(6));

        JButton btnActiveElephants = createNavButton("Active Elephants", true);
        JButton btnAlerts = createNavButton("Alerts Timeline", false);
        JButton btnRoutes = createNavButton("Safe Routes", false);
        JButton btnSettings = createNavButton("System Settings", false);


        btnActiveElephants.addActionListener(e -> {
            highlightSelected(btnActiveElephants, btnAlerts, btnRoutes, btnSettings);
            cardLayout.show(center, "elephants");
        });

        btnAlerts.addActionListener(e -> {
            highlightSelected(btnAlerts, btnActiveElephants, btnRoutes, btnSettings);
            cardLayout.show(center, "alerts");
        });

        btnRoutes.addActionListener(e -> {
            highlightSelected(btnRoutes, btnActiveElephants, btnAlerts, btnSettings);
            cardLayout.show(center, "routes");
        });

        btnSettings.addActionListener(e -> {
            highlightSelected(btnSettings, btnActiveElephants, btnAlerts, btnRoutes);
            cardLayout.show(center, "settings");
        });

        sideNav.add(btnActiveElephants);
        sideNav.add(Box.createVerticalStrut(6));
        sideNav.add(btnAlerts);
        sideNav.add(Box.createVerticalStrut(6));
        sideNav.add(btnRoutes);
        sideNav.add(Box.createVerticalStrut(6));
        sideNav.add(btnSettings);
        sideNav.add(Box.createVerticalGlue());

        JLabel envLabel = new JLabel("<html><span style='color:#78c29a;'>●</span> SENSORS CONNECTED</html>");
        envLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        envLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        envLabel.setForeground(new Color(0xC3EED5));
        sideNav.add(envLabel);


        root.add(topBar, BorderLayout.NORTH);
        root.add(sideNav, BorderLayout.WEST);
        root.add(mainCenter, BorderLayout.CENTER);

        setContentPane(root);
    }


    private JPanel createStatCard(String title, String value, String iconPath, Color accentColor) {
        RoundedPanel card = new RoundedPanel(18);
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));


        JPanel iconHolder = new RoundedPanel(999);
        iconHolder.setPreferredSize(new Dimension(42, 42));
        iconHolder.setBackground(accentColor);
        iconHolder.setLayout(new BorderLayout());

        JLabel iconLabel;
        ImageIcon icon = null;
        if (iconPath != null) {
            try {
                icon = new ImageIcon(getClass().getResource(iconPath));
            } catch (Exception ignored) {}
        }

        if (icon != null) {
            iconLabel = new JLabel(icon);
        } else {

            iconLabel = new JLabel("🐘", SwingConstants.CENTER);
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
            iconLabel.setForeground(Color.WHITE);
        }
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconHolder.add(iconLabel, BorderLayout.CENTER);


        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(new Color(0x666666));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblValue.setForeground(new Color(0x222222));

        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(lblValue);

        card.add(iconHolder, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }


    private JButton createNavButton(String text, boolean selected) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        if (selected) {
            btn.setBackground(new Color(0x10A35A));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(0x0B5531));
            btn.setForeground(new Color(0xE8F4EC));
        }

        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!btn.getBackground().equals(new Color(0x10A35A))) {
                    btn.setBackground(new Color(0x126842));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!btn.getBackground().equals(new Color(0x10A35A))) {
                    btn.setBackground(new Color(0x0B5531));
                }
            }
        });

        return btn;
    }


    private void highlightSelected(JButton selected, JButton... others) {
        selected.setBackground(new Color(0x10A35A));
        selected.setForeground(Color.WHITE);

        for (JButton b : others) {
            b.setBackground(new Color(0x0B5531));
            b.setForeground(new Color(0xE8F4EC));
        }
    }


    private void styleTopButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(new Color(0xF9FAFB));
        btn.setForeground(new Color(0x0B6B3A));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    static void stylePrimaryButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(0x10A35A));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    private static class TopBarPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(0x0B6B3A),
                    w, h, new Color(0x168F56)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }


    private static class RoundedPanel extends JPanel {
        private final int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Shape round = new RoundRectangle2D.Float(
                    0, 0,
                    getWidth(), getHeight(),
                    radius, radius
            );

            g2.setColor(getBackground());
            g2.fill(round);
            g2.dispose();

            super.paintComponent(g);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            new DashboardFrame().setVisible(true);
        });
    }
}
