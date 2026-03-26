package panels;
import javax.swing.*;
import database.*;

public class studentDeletePanel extends JPanel {
    public studentDeletePanel(database db) {
        JTextField regNoField = new JTextField(20);
        JButton deleteButton = new JButton("Delete");
        JTextPane resultText = new JTextPane();
        resultText.setEditable(false);

        deleteButton.addActionListener(e -> {
            int status = db.studentDeletion(regNoField.getText());
            if(status == 0) {
                resultText.setText("Deleted");
                regNoField.setText("");
            } else if (status == 2){
                resultText.setText("This register number doesn't exist.");
            }
        });
        
        add(new JLabel("Register Number:"));
        add(regNoField);
        add(deleteButton);
        add(resultText);
    }
}
