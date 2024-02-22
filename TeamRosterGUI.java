import javax.swing.*;
import java.awt.*;

public class TeamRosterGUI {
    private JFrame frame;


    public TeamRosterGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Team Roster Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
    
        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
    
        // Create tabs for different information
        JPanel playerPanel = editRosterPanel();
        JPanel statisticsPanel = createStatisticsPanel();
    
        // Add tabs to the tabbedPane
        tabbedPane.addTab("Roster", playerPanel);
        tabbedPane.addTab("Statistics", statisticsPanel);
    
        frame.add(tabbedPane, BorderLayout.CENTER);
    
        frame.setVisible(true);
    }
    
    
    private JPanel editRosterPanel() {
        JPanel panel = new JPanel();
        // Add components related to player information
        JTextField memberField = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        // The functionality of the buttons for the players will use the basketball player class
        // The functionality of the buttons for the shooting statistics will use the shooting statistics class
        // The two classes mentioned will need more improvements to be able to use the buttons

        panel.add(memberField);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        return panel;
    }
    
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel();
        // Add components related to statistics information
        // The functionality of the buttons for the players will use the basketball player class
        // The functionality of the buttons for the shooting statistics will use the shooting statistics class
        // The two classes mentioned will need more improvements to be able to use the buttons

        return panel;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamRosterGUI::new);
    }
    
}
