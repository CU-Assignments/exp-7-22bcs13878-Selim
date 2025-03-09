import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "userAdmin";
    private static final String PASSWORD = "pass2346";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false); // Enable transaction handling
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("\n1. Insert Product");
                System.out.println("2. Read Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        insertProduct(conn, scanner);
                        break;
                    case 2:
                        readProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, scanner);
                        break;
                    case 4:
                        deleteProduct(conn, scanner);
                        break;
                    case 5:
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertProduct(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter Product Name: ");
            String name = scanner.next();
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            
            String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.executeUpdate();
                conn.commit();
                System.out.println("Product inserted successfully.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    private static void readProducts(Connection conn) {
        String query = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("ProductID | ProductName | Price | Quantity");
            System.out.println("------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("ProductID") + " | " + rs.getString("ProductName") + " | " + rs.getDouble("Price") + " | " + rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = scanner.nextInt();
            System.out.print("Enter new Price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter new Quantity: ");
            int quantity = scanner.nextInt();
            
            String sql = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, price);
                pstmt.setInt(2, quantity);
                pstmt.setInt(3, id);
                pstmt.executeUpdate();
                conn.commit();
                System.out.println("Product updated successfully.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = scanner.nextInt();
            
            String sql = "DELETE FROM Product WHERE ProductID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                conn.commit();
                System.out.println("Product deleted successfully.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }
}
