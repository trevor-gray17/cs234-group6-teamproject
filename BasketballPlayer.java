import java.util.HashMap;
import java.util.Map;

class BasketballPlayer {
    private String name;
    private int number;
    private String position;
    private String grade;
    private Map<String, ShootingStatistics> shootingStats;

    public BasketballPlayer(String name, int number, String position, String grade) {
        this.name = name;
        this.number = number;
        this.position = position;
        this.grade = grade;
        this.shootingStats = new HashMap<>();
    }

    public void addShootingStats(String date, ShootingStatistics stats) {
        shootingStats.put(date, stats);
    }

    // Getters and setters

    @Override
    public String toString() {
        return "BasketballPlayer{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", position='" + position + '\'' +
                ", grade=" + grade +
                ", shootingStats=" + shootingStats +
                '}';
    }
}




// BasketballPlayer player1 = new BasketballPlayer("Player1", 1, "Forward", 10);
//         player1.addShootingStats("2024-02-19", new ShootingStatistics(0.5, 0.4, 0.8));
//         player1.addShootingStats("2024-02-17", new ShootingStatistics(0.6, 0.3, 0.7));
// Example of how to use the classes