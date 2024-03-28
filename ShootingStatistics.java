public class ShootingStatistics {
    private int threePointersTaken;
    private int threePointersMade;
    private int freeThrowsTaken;
    private int freeThrowsMade;

    public ShootingStatistics(int threePointersTaken, int threePointersMade, int freeThrowsTaken, int freeThrowsMade) {
        this.threePointersTaken = threePointersTaken;
        this.threePointersMade = threePointersMade;
        this.freeThrowsTaken = freeThrowsTaken;
        this.freeThrowsMade = freeThrowsMade;
    }

    public int getThreePointersTaken() {
        return threePointersTaken;
    }

    public void setThreePointersTaken(int threePointersTaken) {
        this.threePointersTaken = threePointersTaken;
    }

    public int getThreePointersMade() {
        return threePointersMade;
    }

    public void setThreePointersMade(int threePointersMade) {
        this.threePointersMade = threePointersMade;
    }

    public int getFreeThrowsTaken() {
        return freeThrowsTaken;
    }

    public void setFreeThrowsTaken(int freeThrowsTaken) {
        this.freeThrowsTaken = freeThrowsTaken;
    }

    public int getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public void setFreeThrowsMade(int freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
    }

    // Calculate and return the free throw percentage
    public double calculateFreeThrowPercentage() {
        if (freeThrowsTaken == 0) return 0;
        return ((double) freeThrowsMade / freeThrowsTaken) * 100;
    }

    // Calculate and return the three-point percentage
    public double calculateThreePointPercentage() {
        if (threePointersTaken == 0) return 0;
        return ((double) threePointersMade / threePointersTaken) * 100;
    }

    @Override
    public String toString() {
        return "ShootingStatistics{" +
                "threePointersTaken=" + threePointersTaken +
                ", threePointersMade=" + threePointersMade +
                ", freeThrowsTaken=" + freeThrowsTaken +
                ", freeThrowsMade=" + freeThrowsMade +
                '}';
    }
}
