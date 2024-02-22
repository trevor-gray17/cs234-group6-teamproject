import java.util.Map;

public class ViewingStats {
    Map<String, ShootingStatistics> shootingStats;

    public ViewingStats(Map<String, ShootingStatistics> shootingStats) {
        this.shootingStats = shootingStats;
    }

    public double FreeThrowsPercentage() {
        double FreeThrowsMade = 0.0;
        double FreeThrowsAttempted =0.0;
        for (ShootingStatistics stats : shootingStats.values()) {
            FreeThrowsMade += stats.getFreeThrowsMade();
            FreeThrowsAttempted += stats.getFreeThrowsAttempted();
        }
        return FreeThrowsMade / FreeThrowsAttempted * 100;
    }

    public double ThreePointersPercentage() {
        double ThreePointersMade = 0.0;
        double ThreePointersAttempted = 0.0;
        for (ShootingStatistics stats : shootingStats.values()) {
            ThreePointersMade += stats.getThreePointersMade();
            ThreePointersAttempted += stats.getThreePointersAttempted();
        }
        return ThreePointersMade / ThreePointersAttempted * 100;
    }
    
    

}