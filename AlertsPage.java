package com.elephant.alerts;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class AlertsPage extends JPanel {

    private AlertsTableModel model;
    private JTable table;

    public AlertsPage() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF4F6F9));

        JLabel title = new JLabel("Alerts & Incident Timeline");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));


        AlertDAO dao = new AlertDAO();
        List<Alert> alerts = dao.findAllLatest(100);

        model = new AlertsTableModel(alerts);
        table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);


        TableRowSorter<AlertsTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(table);

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}
