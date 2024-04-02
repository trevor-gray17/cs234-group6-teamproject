import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.Statement;

public class Roster {
    private HashMap<String, BasketballPlayer> players;

    public Roster() {
        this.players = new HashMap<>();
    }

    public void addPlayer(BasketballPlayer player) {
        this.players.put(player.getName(), player); // Assuming name is unique and can be used as a key
    }

    public void updatePlayer(BasketballPlayer updatedPlayer) {
        // This implementation assumes the player's name is not changed.
        // If the name can change, you'll need a more complex update mechanism.
        players.put(updatedPlayer.getName(), updatedPlayer);
    }
    

    public void removePlayer(String playerName) {
        this.players.remove(playerName);
    }

    public HashMap<String, BasketballPlayer> getPlayers() {
        return players;
    } 
    
    public BasketballPlayer getPlayerByName(String name) {
        return players.get(name);
    }


    private Connection connect() {
        Connection conn = null;
        try {
            // Replace with your database details
            String url = "jdbc:mysql://localhost:3306/roster?user=root&password=Jillrusso";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    

public void saveRosterToDB() {
    String insertPlayerSQL = "INSERT INTO Player(number, name, year) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE year=VALUES(year);";
    String insertStatSQL = "INSERT INTO Stats_1(threePointersTaken, threePointersMade, freeThrowsTaken, freeThrowsMade, playerName, practice_date, PlayerID) VALUES(?, ?, ?, ?, ?, ?, ?);";
    String selectPlayerIDSQL = "SELECT PlayerID FROM Player WHERE name = ? AND number = ?;";

    try (Connection conn = this.connect();
         PreparedStatement pstmtPlayer = conn.prepareStatement(insertPlayerSQL, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement pstmtStat = conn.prepareStatement(insertStatSQL);
         PreparedStatement pstmtSelectPlayerID = conn.prepareStatement(selectPlayerIDSQL)) {

        for (BasketballPlayer player : players.values()) {
            // Insert or update player
            pstmtPlayer.setString(1, player.getName());
            pstmtPlayer.setInt(2, player.getNumber());
            pstmtPlayer.setInt(5, player.getYear());
            pstmtPlayer.executeUpdate();

            ResultSet rs = pstmtPlayer.getGeneratedKeys();
            long playerID = 0;
            if (rs.next()) {
                playerID = rs.getLong(1);
            } else {
                // If the player was not inserted but updated, retrieve existing PlayerID
                pstmtSelectPlayerID.setString(1, player.getName());
                pstmtSelectPlayerID.setInt(2, player.getNumber());
                ResultSet rsID = pstmtSelectPlayerID.executeQuery();
                if (rsID.next()) {
                    playerID = rsID.getLong("PlayerID");
                }
            }

            // Insert player's shooting stats
            for (Map.Entry<String, ShootingStatistics> entry : player.getShootingStats().entrySet()) {
                ShootingStatistics stat = entry.getValue();
                pstmtStat.setInt(1, stat.getThreePointersTaken());
                pstmtStat.setInt(2, stat.getThreePointersMade());
                pstmtStat.setInt(3, stat.getFreeThrowsTaken());
                pstmtStat.setInt(4, stat.getFreeThrowsMade());
                pstmtStat.setString(5, player.getName());
                pstmtStat.setString(6, entry.getKey()); // Assuming entry.getKey() is the practice_date
                pstmtStat.setLong(7, playerID);
                pstmtStat.executeUpdate();
            }
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}



public void loadRosterFromDB() {
    String selectPlayersSQL = "SELECT * FROM Player;";
    String selectStatsSQL = "SELECT * FROM Stats_1 WHERE PlayerID = ?;";

    try (Connection conn = this.connect();
         PreparedStatement pstmtPlayer = conn.prepareStatement(selectPlayersSQL);
         PreparedStatement pstmtStat = conn.prepareStatement(selectStatsSQL);
         ResultSet rsPlayers = pstmtPlayer.executeQuery()) {

        while (rsPlayers.next()) {
            BasketballPlayer player = new BasketballPlayer(
                rsPlayers.getString("name"),
                rsPlayers.getInt("number"),
                rsPlayers.getInt("year")
            );

            long playerID = rsPlayers.getLong("PlayerID");
            pstmtStat.setLong(1, playerID);
            ResultSet rsStats = pstmtStat.executeQuery();
            while (rsStats.next()) {
                ShootingStatistics stats = new ShootingStatistics(
                    rsStats.getInt("threePointersTaken"),
                    rsStats.getInt("threePointersMade"),
                    rsStats.getInt("freeThrowsTaken"),
                    rsStats.getInt("freeThrowsMade")
                );
                player.addShootingStats(rsStats.getString("practice_date"), stats); // Ensure ShootingStatistics constructor and addShootingStats support this
            }
            this.addPlayer(player); // Adds the player and their stats to the roster
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
    
}
