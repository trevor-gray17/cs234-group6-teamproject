import java.util.ArrayList;

public class Roster {
    private ArrayList<BasketballPlayer> players;

    public Roster() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(BasketballPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(BasketballPlayer player) {
        this.players.remove(player);
    }

    public ArrayList<BasketballPlayer> getPlayers() {
        return players;
    }
}
