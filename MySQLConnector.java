import java.sql.*;

public class MySQLConnector {
    public static void main(String[] args) {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/database"; 
        String user = "userAdmin"; 
        String password = "pass2346"; 

        // SQL Query to fetch data from Employee table
        String query = "SELECT EmpID, Name, Salary FROM Employee"; //Selim Jahangir 22bcs13878

        try (
            // Establishing a connection
            Connection conn = DriverManager.getConnection(url, user, password);
            // Creating a statement
            Statement stmt = conn.createStatement();
            // Executing the query
            ResultSet rs = stmt.executeQuery(query);
        ) {
            // Displaying the records
            System.out.println("EmpID | Name | Salary");
            System.out.println("--------------------------");
            while (rs.next()) {
                int empID = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(empID + " | " + name + " | " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
