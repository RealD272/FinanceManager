public class Budget {
    private double totalBudget;
    private double spent;

    public Budget(double totalBudget, double spent) {
        this.totalBudget = totalBudget;
        this.spent = spent;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public double getRemainingBudget() {
        return totalBudget - spent;
    }
}
