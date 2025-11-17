package com.elephant.alerts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MapService {


    public static void openRouteInBrowser(double startLat,
                                          double startLng,
                                          List<GraphNode> path) {
        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No path to display on map.",
                    "Map Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");
        for (GraphNode n : path) {
            url.append(n.getLat())
                    .append(",")
                    .append(n.getLng())
                    .append("/");
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url.toString()));
            } else {
                JOptionPane.showMessageDialog(null,
                        "Desktop browsing not supported on this system.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to open Google Maps.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
