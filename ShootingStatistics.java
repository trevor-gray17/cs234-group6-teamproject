class ShootingStatistics {
    private double fieldGoalPercentage;
    private double threePointPercentage;
    private double freeThrowPercentage;

    public ShootingStatistics(double fieldGoalPercentage, double threePointPercentage, double freeThrowPercentage) {
        this.fieldGoalPercentage = fieldGoalPercentage;
        this.threePointPercentage = threePointPercentage;
        this.freeThrowPercentage = freeThrowPercentage;
    }

    public double getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public double getThreePointPercentage() {
        return threePointPercentage;
    }

    public double getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public void setFieldGoalPercentage(double fieldGoalPercentage) {
        this.fieldGoalPercentage = fieldGoalPercentage;
    }

    public void setThreePointPercentage(double threePointPercentage) {
        this.threePointPercentage = threePointPercentage;
    }

    public void setFreeThrowPercentage(double freeThrowPercentage) {
        this.freeThrowPercentage = freeThrowPercentage;
    }

    @Override
    public String toString() {
        return "ShootingStatistics{" +
                "fieldGoalPercentage=" + fieldGoalPercentage +
                ", threePointPercentage=" + threePointPercentage +
                ", freeThrowPercentage=" + freeThrowPercentage +
                '}';
    }
}
