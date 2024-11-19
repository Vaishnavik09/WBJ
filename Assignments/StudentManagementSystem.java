package com.cdac.jdbc.assignment;
import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

	    
	    // JDBC URL, username, and password
	    private static final String URL = "jdbc:mysql://localhost:3306/school_db";
	    private static final String USER = "root"; // replace with your MySQL username
	    private static final String PASSWORD = "password"; // replace with your MySQL password
	    
	    // Create a connection to the database
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }

	    // Add a new student
	    public static void addStudent(Scanner scanner) {
	        System.out.print("Enter student name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter student age: ");
	        int age = Integer.parseInt(scanner.nextLine());
	        System.out.print("Enter student grade: ");
	        String grade = scanner.nextLine();
	        System.out.print("Enter student email: ");
	        String email = scanner.nextLine();

	        String query = "INSERT INTO students (name, age, grade, email) VALUES (?, ?, ?, ?)";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, name);
	            stmt.setInt(2, age);
	            stmt.setString(3, grade);
	            stmt.setString(4, email);

	            int rowsInserted = stmt.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("A new student was added successfully!");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error adding student: " + e.getMessage());
	        }
	    }

	    // View all students
	    public static void viewAllStudents() {
	        String query = "SELECT * FROM students";

	        try (Connection connection = getConnection();
	             Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery(query)) {

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                int age = rs.getInt("age");
	                String grade = rs.getString("grade");
	                String email = rs.getString("email");
	                System.out.printf("ID: %d, Name: %s, Age: %d, Grade: %s, Email: %s%n", 
	                                  id, name, age, grade, email);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error viewing students: " + e.getMessage());
	        }
	    }

	    // Update student details
	    public static void updateStudent(Scanner scanner) {
	        System.out.print("Enter student ID to update: ");
	        int id = Integer.parseInt(scanner.nextLine());
	        System.out.print("Enter new name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter new age: ");
	        int age = Integer.parseInt(scanner.nextLine());
	        System.out.print("Enter new grade: ");
	        String grade = scanner.nextLine();
	        System.out.print("Enter new email: ");
	        String email = scanner.nextLine();

	        String query = "UPDATE students SET name = ?, age = ?, grade = ?, email = ? WHERE id = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, name);
	            stmt.setInt(2, age);
	            stmt.setString(3, grade);
	            stmt.setString(4, email);
	            stmt.setInt(5, id);

	            int rowsUpdated = stmt.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Student details updated successfully.");
	            } else {
	                System.out.println("Student not found with ID: " + id);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error updating student: " + e.getMessage());
	        }
	    }

	    // Delete a student by ID
	    public static void deleteStudent(Scanner scanner) {
	        System.out.print("Enter student ID to delete: ");
	        int id = Integer.parseInt(scanner.nextLine());

	        String query = "DELETE FROM students WHERE id = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setInt(1, id);
	            int rowsDeleted = stmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Student deleted successfully.");
	            } else {
	                System.out.println("Student not found with ID: " + id);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error deleting student: " + e.getMessage());
	        }
	    }

	    // Search for a student by name or email
	    public static void searchStudent(Scanner scanner) {
	        System.out.print("Enter student name or email to search: ");
	        String searchTerm = scanner.nextLine();

	        String query = "SELECT * FROM students WHERE name LIKE ? OR email LIKE ?";

	        try (Connection connection = getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, "%" + searchTerm + "%");
	            stmt.setString(2, "%" + searchTerm + "%");

	            try (ResultSet rs = stmt.executeQuery()) {
	                boolean found = false;
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    String name = rs.getString("name");
	                    int age = rs.getInt("age");
	                    String grade = rs.getString("grade");
	                    String email = rs.getString("email");
	                    System.out.printf("ID: %d, Name: %s, Age: %d, Grade: %s, Email: %s%n", 
	                                      id, name, age, grade, email);
	                    found = true;
	                }
	                if (!found) {
	                    System.out.println("No student found with that name or email.");
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error searching for student: " + e.getMessage());
	        }
	    }

	    // Main menu for the student management system
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("\n--- Student Management System ---");
	            System.out.println("1. Add Student");
	            System.out.println("2. View All Students");
	            System.out.println("3. Update Student Details");
	            System.out.println("4. Delete Student");
	            System.out.println("5. Search Student");
	            System.out.println("6. Exit");
	            System.out.print("Choose an option: ");
	            int choice = Integer.parseInt(scanner.nextLine());

	            switch (choice) {
	                case 1:
	                    addStudent(scanner);
	                    break;
	                case 2:
	                    viewAllStudents();
	                    break;
	                case 3:
	                    updateStudent(scanner);
	                    break;
	                case 4:
	                    deleteStudent(scanner);
	                    break;
	                case 5:
	                    searchStudent(scanner);
	                    break;
	                case 6:
	                    System.out.println("Exiting...");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Try again.");
	            }
	        }
	    }
	
}
