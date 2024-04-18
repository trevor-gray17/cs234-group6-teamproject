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


    public Connection connect() {
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
    String insertOrUpdateStatSQL = "INSERT INTO Stats_1 (practice_date, PlayerName, ThreePointersTaken, ThreePointersMade, FreeThrowsTaken, FreeThrowsMade, PlayerID) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE ThreePointersTaken = VALUES(ThreePointersTaken), ThreePointersMade = VALUES(ThreePointersMade), FreeThrowsTaken = VALUES(FreeThrowsTaken), FreeThrowsMade = VALUES(FreeThrowsMade)";
    String selectPlayerIDSQL = "SELECT PlayerID FROM Player WHERE Number = ? AND Name = ?;";

    try (Connection conn = this.connect();
         PreparedStatement pstmtPlayer = conn.prepareStatement(insertPlayerSQL, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement pstmtStat = conn.prepareStatement(insertOrUpdateStatSQL);
         PreparedStatement pstmtSelectPlayerID = conn.prepareStatement(selectPlayerIDSQL)) {



        for (BasketballPlayer player : players.values()) {
            // Insert or update player
            pstmtPlayer.setInt(1, player.getNumber());
            pstmtPlayer.setString(2, player.getName());
            pstmtPlayer.setInt(3, player.getYear());
            pstmtPlayer.executeUpdate();

            ResultSet rs = pstmtPlayer.getGeneratedKeys();
            long playerID = 0;
            if (rs.next()) {
                playerID = rs.getLong(1);
            } else {
                // If the player was not inserted but updated, retrieve existing PlayerID
                pstmtSelectPlayerID.setInt(1, player.getNumber());
                pstmtSelectPlayerID.setString(2, player.getName());
                ResultSet rsID = pstmtSelectPlayerID.executeQuery();
                if (rsID.next()) {
                    playerID = rsID.getLong("PlayerID");
                }
            }

            // Insert player's shooting stats

            for (Map.Entry<String, ShootingStatistics> entry : player.getShootingStats().entrySet()) {
                ShootingStatistics stat = entry.getValue();
                System.out.println(entry.getKey());

                


                System.out.println("Save to Database");
                pstmtStat.setString(1, entry.getKey()); // Assuming entry.getKey() is the practice_date
                pstmtStat.setString(2, player.getName());
                pstmtStat.setInt(3, stat.getThreePointersTaken());
                pstmtStat.setInt(4, stat.getThreePointersMade());
                pstmtStat.setInt(5, stat.getFreeThrowsTaken());
                pstmtStat.setInt(6, stat.getFreeThrowsMade());
                pstmtStat.setLong(7, playerID);
                pstmtStat.executeUpdate();
                //break;
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
         System.out.println("Loading roster from database...");


         while (rsPlayers.next()) {
            BasketballPlayer player = new BasketballPlayer(
                rsPlayers.getInt("number"),
                rsPlayers.getString("name"),
                rsPlayers.getInt("year")
            );

            long playerID = rsPlayers.getLong("PlayerID"); // Change getInt() to getLong()
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

                


                this.addPlayer(player);
                

        }
        System.out.println(players);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
    
}
