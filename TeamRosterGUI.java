import javax.swing.*;
import java.awt.*;

public class TeamRosterGUI {
    private JFrame frame;
    private DefaultListModel<String> teamListModel;
    private JList<String> teamList;
    private JTabbedPane tabbedPane;
    private Roster roster; // Assume Roster is defined somewhere
    private static final String FILE_PATH = "roster.json"; // Define the file path for saving/loading

    public TeamRosterGUI() {
        this.roster = new Roster(); // Or loadRosterFromFile(); if you're loading from a file
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Team Roster Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        tabbedPane = new JTabbedPane();

        teamListModel = new DefaultListModel<>();
        updateTeamList(); // Update the team list based on loaded roster

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

    private void editSelectedPlayer() {
        String selectedPlayerName = teamList.getSelectedValue();
        if (selectedPlayerName != null) {
            // Find the player in the roster. This requires a method in Roster to find a player by name.
            BasketballPlayer playerToEdit = roster.getPlayerByName(selectedPlayerName);
            if (playerToEdit != null) {
                PlayerFormDialog editDialog = new PlayerFormDialog(frame, playerToEdit);
                editDialog.setVisible(true);
                if (editDialog.isSaved()) {
                    // Update the player's details with the values from the dialog.
                    playerToEdit.setName(editDialog.getPlayerName());
                    playerToEdit.setNumber(Integer.parseInt(editDialog.getNumber()));
                    playerToEdit.setPosition(editDialog.getPosition());
                    playerToEdit.setYear(Integer.parseInt(editDialog.getYear()));
    
                    // Refresh the team list to reflect the updated player details.
                    updateTeamList();
                }
            }
        }
    }
    

    private void updateTeamList() {
        teamListModel.clear();
        for (BasketballPlayer player : roster.getPlayers().values()) {
            // Here, you can access each player object
            // For example, to update the team list in the GUI:
            teamListModel.clear();
            teamListModel.addElement(player.getName());

        }
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
    
        addButton.addActionListener(e -> {
            PlayerFormDialog addDialog = new PlayerFormDialog(frame);
            addDialog.setVisible(true);
            // After the dialog is closed, check if the save button was clicked and then add the player
            if (addDialog.isSaved()) {
                BasketballPlayer player = new BasketballPlayer(
                    addDialog.getPlayerName(),
                    Integer.parseInt(addDialog.getNumber()), // Convert String to int
                    addDialog.getPosition(),
                    Integer.parseInt(addDialog.getYear()) // Ensure this matches your dialog's method and data type
                );
                roster.addPlayer(player); // Ensure the Roster class has this method implemented correctly
                teamListModel.addElement(player.getName()); // Update GUI list
            }
        });
    
        editButton.addActionListener(e -> {
            String selectedPlayerName = teamList.getSelectedValue(); // Moved inside the listener
            if (selectedPlayerName != null) {
                BasketballPlayer playerToEdit = roster.getPlayerByName(selectedPlayerName);
                if (playerToEdit != null) {
                    PlayerFormDialog editDialog = new PlayerFormDialog(frame, playerToEdit);
                    editDialog.setVisible(true);
                    if (editDialog.isSaved()) {
                        playerToEdit.setName(editDialog.getPlayerName());
                        playerToEdit.setNumber(Integer.parseInt(editDialog.getNumber()));
                        playerToEdit.setPosition(editDialog.getPosition());
                        playerToEdit.setYear(Integer.parseInt(editDialog.getYear()));
                        // Assuming there's a method to update a player in Roster, call it here
                        // This step is crucial to ensure the Roster object has the updated player details
                        roster.updatePlayer(playerToEdit); // This method needs to be implemented in Roster
                        updateTeamList(); // Refresh the team list to reflect the updated player details
                    }
                }
            }
        });
        
    
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
    
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamRosterGUI::new);
    }
}
