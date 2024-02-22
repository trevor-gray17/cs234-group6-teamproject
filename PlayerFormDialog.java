import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerFormDialog extends JDialog {
    private final JTextField nameField, numberField, positionField, yearField;
    private final JCheckBox isActiveCheckBox;
    private final JButton saveButton;
    private boolean saved = false;

    public PlayerFormDialog(JFrame parent) {
        super(parent, "Player Details", true);
        setLayout(new GridLayout(0, 2));

        nameField = new JTextField();
        numberField = new JTextField();
        positionField = new JTextField();
        yearField = new JTextField();
        isActiveCheckBox = new JCheckBox();
        saveButton = new JButton("Save");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Number:"));
        add(numberField);
        add(new JLabel("Position:"));
        add(positionField);
        add(new JLabel("Year:"));
        add(yearField);
        add(new JLabel("Active:"));
        add(isActiveCheckBox);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = true;
                setVisible(false);
            }
        });

        add(saveButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isSaved() {
        return saved;
    }

    public String getPlayerName() {
        return nameField.getText();
    }

    public String getNumber() {
        return numberField.getText();
    }

    public String getPosition() {
        return positionField.getText();
    }

    public String getYear() {
        return yearField.getText();
    }

    public boolean isActive() {
        return isActiveCheckBox.isSelected();
    }
}
