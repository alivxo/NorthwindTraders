package com.pluralsight;
import java.sql.*;
import java.util.Scanner;

import static com.pluralsight.DataSource.dataSource;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    final static Scanner scanner = new Scanner (System.in);
    public static void main(String[] args) {
        try (Connection connection = dataSource.getConnection()) {
            // 1. Open a connection to the database
            Statement statement = connection.createStatement();

            // (Rest of the code...)

        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        homeScreen();
        }

        private static void homeScreen() {
            String homeScreen = """
                Welcome to Northwind Traders Database
                -------------------------------------
                What do you want to do?
                1. Display all products
                2. Display all customers
                3. Display all categories
                0. Exit 
                Select an option:
                """;
            System.out.println(homeScreen);
            String userchoice = scanner.nextLine();

            boolean choice = true;

            while(choice) {
                switch (userchoice) {
                    case "1":
                        displayproducts();
                        break;
                    case "2":
                        displaycustomers();
                        break;
                    case "3":
                        displayallCategories();
                        break;
                    case "0":
                        choice = false;
                        break;
                    default:
                        System.out.println("Invalid option, please try again");
                }
            }
        }

    private static void displaycustomers() {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "YUm15510n";

        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Define your query
            String query = "SELECT CustomerID, CompanyName, ContactName, Country FROM customers";

            // Execute your query
            ResultSet results = statement.executeQuery(query);

            System.out.println("Northwind Trader Customer Information");
            while (results.next()) {
                String customerID = results.getString("CustomerID");
                String companyName = results.getString("CompanyName");
                String contactName = results.getString("ContactName");
                String phoneNumber = results.getString("PhoneNumber");
                String city = results.getString("City");
                String country = results.getString("Country");

                System.out.println("Customer ID: " + customerID);
                System.out.println("Company Name: " + companyName);
                System.out.println("Contact Name: " + contactName);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println("Country: " + country);
                System.out.println("City: " + city);
                System.out.println("------------------");
            }
            // Close the connection
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }

    }

    private static void displayproducts() {

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

    private static void displayallCategories() {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "YUm15510n";

        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement categoryStatement = connection.createStatement();
                 Scanner scanner = new Scanner(System.in)) {

                // Query the Categories table
                String categoryQuery = "SELECT CategoryID, CategoryName, Description FROM categories ORDER BY CategoryID";
                ResultSet categoryResults = categoryStatement.executeQuery(categoryQuery);

                // Display categories
                System.out.println("Categories:");
                while (categoryResults.next()) {
                    int categoryId = categoryResults.getInt("CategoryID");
                    String categoryName = categoryResults.getString("CategoryName");
                    String description = categoryResults.getString("Description");

                    System.out.println("Category ID: " + categoryId);
                    System.out.println("Name: " + categoryName);
                    System.out.println("Description: " + description);
                    System.out.println("------------------");
                }

                // Prompt user for a category ID
                System.out.print("Enter a category ID to display all products in that category: ");
                int categoryId = scanner.nextInt();

                // Query the Products table for the selected category
                String productQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                        "FROM products WHERE CategoryID = ?";
                try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                    productStatement.setInt(1, categoryId);
                    ResultSet productResults = productStatement.executeQuery();

                    // Display products
                    System.out.println("Products:");
                    while (productResults.next()) {
                        int productId = productResults.getInt("ProductID");
                        String productName = productResults.getString("ProductName");
                        double unitPrice = productResults.getDouble("UnitPrice");
                        int unitsInStock = productResults.getInt("UnitsInStock");

                        System.out.println("Product ID: " + productId);
                        System.out.println("Name: " + productName);
                        System.out.println("Price: " + String.format("%.2f", unitPrice));
                        System.out.println("Stock: " + unitsInStock);
                        System.out.println("------------------");
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
