package com.cdac.assignment;
import java.sql.*;
import java.util.Scanner;

public class UserManagementSystem {

	


	/*
	 * Outline of the Application: Change Password: Update the password for a user.
	 * Delete User: Delete a user record based on a username or ID. User SignIn:
	 * Authenticate a user by checking username and password. Reading JDBC Concepts:
	 * Provide some basic explanations about JDBC concepts
	 */
	
	    
	    private static final String URL = "jdbc:mysql://localhost:3306/user_db";
	    private static final String USER = "root"; // replace with your MySQL username
	    private static final String PASSWORD = "password"; // replace with your MySQL password

	    // Method to establish the connection with the database
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }

	    // User SignIn: Authenticate user by checking username and password
	    public static boolean userSignIn(Scanner scanner) {
	        System.out.print("Enter username: ");
	        String username = scanner.nextLine();
	        System.out.print("Enter password: ");
	        String password = scanner.nextLine();

	        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    System.out.println("Sign-in successful!");
	                    return true;
	                } else {
	                    System.out.println("Invalid username or password.");
	                    return false;
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error during sign-in: " + e.getMessage());
	            return false;
	        }
	    }

	    // Change password for a user
	    public static void changePassword(Scanner scanner) {
	        System.out.print("Enter username: ");
	        String username = scanner.nextLine();
	        System.out.print("Enter current password: ");
	        String currentPassword = scanner.nextLine();
	        System.out.print("Enter new password: ");
	        String newPassword = scanner.nextLine();

	        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, username);
	            stmt.setString(2, currentPassword);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    // If user exists, update password
	                    String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
	                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
	                        updateStmt.setString(1, newPassword);
	                        updateStmt.setString(2, username);
	                        int rowsUpdated = updateStmt.executeUpdate();
	                        if (rowsUpdated > 0) {
	                            System.out.println("Password updated successfully.");
	                        } else {
	                            System.out.println("Error updating password.");
	                        }
	                    }
	                } else {
	                    System.out.println("Invalid username or current password.");
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error during password change: " + e.getMessage());
	        }
	    }

	    // Delete user by username
	    public static void deleteUser(Scanner scanner) {
	        System.out.print("Enter username to delete: ");
	        String username = scanner.nextLine();

	        String query = "DELETE FROM users WHERE username = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, username);

	            int rowsDeleted = stmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("User deleted successfully.");
	            } else {
	                System.out.println("User not found.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error deleting user: " + e.getMessage());
	        }
	    }

	    // Reading JDBC concepts: basic explanation
	    public static void readJDBCConcepts() {
	        System.out.println("\n--- JDBC Concepts ---");
	        System.out.println("1. **JDBC (Java Database Connectivity)** allows Java programs to interact with relational databases.");
	        System.out.println("2. **Connection**: Establishes a connection to the database using the database URL, username, and password.");
	        System.out.println("3. **Statement**: A `Statement` object is used to execute SQL queries and updates.");
	        System.out.println("4. **PreparedStatement**: A `PreparedStatement` is used for precompiled SQL statements, which can prevent SQL injection attacks.");
	        System.out.println("5. **ResultSet**: The `ResultSet` object holds the result set of a query, allowing you to retrieve data from the database.");
	        System.out.println("6. **Exception Handling**: JDBC operations should be enclosed in try-catch blocks to handle SQL exceptions.");
	    }

	    // Main method with menu to interact with the application
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("\n--- User Management System ---");
	            System.out.println("1. User SignIn");
	            System.out.println("2. Change Password");
	            System.out.println("3. Delete User");
	            System.out.println("4. Read JDBC Concepts");
	            System.out.println("5. Exit");
	            System.out.print("Choose an option: ");
	            int choice = Integer.parseInt(scanner.nextLine());

	            switch (choice) {
	                case 1:
	                    if (userSignIn(scanner)) {
	                        System.out.println("Welcome to the system.");
	                    }
	                    break;
	                case 2:
	                    changePassword(scanner);
	                    break;
	                case 3:
	                    deleteUser(scanner);
	                    break;
	                case 4:
	                    readJDBCConcepts();
	                    break;
	                case 5:
	                    System.out.println("Exiting...");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Try again.");
	            }
	        }
	    }
	
}
