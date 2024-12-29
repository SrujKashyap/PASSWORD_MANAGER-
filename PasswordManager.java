import java.sql.*;
import java.util.Scanner;

public class PasswordManager {

    // Database connection setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/PasswordManager"; // Change the database name if different
    private static final String DB_USER = "root";  // Change to your MySQL username
    private static final String DB_PASS = "W7301@jqir5#";      // Change to your MySQL password

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            System.out.println("Connected to the database!");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nPassword Manager Options:");
                System.out.println("1. Add Account");
                System.out.println("2. View Accounts");
                System.out.println("3. Update Password");
                System.out.println("4. Delete Account");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> addAccount(connection, scanner);
                    case 2 -> viewAccounts(connection);
                    case 3 -> updatePassword(connection, scanner);
                    case 4 -> deleteAccount(connection, scanner);
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
         catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // Add a new account
    private static void addAccount(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter platform: ");
        String platform = scanner.next();
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        String sql = "INSERT INTO Accounts (platform, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, platform);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("Account added successfully!");
        }
    }

    // View all accounts
    private static void viewAccounts(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Accounts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nStored Accounts:");
            while (rs.next()) {
                System.out.printf("ID: %d | Platform: %s | Username: %s | Password: %s%n",
                        rs.getInt("id"), rs.getString("platform"), rs.getString("username"), rs.getString("password"));
            }
        }
    }

    // Update an account's password
    private static void updatePassword(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter account ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();

        String sql = "UPDATE Accounts SET password = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Account not found.");
            }
        }
    }

    // Delete an account
    private static void deleteAccount(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter account ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Accounts WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Account not found.");
            }
        }
    }
}
