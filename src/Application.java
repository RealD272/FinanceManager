import java.util.Scanner;

public class Application {
    private User currentUser;
    private DatabaseHandler dbHandler;

    public Application() {
        dbHandler = new DatabaseHandler();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Personal Finance Tracker!");

        boolean authenticated = false;
        while (!authenticated) {
            System.out.println("Choose an option: 1. Register 2. Login");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    authenticated = registerUser(scanner);
                    break;
                case 2:
                    authenticated = loginUser(scanner);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("Choose an option: 1. Add Expense 2. View Budget 3. Set Savings Goal 4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewBudget();
                    break;
                case 3:
                    setSavingsGoal(scanner);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

    private boolean registerUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Are you subscribing to premium features? (yes/no):");
        String subscriptionInput = scanner.nextLine();
        boolean isSubscribed = subscriptionInput.equalsIgnoreCase("yes");

        User newUser = new User(username, password, isSubscribed);
        boolean success = dbHandler.registerUser(newUser);
        if (success) {
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
        return success;
    }

    private boolean loginUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        currentUser = dbHandler.loginUser(username, password);
        if (currentUser != null) {
            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
            return true;
        } else {
            System.out.println("Login failed. Please check your username and password.");
            return false;
        }
    }

    private void addExpense(Scanner scanner) {
        System.out.println("Enter expense description:");
        String description = scanner.nextLine();
        System.out.println("Enter expense amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        Expense expense = new Expense(description, amount, date);
        dbHandler.addExpense(currentUser, expense);
        System.out.println("Expense added successfully.");
    }

    private void viewBudget() {
        Budget budget = dbHandler.getBudget(currentUser);
        if (budget != null) {
            System.out.println("Total Budget: " + budget.getTotalBudget());
            System.out.println("Amount Spent: " + budget.getSpent());
            System.out.println("Remaining Budget: " + budget.getRemainingBudget());
        } else {
            System.out.println("No budget found. Please set a budget first.");
        }
    }

    private void setSavingsGoal(Scanner scanner) {
        System.out.println("Enter savings goal amount:");
        double goalAmount = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character

        SavingsGoal savingsGoal = new SavingsGoal(goalAmount);
        dbHandler.setSavingsGoal(currentUser, savingsGoal);
        System.out.println("Savings goal set successfully.");
    }
}
