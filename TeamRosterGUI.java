import javax.swing.*;
import java.awt.*;

public class TeamRosterGUI {
    private JFrame frame;
    private DefaultListModel<String> teamListModel;
    private JList<String> teamList;
    private JTabbedPane tabbedPane;

    public TeamRosterGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Team Roster Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        // Player List Tab
        teamListModel = new DefaultListModel<>();
        teamList = new JList<>(teamListModel);
        JScrollPane scrollPane = new JScrollPane(teamList);
        tabbedPane.addTab("Player List", scrollPane);

        // Overall Statistics Tab
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.add(new JLabel("Overall Statistics:")); // Placeholder for actual statistics
        tabbedPane.addTab("Statistics", statisticsPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        JTextField memberField = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
    
        addButton.addActionListener(e -> {
            PlayerFormDialog addDialog = new PlayerFormDialog(frame);
            addDialog.setVisible(true);
            // After the dialog is closed, check if the save button was clicked and then add the player
            if (addDialog.isSaved()) {
                String name = addDialog.getPlayerName();
                // Assume you have a method to add a player to the list, and call it here
                // For example: addPlayerToList(name, ...);
            }
        });
    
        editButton.addActionListener(e -> {
            String selectedPlayer = teamList.getSelectedValue();
            if (selectedPlayer != null) {
                PlayerFormDialog editDialog = new PlayerFormDialog(frame);
                // Prepopulate dialog with player's data if needed
                editDialog.setVisible(true);
                // After the dialog is closed, check if the save button was clicked and then update the player
                if (editDialog.isSaved()) {
                    String name = editDialog.getPlayerName();
                    // Assume you have a method to update the player in the list, and call it here
                    // For example: updatePlayerInList(selectedPlayer, name, ...);
                }
            }
        });
    
        panel.add(memberField);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
    
        return panel;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamRosterGUI::new);
    }
}
