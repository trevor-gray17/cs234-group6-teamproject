import java.util.HashMap;

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
    
}