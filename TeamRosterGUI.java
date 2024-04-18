import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;


public class TeamRosterGUI {
    private JFrame frame;
    private JTable PlayerTable;
    private JTable PlayerStatsTable;
    private JTable DayStatsTable;

    private String[][] playerList;
    private String[][] playerStats;
    private String[][] dayStats;

    private String[] columnNames = {"Number", "Name", "Year"};
    private String[] dayColNames = {"Date", "Number","Name", "Free Throws Made", "Free Throws Attempted",
    "Three Pointers Made", "Three Pointers Attempted"};
    private String[] statColumnNames = {"Number", "Name", "Free Throws %", "Three Pointers %"};


    private JComboBox<String> positionComboBox;
    private JComboBox<String> activeComboBox;



    private JTabbedPane tabbedPane;
    private Roster roster; // Assume Roster is defined somewhere

    public TeamRosterGUI() {
        //this.roster = new Roster(); // Or loadRosterFromFile(); if you're loading from a file
        //createPlayerTable();
        loadRosterFromFile();

        
        initializeUI();
    }

    private void loadRosterFromFile() {
        // Load roster from file
        roster = new Roster();
        roster.loadRosterFromDB();
        playerList = new String[roster.getPlayers().size()][3];
        playerStats = new String[roster.getPlayers().size()][4];
        int i = 0;
        for(BasketballPlayer player : roster.getPlayers().values()){
            playerList[i][0] = player.getNumber() + "";
            playerList[i][1] = player.getName() + "";
            playerList[i][2] = player.getYear() + "";


            playerStats[i][0] = player.getNumber() + "";
            playerStats[i][1] = player.getName() + "";
            //playerStats[i][2] = player.getShootingStats().get(player).calculateFreeThrowPercentage() + "";
            //playerStats[i][3] = player.getShootingStats().get(player).calculateThreePointPercentage() + "";
            i++;

        }

        
        



    }

    private void createPlayerTable(){
        String[] columnNames = {"Number", "Name", "Year"};
        playerList = new String[1][3];
        for (int i = 0; i < 3; i++) {
            playerList[0][i] = "";
        }

        playerStats = new String[1][4];
        for (int i = 0; i < 4; i++) {
            playerStats[0][i] = "";
        }
    }

