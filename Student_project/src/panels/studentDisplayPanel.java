package panels;
import java.sql.*;
import database.*;
import javax.swing.*;
import javax.swing.table.*;

public class studentDisplayPanel extends JPanel{
    private DefaultTableModel model;
    private JTable studentTable;
    public studentDisplayPanel(database db) {
        String[] columns = {"Registe No","Name","Department"};
        model = new DefaultTableModel(columns, 0);
        studentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateStudentTable(db));

        updateStudentTable(db);
        add(scrollPane);
        add(refreshButton);
    }
    public void updateStudentTable (database db) {
        model.setRowCount(0);
        studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ResultSet rs = db.studentDisplay();
        try {
            while(rs.next()) {
                Object[] row = {
                    rs.getString("RegisterNo"),
                    rs.getString("Name"),
                    rs.getString("Department")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
