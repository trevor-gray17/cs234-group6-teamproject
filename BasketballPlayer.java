import java.util.HashMap;
import java.util.Map;

class BasketballPlayer {
    private String name;
    private int number;
    private String position;
    private int grade;
    private Map<String, ShootingStatistics> shootingStats;

    public BasketballPlayer(String name, int number, String position, int grade) {
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