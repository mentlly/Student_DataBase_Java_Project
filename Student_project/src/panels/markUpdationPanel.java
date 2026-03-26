package panels;
import java.text.NumberFormat;

import javax.swing.*;
import database.*;

public class markUpdationPanel extends JPanel {
    public markUpdationPanel (database db) {
        JTextField regNoField = new JTextField(20);
        NumberFormat decimalFormat = NumberFormat.getNumberInstance();
        JFormattedTextField mark1Field = new JFormattedTextField(decimalFormat);
        mark1Field.setColumns(5);
        JFormattedTextField mark2Field = new JFormattedTextField(decimalFormat);
        mark2Field.setColumns(5);
        JFormattedTextField mark3Field = new JFormattedTextField(decimalFormat);
        mark3Field.setColumns(5);

        JButton submitButton = new JButton("Submit");
        JTextPane resultText = new JTextPane();
        resultText.setEditable(false);

        submitButton.addActionListener(x -> {
            try {
                double m1 = ((Number)mark1Field.getValue()).doubleValue();
                double m2 = ((Number)mark2Field.getValue()).doubleValue();
                double m3 = ((Number)mark3Field.getValue()).doubleValue();
                int status = db.studentMarkUpdation(regNoField.getText(), m1, m2, m3);
                if (status == 0) {
                    regNoField.setText("");
                    mark1Field.setValue(null);
                    mark2Field.setValue(null);
                    mark3Field.setValue(null);
                    resultText.setText("The student marks was Updated");
                } else if (status == 2) {
                    resultText.setText("There is no student with this Register Number");
                }
            } catch (Exception e) {
                resultText.setText("Enter all values");
            }
        });

        add(new JLabel("Register No:"));
        add(regNoField);
        add(new JLabel("Mark 1:"));
        add(mark1Field);
        add(new JLabel("Mark 2:"));
        add(mark2Field);
        add(new JLabel("Mark 3:"));
        add(mark3Field);
        add(submitButton);
        add(resultText);
    }
}
