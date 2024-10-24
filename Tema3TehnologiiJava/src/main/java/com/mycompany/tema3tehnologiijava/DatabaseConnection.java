/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema3tehnologiijava;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
/**
 *
 * @author Dan
 */
public class DatabaseConnection {
    private static Connection connection;

    // Metodă pentru a obține conexiunea la baza de date
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Încarcă proprietățile din fișierul db.properties
                Properties props = new Properties();
                try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    props.load(input);
                }

                // Conectează-te la baza de date
                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                // Încarcă driverul PostgreSQL
                Class.forName(props.getProperty("db.driver"));
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Conexiune reușită la baza de date!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Închide conexiunea
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
