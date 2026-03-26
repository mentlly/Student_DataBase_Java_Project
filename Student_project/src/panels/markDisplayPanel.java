package panels;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.*;
import database.*;
import java.awt.*;

public class markDisplayPanel extends JPanel {
    private DefaultTableModel model;
    private JTable markTable;
    public markDisplayPanel(database db) {
        setLayout(new BorderLayout());
        String[] columns = {"Register No", "Name", "Department", "mark1", "mark2", "mark3", "CGPA"};
        model = new DefaultTableModel(columns,0);
        markTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(markTable);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateMarkTable(db));

        updateMarkTable(db);
        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);
    }
    public void updateMarkTable(database db) {
        model.setRowCount(0);
        
        ResultSet rs = db.studentMarkDisplay();
        try {
            while(rs.next()) {
                Object[] row = {
                    rs.getString("RegisterNo"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getObject("mark1") == null ? "N/A" : rs.getDouble("mark1"),
                    rs.getObject("mark2") == null ? "N/A" : rs.getDouble("mark2"),
                    rs.getObject("mark3") == null ? "N/A" : rs.getDouble("mark3"),
                    rs.getObject("cgpa") == null ? "N/A" : rs.getString("cgpa"),
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
