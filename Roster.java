import java.util.ArrayList;
import java.util.List;

public class Roster {
    private List<BasketballPlayer> players;

    public Roster() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(BasketballPlayer player) {
        players.add(player);
    }

    public boolean removePlayer(BasketballPlayer player) {
        return players.remove(player);
    }

    public List<BasketballPlayer> getPlayers() {
        return players;
    }
}
