import javax.swing.*;
import java.awt.*;

public class TeamRosterGUI {
    private JFrame frame;
    private DefaultListModel<String> teamListModel;
    private JList<String> teamList;

    public TeamRosterGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Team Roster Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        teamListModel = new DefaultListModel<>();
        teamList = new JList<>(teamListModel);
        frame.add(new JScrollPane(teamList), BorderLayout.CENTER);

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

        // The functionality of the buttons for the players will use the basketball player class
        // The functionality of the buttons for the shooting statistics will use the shooting statistics class
        // The two classes mentioned will need more improvements to be able to use the buttons
        
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
