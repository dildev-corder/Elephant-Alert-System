package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;

public class SettingsPage extends JPanel {

    public SettingsPage() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF4F6F9));

        JLabel title = new JLabel("System Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblPolling = new JLabel("Sensor polling interval (seconds):");
        JTextField txtPolling = new JTextField("30", 8);

        JLabel lblThreshold = new JLabel("Risk score alert threshold:");
        JTextField txtThreshold = new JTextField("0.75", 8);

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(lblPolling, gbc);
        gbc.gridx = 1;
        form.add(txtPolling, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(lblThreshold, gbc);
        gbc.gridx = 1;
        form.add(txtThreshold, gbc);

        JButton saveBtn = new JButton("Save Settings");
        DashboardFrame.stylePrimaryButton(saveBtn);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(saveBtn, gbc);

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }
}
