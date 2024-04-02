import javax.swing.*;
import java.awt.*;

import javax.swing.table.DefaultTableModel;


public class TeamRosterGUI {
    private JFrame frame;
    private JTable PlayerTable;
    private JTable PlayerStatsTable;
    private JTable DayStatsTable;

    private String[][] playerList;
    private String[][] playerStats;
    private String[][] dayStats;
    private JComboBox<String> positionComboBox;
    private JComboBox<String> activeComboBox;



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

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);


        
        

        tabbedPane = new JTabbedPane();

        //player Table tab
        String[] columnNames = {"Number", "Name", "Year"};
        playerList = new String[1][3];
        for (int i = 0; i < 3; i++) {
            playerList[0][i] = "";
        }


        PlayerTable = new JTable(playerList, columnNames);
        PlayerTable.setBackground(Color.LIGHT_GRAY);
        PlayerTable.setFont(new Font("Times", Font.PLAIN, 25));
        PlayerTable.setForeground(Color.BLACK);
        PlayerTable.setRowHeight(30);
        PlayerTable.setCellSelectionEnabled(true);



        JScrollPane playerRosterPane = new JScrollPane(PlayerTable);


        tabbedPane.addTab("Roster", playerRosterPane);



        //Days Tab
        String[] dayColNames = {"Date", "Number","Name", "Free Throws Made", "Free Throws Attempted",
        "Three Pointers Made", "Three Pointers Attempted"};
        dayStats = new String[0][7];
        
        DayStatsTable = new JTable(dayStats, dayColNames);

        DayStatsTable.setBackground(Color.LIGHT_GRAY);
        DayStatsTable.setFont(new Font("Times", Font.PLAIN, 25));
        DayStatsTable.setForeground(Color.BLACK);
        DayStatsTable.setRowHeight(30);
        DayStatsTable.setCellSelectionEnabled(true);

        JScrollPane dayStatsPane = new JScrollPane(DayStatsTable);

        tabbedPane.addTab("Stats By Date", dayStatsPane);


         // Statistics Tab
         String[] statColumnNames = {"Number", "Name", "Free Throws %", "Three Pointers %"};
         playerStats = new String[1][4];
         for (int i = 0; i < 4; i++) {
             playerStats[0][i] = "";
         }
         PlayerStatsTable = new JTable(playerStats, statColumnNames);
 
         PlayerStatsTable.setBackground(Color.LIGHT_GRAY);
         PlayerStatsTable.setFont(new Font("Times", Font.PLAIN, 25));
         PlayerStatsTable.setForeground(Color.BLACK);
         PlayerStatsTable.setRowHeight(30);
         PlayerStatsTable.setCellSelectionEnabled(true);
 
         JScrollPane playerStatsPane = new JScrollPane(PlayerStatsTable);
 
         tabbedPane.addTab("Statistics", playerStatsPane);
 


        frame.add(tabbedPane, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);


        // Dropdown for player selection in Statistics
        final JComboBox<String> cb = new JComboBox<String>();
    }
    

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        Font buttonFont = new Font("Times", Font.BOLD, 14);
        JButton addButton = new JButton("Add");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JButton addDayButton = new JButton("Add Day");


        addButton.setFont(buttonFont);
        addDayButton.setFont(buttonFont);
        saveButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);


        addButton.setBackground(Color.WHITE);
        addDayButton.setBackground(Color.WHITE);
        saveButton.setBackground(Color.WHITE);
        deleteButton.setBackground(Color.WHITE);
        


        // Set foreground color for buttons
        addButton.setForeground(Color.BLACK.darker());
        addDayButton.setForeground(Color.BLACK.darker());
        saveButton.setForeground(Color.BLACK.darker());
        deleteButton.setForeground(Color.BLACK.darker());

    
    
        addButton.addActionListener(e -> {

            //Player data
            for (int i = 0; i < playerList.length; i++) {
                for (int j = 0; j < 3; j++) {
                    playerList[i][j] = PlayerTable.getValueAt(i, j).toString();
            }}

            String[][] copyPlayerList = playerList;
            playerList = new String[copyPlayerList.length + 1][3];
            
            for (int i = 0; i < copyPlayerList.length; i++) {
                for (int j = 0; j < 3; j++) {
                        playerList[i][j] = copyPlayerList[i][j];
                }
            }
            for(int i = 0; i < 3; i++){
                playerList[copyPlayerList.length][i] = "";
            
            }


            //Statistics Add column

            for (int i = 0; i < playerStats.length; i++) {
                for (int j = 1; j < 4; j++) {
                    playerStats[i][j] = PlayerStatsTable.getValueAt(i, j).toString();
            }}

            for (int i = 0; i < playerStats.length; i++) {
                playerStats[i][0] = playerList[i][0];
                playerStats[i][1] = playerList[i][1];
                }


            String[][] copyPlayerStats = playerStats;
            playerStats = new String[playerList.length][4];
            
            for (int i = 0; i < copyPlayerStats.length; i++) {
                for (int j = 0; j < 4; j++) {
                        playerStats[i][j] = copyPlayerStats[i][j];
                }
            }
            
            for(int i = 0; i < 4; i++){
                playerStats[copyPlayerStats.length][i] = "";
            
            }

            for (int i = 0; i < dayStats.length; i++) {
                dayStats[i][1] = playerList[i][0];
                dayStats[i][2] = playerList[i][1];
                }






            DayStatsTable.setModel(new DefaultTableModel(dayStats, new String[]{"Date", "Number","Name", "Free Throws Made", "Free Throws Attempted",
                "Three Pointers Made", "Three Pointers Attempted"}));

            PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Number","Name",  "Year"}));



            PlayerStatsTable.setModel(new DefaultTableModel(playerStats, new String[] {"Number", "Name", "Free Throws %", "Three Pointers %"}));


            // After the dialog is closed, check if the save button was clicked and then add the player

    });
    addDayButton.addActionListener(e -> {

        String input = JOptionPane.showInputDialog(null, "Please Enter Next Date:");

        for(int i = 0; i < dayStats.length; i++){
            for(int j = 0; j < 7; j++){
                if (dayStats[i][j] == "") {
                    dayStats[i][j] = "";
                }
                else
                    dayStats[i][j] = DayStatsTable.getValueAt(i, j).toString();
            }
        }

        String[][] copyDayStats = dayStats;
        if(dayStats.length == 0){
            dayStats = new String[playerList.length][7];
            for (int i = 0; i < playerList.length; i++) {
                dayStats[i][0] = input;
                dayStats[i][1] = playerList[i][0];
                dayStats[i][2] = playerList[i][1];
                }
        }
        else{
            dayStats = new String[copyDayStats.length + playerList.length][7];
            for (int i = 0; i < copyDayStats.length; i++) {
                for (int j = 0; j < 7; j++) {
                        dayStats[i][j] = copyDayStats[i][j];
                }

            }
            for (int i = 0; i < playerList.length; i++) {
                dayStats[copyDayStats.length + i][0] = input;
                dayStats[copyDayStats.length + i][1] = playerList[i][0];
                dayStats[copyDayStats.length + i][2] = playerList[i][1];
                }
        }



        



            
        DayStatsTable.setModel(new DefaultTableModel(dayStats, new String[]{"Date", "Number","Name", "Free Throws Made", "Free Throws Attempted",
            "Three Pointers Made", "Three Pointers Attempted"}));


        PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Number","Name",  "Year"}));



        PlayerStatsTable.setModel(new DefaultTableModel(playerStats, new String[] {"Number", "Name", "Free Throws %", "Three Pointers %"}));





});


        saveButton.addActionListener(e -> {
            //Roster
            for (int i = 0; i < playerList.length; i++) {
                for (int j = 0; j < 3; j++) {
                    playerList[i][j] = PlayerTable.getValueAt(i, j).toString();
                    if(playerList[i][j].toString() == "")
                        playerList[i][j] = "";
                }
            }

            for (int i = 0; i < playerList.length; i++) {
                playerStats[i][0] = playerList[i][0];

            }    

            for (int i = 0; i < playerStats.length; i++) {
                for (int j = 1; j < 4; j++) {
                    playerStats[i][j] = PlayerStatsTable.getValueAt(i, j).toString();
                    if(playerStats[i][j].toString() == "")
                        playerStats[i][j] = "";
                }
            }

            for (int i = 0; i < playerStats.length; i++) {
                playerStats[i][0] = playerList[i][0];
                playerStats[i][1] = playerList[i][1];

        }

        
        for (int i = 0; i < (dayStats.length/playerList.length); i++) {
            for(int j = 0; j < playerList.length; j++){
                dayStats[j + (playerList.length * i)][1] = playerList[j][0];
                dayStats[j + (playerList.length * i)][2] = playerList[j][1];
            }

        }

        if(DayStatsTable.getSize().equals(0)){
            for (int i = 0; i < dayStats.length; i++) {
                for (int j = 0; j < 7; j++) {
                    dayStats[i][j] = DayStatsTable.getValueAt(i, j).toString();
                    if(DayStatsTable.getValueAt(i, j) == null){
                        if (j == 3 || j == 4 || j == 5 || j == 6)
                            dayStats[i][j] = "0";
                        else
                            dayStats[i][j] = "";
                    }
                }
            }
        }
            

            DayStatsTable.setModel(new DefaultTableModel(dayStats, new String[]{"Date", "Number","Name", "Free Throws Made", "Free Throws Attempted",
            "Three Pointers Made", "Three Pointers Attempted"}));

            PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Number","Name",  "Year"}));


            PlayerStatsTable.setModel(new DefaultTableModel(playerStats, new String[] {"Number", "Name", "Free Throws %", "Three Pointers %"}));

            for (int i = 0; i < playerList.length; i++) {
                roster.addPlayer(new BasketballPlayer(playerList[i][1], Integer.parseInt(playerList[i][0]), Integer.parseInt(playerList[i][2])));
            }

            for (int i = 0; i < playerStats.length; i++) {
                roster.getPlayers().get(playerStats[i][1]).addShootingStats("date", new ShootingStatistics(0, Integer.parseInt(playerStats[i][3]), 0, Integer.parseInt(playerStats[i][2])));
            }




            roster.getPlayers().forEach((k, v) -> {
                System.out.println(k + " " + v.getNumber());
            });


        });




        deleteButton.addActionListener(e -> {
            int playerRow = PlayerTable.getSelectedRow();
            int statsRow = PlayerStatsTable.getSelectedRow();
            
            if (playerRow >= 0 || statsRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this player and their statistics?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel playerTableModel = (DefaultTableModel) PlayerTable.getModel();
                    DefaultTableModel statsTableModel = (DefaultTableModel) PlayerStatsTable.getModel();
                    
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
                        String[][] newPlayerList = new String[playerList.length - 1][3];
                        String[][] newPlayerStats = new String[playerStats.length - 1][4];
                        for (int i = 0, k = 0; i < newPlayerList.length; i++) {
                            if (i != playerRow) {
                                System.arraycopy(playerList[i], 0, newPlayerList[k], 0, 3);
                                System.arraycopy(playerStats[i], 0, newPlayerStats[k], 0, 4);
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
        panel.add(addDayButton);

    
        return panel;
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamRosterGUI::new);
    }
}
