package com.elephant.alerts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

public class ElephantDetailsFrame extends JFrame {

    private Elephant elephant;
    private JTable sightingsTable;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private ElephantAlertDAO alertDAO = new ElephantAlertDAO();


    private Graph graph;
    private GraphNode nodeElephant;
    private GraphNode villageA;
    private GraphNode villageB;
    private GraphNode villageC;

    public ElephantDetailsFrame(Elephant elephant) {
        this.elephant = elephant;
        setTitle("Elephant Details - " + elephant.getName());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        graph = new Graph();

        initGraph();
        initUI();
        loadAlertsFromDB();
    }

    private void initGraph() {


        nodeElephant = graph.addNode(
                "ELOC",
                "Elephant Location - " + elephant.getLocation(),
                elephant.getLatitude(),
                elephant.getLongitude()
        );

        villageA = graph.addNode(
                "A",
                "Village A",
                7.743, 81.609
        );

        villageB = graph.addNode(
                "B",
                "Village B",
                7.838, 81.299
        );

        villageC = graph.addNode(
                "C",
                "Village C",
                7.333, 81.000
        );


        graph.addUndirectedEdge(nodeElephant, villageA, 2.0);
        graph.addUndirectedEdge(villageA, villageB, 3.0);
        graph.addUndirectedEdge(villageB, villageC, 2.5);
    }

    private void initUI() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        headerPanel.setBackground(new Color(0x1B5E20));

        JLabel title = new JLabel("Active Elephant - " + elephant.getName());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel(
                "ID: " + elephant.getId() +
                        "   |   Status: " + elephant.getStatus() +
                        "   |   Last Known Location: " + elephant.getLocation()
        );
        subtitle.setForeground(new Color(0xC8E6C9));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel headerText = new JPanel();
        headerText.setBackground(new Color(0x1B5E20));
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.add(title);
        headerText.add(Box.createVerticalStrut(4));
        headerText.add(subtitle);

        headerPanel.add(headerText, BorderLayout.WEST);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.45);
        splitPane.setDividerSize(6);

        JPanel mapPanel = createMapPanel();
        JPanel tablePanel = createTablePanel();

        splitPane.setLeftComponent(mapPanel);
        splitPane.setRightComponent(tablePanel);

        getContentPane().add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 10, 16));
        bottomPanel.setBackground(Color.WHITE);

        JButton routeBtn = new JButton("View Safe Route");
        JButton closeBtn = new JButton("Close");

        stylePrimaryButton(routeBtn);
        styleDangerButton(closeBtn);

        routeBtn.addActionListener(e -> onViewSafeRoute());
        closeBtn.addActionListener(e -> dispose());

        bottomPanel.add(routeBtn);
        bottomPanel.add(closeBtn);

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createMapPanel() {
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        mapPanel.setBackground(new Color(0xE8F5E9));

        JLabel mapTitle = new JLabel("Live Location & Elephant Zone");
        mapTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel coordLabel = new JLabel(
                "Lat: " + elephant.getLatitude() +
                        "  |  Lon: " + elephant.getLongitude()
        );
        coordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        top.add(mapTitle);
        top.add(coordLabel);

        JPanel googleMapPlaceholder = new JPanel();
        googleMapPlaceholder.setBackground(Color.WHITE);
        googleMapPlaceholder.setBorder(
                BorderFactory.createTitledBorder("Google Map (Route will open in browser)")
        );
        googleMapPlaceholder.setLayout(new BorderLayout());

        JLabel placeholderLabel = new JLabel(
                "<html><div style='text-align:center;'>Click 'View Safe Route' to Find safe route </div></html>",
                SwingConstants.CENTER
        );
        placeholderLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        googleMapPlaceholder.add(placeholderLabel, BorderLayout.CENTER);

        mapPanel.add(top, BorderLayout.NORTH);
        mapPanel.add(googleMapPlaceholder, BorderLayout.CENTER);

        return mapPanel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        panel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new BorderLayout(6, 0));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search Alerts / Villages: ");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        stylePrimaryButton(searchBtn);

        searchBtn.addActionListener(e -> filterTable());

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new Object[]{"Time", "Village", "Risk Level", "Distance (km)"}, 0
        );
        tableModel.setColumnIdentifiers(new Object[]{"Time", "Village", "Risk Level", "Distance (km)"});

        sightingsTable = new JTable(tableModel);
        sightingsTable.setFillsViewportHeight(true);
        sightingsTable.setRowHeight(24);
        sightingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(sightingsTable);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadAlertsFromDB() {
        List<ElephantAlert> alerts = alertDAO.getAlertsForElephant(elephant.getId());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (ElephantAlert a : alerts) {
            Vector<Object> row = new Vector<>();
            row.add(a.getAlertTime().format(fmt));
            row.add(a.getVillage());
            row.add(a.getRiskLevel());
            row.add(a.getDistanceKm());
            tableModel.addRow(row);
        }
    }

    private void filterTable() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableModel.setRowCount(0);
            loadAlertsFromDB();
            return;
        }

        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            String village = tableModel.getValueAt(i, 1).toString().toLowerCase();
            String risk = tableModel.getValueAt(i, 2).toString().toLowerCase();
            if (!village.contains(keyword) && !risk.contains(keyword)) {
                tableModel.removeRow(i);
            }
        }
    }

    private void onViewSafeRoute() {
        // Use DijkstraService with the NEW Graph / GraphNode
        DijkstraService dijkstra = new DijkstraService();
        PathResult result = dijkstra.shortestPath(graph, nodeElephant, villageC);

        if (result.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No safe route path found in graph.",
                    "Route Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Computed Safe Route (Dijkstra):\n");
        for (GraphNode n : result.getPath()) {
            sb.append(" -> ").append(n.getName());
        }
        JOptionPane.showMessageDialog(this, sb.toString(),
                "Safe Route", JOptionPane.INFORMATION_MESSAGE);


        MapService.openRouteInBrowser(elephant.getLatitude(), elephant.getLongitude(), result.getPath());
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(0x2E7D32));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void styleDangerButton(JButton btn) {
        btn.setBackground(new Color(0xC62828));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
}
