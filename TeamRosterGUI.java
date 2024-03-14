import javax.swing.*;
import java.awt.*;


public class TeamRosterGUI {
    private JFrame frame;
    private DefaultListModel<String> teamListModel;
    private JList<String> teamList;
    private JTabbedPane tabbedPane;
    private Roster roster; // Assume Roster is defined somewhere

    public TeamRosterGUI() {
        this.roster = new Roster(); // Or loadRosterFromFile(); if you're loading from a file
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Team Roster Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 800);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color of the frame
        frame.getContentPane().setBackground(Color.BLUE.darker());


        tabbedPane = new JTabbedPane();

        teamListModel = new DefaultListModel<>();
        updateTeamList(); // Update the team list based on loaded roster

        // Player List Tab
        teamListModel = new DefaultListModel<>();
        teamList = new JList<>(teamListModel);

        // Set font size of the player list
        teamList.setFont(new Font("Times", Font.PLAIN, 18));

        JScrollPane scrollPane = new JScrollPane(teamList);
        tabbedPane.addTab("Player List", scrollPane);

        // Overall Statistics Tab
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setBackground(Color.LIGHT_GRAY); // Set background color of statistics panel
        statisticsPanel.add(new JLabel("Overall Statistics:")); // Placeholder for actual statistics
        statisticsPanel.setForeground(Color.BLUE); // Set text color
        statisticsPanel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        tabbedPane.addTab("Statistics", statisticsPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);


        // Dropdown for player selection in Statistics
        final JComboBox<String> cb = new JComboBox<String>();
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
                    playerToEdit.setActive(editDialog.getActive());

    
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
        panel.setBackground(Color.WHITE); // Set background color of control panel

        // Use BorderLayout for the panel
        panel.setLayout(new BorderLayout());


        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
    
        // Set font for buttons
        Font buttonFont = new Font("Times", Font.BOLD, 14);
        addButton.setFont(buttonFont);
        editButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);

        // Set background color for buttons
        addButton.setBackground(Color.WHITE);
        editButton.setBackground(Color.WHITE);
        deleteButton.setBackground(Color.WHITE);

        // Set foreground color for buttons
        addButton.setForeground(Color.BLACK.darker());
        editButton.setForeground(Color.BLACK.darker());
        deleteButton.setForeground(Color.BLACK.darker());
    

        addButton.addActionListener(e -> {
            PlayerFormDialog addDialog = new PlayerFormDialog(frame);
            addDialog.setVisible(true);
            // After the dialog is closed, check if the save button was clicked and then add the player
            if (addDialog.isSaved()) {
                BasketballPlayer player = new BasketballPlayer(
                    addDialog.getPlayerName(),
                    Integer.parseInt(addDialog.getNumber()), // Convert String to int
                    addDialog.getPosition(),
                    Integer.parseInt(addDialog.getYear()), // Ensure this matches your dialog's method and data type
                    addDialog.getActive() // Assuming there's a method to get the active status

                    
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
                        playerToEdit.setActive(editDialog.getActive());
                        // Assuming there's a method to update a player in Roster, call it here
                        // This step is crucial to ensure the Roster object has the updated player details
                        roster.updatePlayer(playerToEdit); // This method needs to be implemented in Roster
                        updateTeamList(); // Refresh the team list to reflect the updated player details
                    }
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String selectedPlayerName = teamList.getSelectedValue();
            if (selectedPlayerName != null) {
                roster.removePlayer(selectedPlayerName); // Assuming there's a method to remove a player by name
                updateTeamList(); // Refresh the team list to reflect the updated player details
            }
        });
        
        JPanel addEditPanel = new JPanel();
        addEditPanel.setBackground(Color.WHITE);
        addEditPanel.add(addButton);
        addEditPanel.add(editButton);

        panel.add(deleteButton, BorderLayout.EAST);
        panel.add(addEditPanel, BorderLayout.WEST);
    
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamRosterGUI::new);
    }
}