    private void initializeUI() {
        frame = new JFrame("Team Roster Manager");
        frame.setSize(1600, 800);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                roster.saveRosterToDB();
                System.exit(0);
            }
        
        });

        
        

        tabbedPane = new JTabbedPane();


        PlayerTable = new JTable(playerList, columnNames);

        PlayerTable.setBackground(Color.LIGHT_GRAY);
        PlayerTable.setFont(new Font("Times", Font.PLAIN, 25));
        PlayerTable.setForeground(Color.BLACK);
        PlayerTable.setRowHeight(30);
        PlayerTable.setCellSelectionEnabled(true);
        
        PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Number","Name",  "Year"}));



        



        //Days Tab

        dayStats = new String[0][7];
        if(roster.getPlayers().size() != 0){
            int numPracticeDates = roster.getPlayers().get(playerList[0][1]).getShootingStats().size();
            dayStats = new String[roster.getPlayers().size() * numPracticeDates][7];
            System.out.println("Num Practice Dates * roster.getPlayers().size(): " + roster.getPlayers().size() * numPracticeDates);
            int j = 0;
            for (int i = 0; i < roster.getPlayers().size(); i++) {

                Map<String, ShootingStatistics> insertStats = roster.getPlayers().get(playerList[i][1]).getShootingStats();
                for (Map.Entry<String, ShootingStatistics> entry : insertStats.entrySet()) {
                    dayStats[j][0] = entry.getKey();
                    dayStats[j][1] = playerList[i][0];
                    dayStats[j][2] = playerList[i][1];
                    dayStats[j][3] = entry.getValue().getFreeThrowsMade() + "";
                    dayStats[j][4] = entry.getValue().getFreeThrowsTaken() + "";
                    dayStats[j][5] = entry.getValue().getThreePointersMade() + "";
                    dayStats[j][6] = entry.getValue().getThreePointersTaken() + "";
                    j++;
                    //break;
            }
        }
    }
        else{
            dayStats = new String[0][7];
        }


        for (int j = 0; j < playerStats.length; j++) {

            playerStats[j][2] = roster.getPlayers().get(playerStats[j][1]).getShootingStats().get(dayStats[j][0]).calculateFreeThrowPercentage() + "";
            playerStats[j][3] = roster.getPlayers().get(playerStats[j][1]).getShootingStats().get(dayStats[j][0]).calculateThreePointPercentage() + "";
        }


        DayStatsTable = new JTable(dayStats, dayColNames);

        DayStatsTable.setBackground(Color.LIGHT_GRAY);
        DayStatsTable.setFont(new Font("Times", Font.PLAIN, 25));
        DayStatsTable.setForeground(Color.BLACK);
        DayStatsTable.setRowHeight(30);
        DayStatsTable.setCellSelectionEnabled(true);

        

         // Statistics Tab
         PlayerStatsTable = new JTable(playerStats, statColumnNames);

         PlayerStatsTable.setBackground(Color.LIGHT_GRAY);
         PlayerStatsTable.setFont(new Font("Times", Font.PLAIN, 25));
         PlayerStatsTable.setForeground(Color.BLACK);
         PlayerStatsTable.setRowHeight(30);
         PlayerStatsTable.setCellSelectionEnabled(true);
         PlayerStatsTable.setModel(new DefaultTableModel(playerStats, new String[] {"Number", "Name", "Free Throws %", "Three Pointers %"}));

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JScrollPane playerRosterPane = new JScrollPane(PlayerTable);


        tabbedPane.addTab("Roster", playerRosterPane);


        JScrollPane dayStatsPane = new JScrollPane(DayStatsTable);

        tabbedPane.addTab("Stats By Date", dayStatsPane);

 
        JScrollPane playerStatsPane = new JScrollPane(PlayerStatsTable);
 
        tabbedPane.addTab("Statistics", playerStatsPane);
 


        frame.add(tabbedPane, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);


        // Dropdown for player selection in Statistics


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
            System.out.println("ADD");


            // Create a dialog to add a new player

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

            //roster.addPlayer(new BasketballPlayer(Integer.parseInt(playerList[playerList.length - 2][0]), playerList[playerList.length - 2][1], Integer.parseInt(playerList[playerList.length - 2][2])));


            String [][] copyPlayerStat = playerStats;
            playerStats = new String[playerList.length][4];
            for(int i = 0; i < copyPlayerStat.length; i++){
                for(int j = 0; j < 4; j++){
                    playerStats[i][j] = copyPlayerStat[i][j];
                }
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

            System.out.println("SAVE");
            for (int i = 0; i < PlayerTable.getRowCount(); i++) {
                if(roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()) != null){
                    if(PlayerTable.getValueAt(i, 1).toString() == roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getName()){
                        if (PlayerTable.getValueAt(i, 2).toString() == "" && PlayerTable.getValueAt(i, 0).toString() == "" || PlayerTable.getValueAt(i, 2).toString() == null && PlayerTable.getValueAt(i, 0).toString() == null) 
                            roster.updatePlayer(new BasketballPlayer(0, PlayerTable.getValueAt(i, 1).toString(), 0000 ));     
                        else if(PlayerTable.getValueAt(i, 0).toString() == "" || PlayerTable.getValueAt(i, 0).toString() == null)
                            roster.updatePlayer(new BasketballPlayer(0, PlayerTable.getValueAt(i, 1).toString(), Integer.parseInt(PlayerTable.getValueAt(i, 2).toString())));
                        else if (PlayerTable.getValueAt(i, 2).toString() == "" || PlayerTable.getValueAt(i, 2).toString() == null) 
                            roster.updatePlayer(new BasketballPlayer(Integer.parseInt(PlayerTable.getValueAt(i, 0).toString()), PlayerTable.getValueAt(i, 1).toString(), 0000 ));
                        else
                            roster.updatePlayer(new BasketballPlayer(Integer.parseInt(PlayerTable.getValueAt(i, 0).toString()), PlayerTable.getValueAt(i, 1).toString(), Integer.parseInt(PlayerTable.getValueAt(i, 2).toString()) ));
                }}                        
            }

            if(roster.getPlayers().size() != PlayerTable.getRowCount()){
                for (int i = 0; i < PlayerTable.getRowCount(); i++) {
                    if(roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()) == null){
                        if (PlayerTable.getValueAt(i, 1).toString() == ""  || PlayerTable.getValueAt(i,1).toString() == null) 
                            break;
                        else if (PlayerTable.getValueAt(i, 2).toString() == "" && PlayerTable.getValueAt(i, 0).toString() == "" || PlayerTable.getValueAt(i, 2).toString() == null && PlayerTable.getValueAt(i, 0).toString() == null) 
                            roster.addPlayer(new BasketballPlayer(0, PlayerTable.getValueAt(i, 1).toString(), 0000 )); 
                        else if(PlayerTable.getValueAt(i, 0).toString() == "" || PlayerTable.getValueAt(i, 0).toString() == null)
                            roster.addPlayer(new BasketballPlayer(0, PlayerTable.getValueAt(i, 1).toString(), Integer.parseInt(PlayerTable.getValueAt(i, 2).toString())));
                        else if (PlayerTable.getValueAt(i, 2).toString() == "" || PlayerTable.getValueAt(i, 2).toString() == null) 
                            roster.addPlayer(new BasketballPlayer(Integer.parseInt(PlayerTable.getValueAt(i, 0).toString()), PlayerTable.getValueAt(i, 1).toString(), 0000 ));
                        else
                            roster.addPlayer(new BasketballPlayer(Integer.parseInt(PlayerTable.getValueAt(i, 0).toString()), PlayerTable.getValueAt(i, 1).toString(), Integer.parseInt(PlayerTable.getValueAt(i, 2).toString()) ));
                    }                       

                }
            }

            //Stats

            



            if(PlayerTable.getValueAt(PlayerTable.getRowCount() - 1, 1).toString() == ""){
                for(int i = 0; i < PlayerTable.getRowCount() - 1; i++){

                    playerStats[i][0] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getNumber() + "";
                    playerStats[i][1] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getName() + "";

                    playerList[i][0] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getNumber() + "";
                    playerList[i][1] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getName() + "";
                    playerList[i][2] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getYear() + "";

                }
            }
            else{
                for(int i = 0; i < PlayerTable.getRowCount(); i++){

                    playerStats[i][0] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getNumber() + "";
                    playerStats[i][1] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getName() + "";

                    playerList[i][0] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getNumber() + "";
                    playerList[i][1] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getName() + "";
                    playerList[i][2] = roster.getPlayers().get(PlayerTable.getValueAt(i, 1).toString()).getYear() + "";

                    }
                }





                for (int i = 0; i < (dayStats.length/playerList.length); i++) {
                    for(int j = 0; j < playerList.length; j++){
                        dayStats[j + (playerList.length * i)][1] = playerList[j][0];
                        dayStats[j + (playerList.length * i)][2] = playerList[j][1];
                    }
        
                }
        
                if(!DayStatsTable.getSize().equals(dayStats.length)){
                    for (int i = 0; i < dayStats.length; i++) {
                        for (int j = 0; j < 7; j++) {
                            if(DayStatsTable.getValueAt(i, j) == null){
                                if (j == 3 || j == 4 || j == 5 || j == 6)
                                    dayStats[i][j] = "0";
                                    DayStatsTable.setValueAt("0", i, j);
                            }
                            else
                                dayStats[i][j] = DayStatsTable.getValueAt(i, j).toString();

                        }
                        roster.getPlayers().get(dayStats[i][2]).addShootingStats(dayStats[i][0], new ShootingStatistics(Integer.parseInt(dayStats[i][6]), Integer.parseInt(dayStats[i][5]), Integer.parseInt(dayStats[i][4]), Integer.parseInt(dayStats[i][3])));

                        if(roster.getPlayers().get(dayStats[i][2]).getShootingStats().get(dayStats[i][0]) == null){
                           roster.getPlayers().get(dayStats[i][2]).addShootingStats(dayStats[i][0], new ShootingStatistics(Integer.parseInt(dayStats[i][6]), Integer.parseInt(dayStats[i][5]), Integer.parseInt(dayStats[i][4]), Integer.parseInt(dayStats[i][3])));
                        }
                        else{
                            Map<String, ShootingStatistics> shootingStatsMap = new HashMap<String, ShootingStatistics>();
                            shootingStatsMap.put(dayStats[i][0], new ShootingStatistics(Integer.parseInt(dayStats[i][6]), Integer.parseInt(dayStats[i][5]), Integer.parseInt(dayStats[i][4]), Integer.parseInt(dayStats[i][3])));
                            roster.getPlayers().get(dayStats[i][2]).setShootingStats(shootingStatsMap);
                        }
                        
                    }
                }
                
                for (int j = 0; j < playerStats.length; j++) {

                    playerStats[j][2] = roster.getPlayers().get(playerStats[j][1]).getShootingStats().get(dayStats[j][0]).calculateFreeThrowPercentage() + "";
                    playerStats[j][3] = roster.getPlayers().get(playerStats[j][1]).getShootingStats().get(dayStats[j][0]).calculateThreePointPercentage() + "";
                }


                

           
            

            DayStatsTable.setModel(new DefaultTableModel(dayStats, new String[]{"Date", "Number","Name", "Free Throws Made", "Free Throws Attempted",
            "Three Pointers Made", "Three Pointers Attempted"}));

            PlayerTable.setModel(new DefaultTableModel(playerList, new String[]{"Number","Name",  "Year"}));


            PlayerStatsTable.setModel(new DefaultTableModel(playerStats, new String[] {"Number", "Name", "Free Throws %", "Three Pointers %"}));

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
                        roster.removePlayer(PlayerTable.getValueAt(playerRow, 1).toString());
                        // Remove player from database
                        String playerName = PlayerTable.getValueAt(playerRow, 1).toString();
                        try {
                        Connection conn = roster.connect();
                        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Player WHERE name = ?");                            pstmt.setString(1, playerName);
                            pstmt.executeUpdate();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        

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
