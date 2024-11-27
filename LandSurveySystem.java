import java.sql.*;
import java.util.Scanner;

public class LandSurveySystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/LandSurveyDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Method to connect to the database
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to add land details and calculate area, then save to the database
    public static void addLandDetails(Scanner sc) {
        System.out.print("Enter Plot Name: ");
        String plotName = sc.next();

        System.out.print("Enter Length of the land (in meters): ");
        double length = sc.nextDouble();

        System.out.print("Enter Width of the land (in meters): ");
        double width = sc.nextDouble();

        double area = length * width;

        // Save land details to the database
        try (Connection conn = connect()) {
            String sql = "INSERT INTO Land (plotName, length, width, area) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, plotName);
                pstmt.setDouble(2, length);
                pstmt.setDouble(3, width);
                pstmt.setDouble(4, area);
                pstmt.executeUpdate();
                System.out.println("Land details added successfully to the database!\n");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to display all the land details from the database
    public static void displayAllLandDetails() {
        System.out.println("\nDisplaying All Land Details from Database:");
        String sql = "SELECT plotName, length, width, area FROM Land";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String plotName = rs.getString("plotName");
                double length = rs.getDouble("length");
                double width = rs.getDouble("width");
                double area = rs.getDouble("area");

                System.out.println("Plot Name: " + plotName);
                System.out.println("Length: " + length + " meters");
                System.out.println("Width: " + width + " meters");
                System.out.println("Area: " + area + " square meters\n");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the Land Area Calculation and Survey System!");

        while (!exit) {
            System.out.println("\n1. Add Land Details");
            System.out.println("2. Display All Land Details");
            System.out.println("3. Exit");
            System.out.print("\nSelect an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addLandDetails(sc);
                    break;
                case 2:
                    displayAllLandDetails();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}
