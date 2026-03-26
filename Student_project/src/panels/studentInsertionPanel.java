package panels;
import javax.swing.*;
import database.*;

public class studentInsertionPanel extends JPanel {
    public studentInsertionPanel (database db) {
        JTextField regNoField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField deptField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JTextPane resultText = new JTextPane();
        resultText.setEditable(false);

        submitButton.addActionListener(e -> {
            int status = db.studentInsertion(regNoField.getText(), nameField.getText(), deptField.getText());
            if (status == 0) {
                regNoField.setText("");
                nameField.setText("");
                deptField.setText("");
                resultText.setText("Student was Added Successfully!");
            } else if (status == 1) {
                resultText.setText("Student with the Register Number already exists!");
            }
        });

        add(new JLabel("Register No:"));
        add(regNoField);
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Department:"));
        add(deptField);
        add(submitButton);
        add(resultText);
    }
}
