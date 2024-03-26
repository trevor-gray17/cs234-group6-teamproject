import javax.swing.*;
import java.awt.*;

import javax.swing.table.DefaultTableModel;


public class TeamRosterGUI {
    private JFrame frame;
    private JTable PlayerTable;
    private JTable StatisticsTable;

    private String[][] playerList;
    private String[][] playerStats;


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
        frame.setSize(1600, 800);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);


        
        

        tabbedPane = new JTabbedPane();

        //player Table tab
        String[] columnNames = {"Name", "Number", "Year", "Active"};
        playerList = new String[1][4];
        for (int i = 0; i < 4; i++) {
            playerList[0][i] = "";
        }

        ;
        PlayerTable = new JTable(playerList, columnNames);
        PlayerTable.setBackground(Color.LIGHT_GRAY);
        PlayerTable.setFont(new Font("Times", Font.PLAIN, 25));
        PlayerTable.setForeground(Color.BLACK);
        PlayerTable.setRowHeight(30);
        PlayerTable.setCellSelectionEnabled(true);

        JScrollPane tablePane = new JScrollPane(PlayerTable);


        tabbedPane.addTab("Roster", tablePane);

///* 
        // Statistics Tab
        String[] statColumnNames = {"Name", "Free Throws Made", "Free Throws Attempted", 
        "Three Pointers Made", "Three Pointers Attempted"};
        playerStats = new String[1][5];
        for (int i = 1; i < 5; i++) {
            playerStats[0][i] = "";
        }
        StatisticsTable = new JTable(playerStats, statColumnNames);

        StatisticsTable.setBackground(Color.LIGHT_GRAY);
        StatisticsTable.setFont(new Font("Times", Font.PLAIN, 25));
        StatisticsTable.setForeground(Color.BLACK);
        StatisticsTable.setRowHeight(30);
        StatisticsTable.setCellSelectionEnabled(true);

        JScrollPane statsPane = new JScrollPane(StatisticsTable);

        tabbedPane.addTab("Statistics", statsPane);
//*/

        // Overall Statistics Tab
        frame.add(tabbedPane, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);


        // Dropdown for player selection in Statistics
        final JComboBox<String> cb = new JComboBox<String>();
    }
    

    private void updateTeamList() {
       
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        Font buttonFont = new Font("Times", Font.BOLD, 14);
        JButton addButton = new JButton("Add");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        addButton.setFont(buttonFont);
        saveButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);


        addButton.setBackground(Color.WHITE);
        saveButton.setBackground(Color.WHITE);
        deleteButton.setBackground(Color.WHITE);
        


        // Set foreground color for buttons
        addButton.setForeground(Color.BLACK.darker());
        saveButton.setForeground(Color.BLACK.darker());
        deleteButton.setForeground(Color.BLACK.darker());



        // Set foreground color for buttons


    
    
        addButton.addActionListener(e -> {

            //Player data
            for (int i = 0; i < playerList.length; i++) {
                for (int j = 0; j < 4; j++) {
                    playerList[i][j] = PlayerTable.getValueAt(i, j).toString();
            }}

            String[][] copyPlayerList = playerList;
            playerList = new String[copyPlayerList.length + 1][4];
            
            for (int i = 0; i < copyPlayerList.length; i++) {
                for (int j = 0; j < 4; j++) {
                        playerList[i][j] = copyPlayerList[i][j];
                }
            }
            for(int i = 0; i < 4; i++){
                playerList[copyPlayerList.length][i] = "";
            
            }


            //Statistics Add column

            for (int i = 0; i < playerStats.length; i++) {
                for (int j = 1; j < 5; j++) {
                    playerStats[i][j] = StatisticsTable.getValueAt(i, j).toString();
            }}

            for (int i = 0; i < playerStats.length; i++) {
                    playerStats[i][0] = PlayerTable.getValueAt(i, 0).toString();
            }


            String[][] copyPlayerStats = playerStats;
            playerStats = new String[playerList.length][5];
            
            for (int i = 0; i < copyPlayerStats.length; i++) {
                for (int j = 0; j < 5; j++) {
                        playerStats[i][j] = copyPlayerStats[i][j];
                }
            }
            for(int i = 0; i < 5; i++){
                playerStats[copyPlayerStats.length][i] = "";
            
            }



                  
             
            PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Name", "Number", "Year", "Active"}));
            StatisticsTable.setModel(new DefaultTableModel(playerStats, new String[]{"Name", "Free Throws Made", "Free Throws Attempted", 
            "Three Pointers Made", "Three Pointers Attempted"}));


            // After the dialog is closed, check if the save button was clicked and then add the player

        });


        saveButton.addActionListener(e -> {
            //Roster
            for (int i = 0; i < playerList.length; i++) {
                for (int j = 0; j < 4; j++) {
                    playerList[i][j] = PlayerTable.getValueAt(i, j).toString();
                    if(playerList[i][j].toString() == "")
                        playerList[i][j] = "NA";
                }
            }

            for (int i = 0; i < playerList.length; i++) {
                playerStats[i][0] = playerList[i][0];

            }    
            for (int i = 0; i < playerStats.length; i++) {
                for (int j = 1; j < 5; j++) {
                    playerStats[i][j] = StatisticsTable.getValueAt(i, j).toString();
                    if(playerStats[i][j].toString() == "")
                        playerStats[i][j] = "NA";
                }
            }
            for (int i = 0; i < playerStats.length; i++) {
                playerStats[i][0] = playerList[i][0];
        }


            PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Name", "Number", "Year", "Active"}));
            StatisticsTable.setModel(new DefaultTableModel(playerStats, new String[]{"Name", "Free Throws Made", "Free Throws Attempted", 
            "Three Pointers Made", "Three Pointers Attempted"}));


            //Statistics

        });




deleteButton.addActionListener(e -> {
    int playerRow = PlayerTable.getSelectedRow();
    int statsRow = StatisticsTable.getSelectedRow();
    
    if (playerRow >= 0 || statsRow >= 0) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this player and their statistics?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel playerTableModel = (DefaultTableModel) PlayerTable.getModel();
            DefaultTableModel statsTableModel = (DefaultTableModel) StatisticsTable.getModel();
            
            if (playerRow >= 0) {
                playerTableModel.removeRow(playerRow);
                statsTableModel.removeRow(playerRow); // Remove corresponding row from statistics table
            }
            if (statsRow >= 0) {
                statsTableModel.removeRow(statsRow);
                playerTableModel.removeRow(statsRow); // Remove corresponding row from player table
            }

            // Update player data
            if (playerRow >= 0) {
                String[][] newPlayerList = new String[playerList.length - 1][4];
                String[][] newPlayerStats = new String[playerStats.length - 1][5];
                for (int i = 0, k = 0; i < playerList.length; i++) {
                    if (i != playerRow) {
                        System.arraycopy(playerList[i], 0, newPlayerList[k], 0, 4);
                        System.arraycopy(playerStats[i], 0, newPlayerStats[k], 0, 5);
                        k++;
                    }
                }
                playerList = newPlayerList;
                playerStats = newPlayerStats;
            }
        }
    }
});

        
    
        panel.add(addButton);
        panel.add(saveButton);
        panel.add(deleteButton);

    
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamRosterGUI::new);
    }
}
