package com.baeldung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        VulnerableDatabaseAccess dbAccess = new VulnerableDatabaseAccess();
        dbAccess.getUserData("admin' OR '1'='1");
    }
}

class VulnerableDatabaseAccess {

    public void getUserData(String username) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establishing a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "user", "password");
            statement = connection.createStatement();

            // Vulnerable SQL query with user input directly concatenated
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            resultSet = statement.executeQuery(query);

            // Processing the result set
            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("id"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email: " + resultSet.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("An error occurred:" + e);
        } finally {
            // Closing resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("An error occurred:" + e);
            }
        }
    }

}