package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SafeRoutesPage extends JPanel {

    private Graph graph;
    private GraphNode[] nodesArray;
    private JComboBox<GraphNode> cmbStart;
    private JComboBox<GraphNode> cmbEnd;
    private JTextArea txtResult;
    private PathResult lastResult;

    public SafeRoutesPage() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF4F6F9));

        JLabel title = new JLabel("Safe Routes & Dynamic Response");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(title, BorderLayout.NORTH);


        initGraph();


        JPanel controls = new JPanel(new GridBagLayout());
        controls.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        cmbStart = new JComboBox<>(nodesArray);
        cmbEnd = new JComboBox<>(nodesArray);

        JButton btnCompute = new JButton("Compute Safe Route");
        DashboardFrame.stylePrimaryButton(btnCompute);

        JButton btnViewMap = new JButton("View in Google Maps");
        DashboardFrame.stylePrimaryButton(btnViewMap);

        JButton btnExportPdf = new JButton("Export Route Plan as PDF");
        DashboardFrame.stylePrimaryButton(btnExportPdf);

        gbc.gridx = 0; gbc.gridy = 0;
        controls.add(new JLabel("Start location:"), gbc);
        gbc.gridx = 1;
        controls.add(cmbStart, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        controls.add(new JLabel("Destination:"), gbc);
        gbc.gridx = 1;
        controls.add(cmbEnd, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(btnCompute);
        buttonRow.add(btnViewMap);
        buttonRow.add(btnExportPdf);
        controls.add(buttonRow, gbc);

        add(controls, BorderLayout.CENTER);


        txtResult = new JTextArea(7, 40);
        txtResult.setEditable(false);
        txtResult.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtResult.setBorder(BorderFactory.createTitledBorder("Route details"));

        add(new JScrollPane(txtResult), BorderLayout.SOUTH);


        btnCompute.addActionListener(this::onComputeRoute);
        btnViewMap.addActionListener(this::onViewMap);
        btnExportPdf.addActionListener(this::onExportPdf);
    }

    private void initGraph() {
        graph = new Graph();


        GraphNode e1 = graph.addNode(
                "E-001",
                "E-001 Raja (Galoya)",
                7.743, 81.609
        );

        GraphNode e2 = graph.addNode(
                "E-002",
                "E-002 Kawanthissa (Maduru Oya)",
                7.838, 81.299
        );

        GraphNode e3 = graph.addNode(
                "E-003",
                "E-003 Gamunu (Mahiyanganaya)",
                7.333, 81.000
        );


        GraphNode villageA = graph.addNode("VA", "Village A (Galoya side)", 7.750, 81.620);
        GraphNode villageB = graph.addNode("VB", "Village B (Maduru Oya side)", 7.845, 81.310);
        GraphNode villageC = graph.addNode("VC", "Village C (Mahiyanganaya side)", 7.340, 80.990);
        GraphNode rangerPost = graph.addNode("R", "Central Ranger Post", 7.55, 81.35);


        graph.addUndirectedEdge(e1, villageA, 5);   // Raja ↔ Village A
        graph.addUndirectedEdge(e2, villageB, 4);   // Kawanthissa ↔ Village B
        graph.addUndirectedEdge(e3, villageC, 3);   // Gamunu ↔ Village C


        graph.addUndirectedEdge(villageA, villageB, 20);
        graph.addUndirectedEdge(villageB, villageC, 18);
        graph.addUndirectedEdge(villageA, villageC, 25);


        graph.addUndirectedEdge(villageA, rangerPost, 22);
        graph.addUndirectedEdge(villageB, rangerPost, 15);
        graph.addUndirectedEdge(villageC, rangerPost, 19);


        graph.addUndirectedEdge(e1, rangerPost, 30);
        graph.addUndirectedEdge(e2, rangerPost, 28);
        graph.addUndirectedEdge(e3, rangerPost, 26);


        nodesArray = graph.getNodes().toArray(new GraphNode[0]);
    }

    private void onComputeRoute(ActionEvent e) {
        GraphNode start = (GraphNode) cmbStart.getSelectedItem();
        GraphNode end = (GraphNode) cmbEnd.getSelectedItem();
        if (start == null || end == null) return;

        DijkstraService service = new DijkstraService();
        PathResult result = service.shortestPath(graph, start, end);
        lastResult = result;

        StringBuilder sb = new StringBuilder();
        if (result.getPath().isEmpty()) {
            sb.append("No route found between ")
                    .append(start.getName())
                    .append(" and ")
                    .append(end.getName())
                    .append(".");
        } else {
            sb.append("Safe route from ").append(start.getName())
                    .append(" to ").append(end.getName()).append(":\n\n");
            int i = 1;
            for (GraphNode n : result.getPath()) {
                sb.append(i++).append(". ")
                        .append(n.getName())
                        .append(String.format(" (%.4f, %.4f)", n.getLat(), n.getLng()))
                        .append("\n");
            }
            sb.append("\nTotal risk-weighted distance: ")
                    .append(String.format("%.2f", result.getTotalWeight()))
                    .append(" units\n");
        }

        txtResult.setText(sb.toString());
    }

    private void onViewMap(ActionEvent e) {
        if (lastResult == null || lastResult.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please compute a route first.",
                    "No route",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");
        for (GraphNode n : lastResult.getPath()) {
            url.append(n.getLat()).append(",").append(n.getLng()).append("/");
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url.toString()));
            } else {
                JOptionPane.showMessageDialog(this,
                        "Desktop browsing not supported on this system.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to open Google Maps.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onExportPdf(ActionEvent e) {
        if (lastResult == null || lastResult.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please compute a route before exporting.",
                    "No route",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Route Plan PDF");
        chooser.setSelectedFile(new File("route_plan.pdf"));

        int choice = chooser.showSaveDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                PdfExporter.exportRoutePlan(lastResult, file);
                JOptionPane.showMessageDialog(this,
                        "Route plan exported: " + file.getAbsolutePath(),
                        "Export successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Failed to export PDF.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
