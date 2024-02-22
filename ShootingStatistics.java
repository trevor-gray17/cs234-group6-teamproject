class ShootingStatistics {
    private double fieldGoalPercentage;
    private double threePointPercentage;
    private double freeThrowPercentage;

    public ShootingStatistics(double fieldGoalPercentage, double threePointPercentage, double freeThrowPercentage) {
        this.fieldGoalPercentage = fieldGoalPercentage;
        this.threePointPercentage = threePointPercentage;
        this.freeThrowPercentage = freeThrowPercentage;
    }

    // Getters and setters

    @Override
    public String toString() {
        return "ShootingStatistics{" +
                "fieldGoalPercentage=" + fieldGoalPercentage +
                ", threePointPercentage=" + threePointPercentage +
                ", freeThrowPercentage=" + freeThrowPercentage +
                '}';
    }

    
}
