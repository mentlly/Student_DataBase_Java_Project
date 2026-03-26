import panels.*;
import javax.swing.*;
import database.*;

public class App {
    public static void main(String[] args) {
        database db = new database();
        db.create();
        db.createStudentTable();
        db.createStudentMarkTable();

        JFrame frame = new JFrame("MBCET");
        JTabbedPane tabbedPane = new JTabbedPane();
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane.addTab("Student Insertion", new studentInsertionPanel(db));
        tabbedPane.addTab("Mark Insertion", new markInsertionPanel(db));
        tabbedPane.addTab("Mark Updation", new markUpdationPanel(db));
        tabbedPane.addTab("Student Deletion", new studentDeletePanel(db));
        studentDisplayPanel studentDisplay = new studentDisplayPanel(db);
        tabbedPane.addTab("Student Display", studentDisplay);
        markDisplayPanel markDisplay = new markDisplayPanel(db);
        tabbedPane.addTab("Mark Display", markDisplay);
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            if (index == 4) {
                studentDisplay.updateStudentTable(db);
            } else if (index == 5) {
                markDisplay.updateMarkTable(db);
            }
        });

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
