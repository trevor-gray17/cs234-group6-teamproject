import java.util.HashMap;
import java.util.Map;


class BasketballPlayer {
    private String name;
    private int number;
    private int year;
    private Map<String, ShootingStatistics> shootingStats;

    public BasketballPlayer(int number, String name, int year){
        this.name = name;
        this.number = number;
        this.year = year;
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

    public Map<String, ShootingStatistics> getShootingStats() {
        return shootingStats;
    }

    public void setShootingStats(Map<String, ShootingStatistics> shootingStats) {
        this.shootingStats = shootingStats;
        this.shootingStats.replace(name, shootingStats.get(shootingStats));
    }

    

    @Override
    public String toString() {
        return "BasketballPlayer{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", shootingStats=" + shootingStats +
                '}';
    }


}

