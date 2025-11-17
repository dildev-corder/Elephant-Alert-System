package com.elephant.alerts;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AlertsTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;


    public static final int COL_TIME        = 0;
    public static final int COL_ELEPHANT_ID = 1;
    public static final int COL_ZONE        = 2;
    public static final int COL_RISK_LEVEL  = 3;
    public static final int COL_STATUS      = 4;

    private static final String[] COLUMN_NAMES = {
            "⏱ Time",
            "🐘 Elephant ID",
            "📍 Zone",
            "⚠ Risk Level",
            "✔ Status"
    };

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final List<Alert> alerts = new ArrayList<>();

    /**
     * Create a new table model with an initial list of alerts.
     *
     * @param initialAlerts list of alerts; can be null
     */
    public AlertsTableModel(List<Alert> initialAlerts) {
        setAlerts(initialAlerts);
    }

    /**
     * Replace the entire list of alerts and refresh the table.
     *
     * @param newAlerts new list of alerts; if null, list will be cleared
     */
    public final void setAlerts(List<Alert> newAlerts) {
        alerts.clear();
        if (newAlerts != null) {
            alerts.addAll(newAlerts);
        }
        fireTableDataChanged();
    }

    /**
     * Add a single alert to the end of the table.
     *
     * @param alert alert to add; ignored if null
     */
    public void addAlert(Alert alert) {
        if (alert == null) return;
        int index = alerts.size();
        alerts.add(alert);
        fireTableRowsInserted(index, index);
    }


    public List<Alert> getAlerts() {
        return Collections.unmodifiableList(alerts);
    }


    public Alert getAlertAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= alerts.size()) {
            return null;
        }
        return alerts.get(rowIndex);
    }


    public void clear() {
        int size = alerts.size();
        if (size == 0) return;
        alerts.clear();
        fireTableRowsDeleted(0, size - 1);
    }

    @Override
    public int getRowCount() {
        return alerts.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= alerts.size()) {
            return "";
        }

        Alert a = alerts.get(rowIndex);
        if (a == null) return "";

        switch (columnIndex) {
            case COL_TIME:
                if (a.getTime() == null) return "";
                return timeFormatter.format(a.getTime());

            case COL_ELEPHANT_ID:
                return safeString(a.getElephantId());

            case COL_ZONE:
                return safeString(a.getZone());

            case COL_RISK_LEVEL:

                return beautifyEnumString(a.getRiskLevel());

            case COL_STATUS:
                return beautifyEnumString(a.getStatus());

            default:
                return "";
        }
    }


    private String safeString(Object value) {
        return value == null ? "" : String.valueOf(value);
    }


    private String beautifyEnumString(Object raw) {
        if (raw == null) return "";
        String s = String.valueOf(raw).trim();
        if (s.isEmpty()) return "";


        s = s.replace("_", " ").toLowerCase();

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
