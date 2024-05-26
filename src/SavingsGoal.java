public class SavingsGoal {
    private double goalAmount;
    private double currentSavings;

    public SavingsGoal(double goalAmount) {
        this.goalAmount = goalAmount;
        this.currentSavings = 0.0; // Initial savings is 0
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getCurrentSavings() {
        return currentSavings;
    }

    public void setCurrentSavings(double currentSavings) {
        this.currentSavings = currentSavings;
    }
}
