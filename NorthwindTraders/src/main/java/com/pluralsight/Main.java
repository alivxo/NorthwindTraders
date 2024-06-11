package com.pluralsight;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "YUm15510n";

        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. Open a connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create statement
            // The statement is tied to the open connection
            Statement statement = connection.createStatement();

            // Define your query
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

            // Execute your query
            ResultSet results = statement.executeQuery(query);

            System.out.println("Northwind Traders Products");
            // Process the results
            while (results.next()) {
//                int productID = results.getInt("ProductID");
//                String productName = results.getString("ProductName");
//                int supplierID = results.getInt("SupplierID");
//                int categoryID = results.getInt("CategoryID");
//                String quantityPerUnit = results.getString("QuantityPerUnit");
//                double unitPrice = results.getDouble("UnitPrice");
//                int unitsInStock = results.getInt("UnitsInStock");
//                int unitsOnOrder = results.getInt("UnitsOnOrder");
//
//                System.out.println("ProductID: " + productID + ", ProductName: " + productName +
//                        ", SupplierID: " + supplierID + ", CategoryID: " + categoryID + ", QuantityPerUnit: "
//                        + quantityPerUnit + ", UnitPrice: " + unitPrice + ", UnitsInStock: " + unitsInStock );

                int productID = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                System.out.println("Product ID: " + productID);
                System.out.println("Name: " + productName);
                System.out.println("Price: " + unitPrice);
                System.out.println("Stock: " + unitsInStock);
                System.out.println("------------------");

            }


            // 3. Close the connection
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
