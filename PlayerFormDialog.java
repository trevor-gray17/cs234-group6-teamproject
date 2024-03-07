import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;




public class PlayerFormDialog extends JDialog {
    private final JTextField nameField, numberField, yearField;
    private JComboBox<String> positionField;
    private final JCheckBox isActiveCheckBox;
    private final JButton saveButton;
    private boolean saved = false;

    // Constructor for creating a new player dialog
    public PlayerFormDialog(JFrame parent) {
        this(parent, null); // Call the other constructor with null as the selectedPlayer
    }

// Constructor for editing an existing player, with pre-population of fields if player is not null
public PlayerFormDialog(JFrame parent, BasketballPlayer player) {
    super(parent, "Player Details", true);
    setLayout(new GridLayout(0, 2));

    nameField = new JTextField(player != null ? player.getName() : "");
    numberField = new JTextField(player != null ? String.valueOf(player.getNumber()) : "");
    String[] positions = {"Point Guard", "Shooting Guard", "Small Forward", "Power Forward", "Center"};
    positionField = new JComboBox<>(positions);
    positionField.setSelectedItem(player != null ? player.getPosition() : null);    yearField = new JTextField(player != null ? String.valueOf(player.getYear()) : "");
    isActiveCheckBox = new JCheckBox("", player != null ? player.getActive() : true);
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

    saveButton.addActionListener(e -> {
        saved = true;
        setVisible(false);
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
        return positionField.getSelectedItem().toString();
    }

    public String getYear() {
        return yearField.getText();
    }

    public boolean getActive() {
        return isActiveCheckBox.isSelected();
    }
}
