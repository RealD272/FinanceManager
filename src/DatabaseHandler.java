import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler() {
        connection = DatabaseConnection.getConnection();
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password, is_subscribed) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isSubscribed());
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                boolean isSubscribed = rs.getBoolean("is_subscribed");
                return new User(id, username, password, isSubscribed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSavingsGoal(User currentUser, SavingsGoal savingsGoal) {
        String query = "INSERT INTO savings_goals (user_id, goal_amount, current_savings) VALUES (?, ?, ?)"
                + " ON DUPLICATE KEY UPDATE goal_amount = VALUES(goal_amount), current_savings = VALUES(current_savings)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, currentUser.getId());
            stmt.setDouble(2, savingsGoal.getGoalAmount());
            stmt.setDouble(3, savingsGoal.getCurrentSavings());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExpense(User currentUser, Expense expense) {
        String query = "INSERT INTO expenses (user_id, description, amount, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, currentUser.getId());
            stmt.setString(2, expense.getDescription());
            stmt.setDouble(3, expense.getAmount());
            stmt.setString(4, expense.getDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Budget getBudget(User currentUser) {
        String query = "SELECT * FROM budgets WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, currentUser.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double totalBudget = rs.getDouble("total_budget");
                double spent = rs.getDouble("spent");
                return new Budget(totalBudget, spent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        DatabaseConnection.closeConnection();
    }
}
