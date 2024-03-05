import java.util.HashMap;
import java.util.Map;

class BasketballPlayer {
    private String name;
    private int number;
    private String position;
    private int grade;
    private int year;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Map<String, ShootingStatistics> getShootingStats() {
        return shootingStats;
    }

    public void setShootingStats(Map<String, ShootingStatistics> shootingStats) {
        this.shootingStats = shootingStats;
    }

    

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