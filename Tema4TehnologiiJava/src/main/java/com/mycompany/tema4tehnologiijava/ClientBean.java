package com.mycompany.tema4tehnologiijava;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ClientBean {

    @Resource(name = "DB")
    private DataSource dataSource;

    private List<Client> clients;
    private Client selectedClient;

    // Constructor (optional)
    public ClientBean() {
        // Optional constructor
    }

    // Getter for clients
    public List<Client> getClients() {
        if (clients == null) {
            loadClients();
        }
        return clients;
    }

    // Getter for selectedClient
    public Client getSelectedClient() {
        return selectedClient;
    }

    // Setter for selectedClient
    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    // Load clients from the database
    private void loadClients() {
        clients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM clients")) {

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getString("phone"));
                client.setRegistrationDate(resultSet.getDate("registration_date"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method called when a row is selected
    public void onRowSelect() {
        if (selectedClient != null) {
            System.out.println("Selected Client: " + selectedClient.getName());
        }
    }

    // Method called when a row is unselected
    public void onRowUnselect() {
        System.out.println("Unselected Client");
        selectedClient = null; // Reset the selected client
    }

    // Method to add a new client
    public void addClient(Client client) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO clients (name, email, phone, registration_date) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setDate(4, new java.sql.Date(client.getRegistrationDate().getTime()));
            statement.executeUpdate();
            loadClients(); // Reload clients after adding
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a client
    public void updateClient() {
        if (selectedClient != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                     "UPDATE clients SET name = ?, email = ?, phone = ?, registration_date = ? WHERE id = ?")) {
                statement.setString(1, selectedClient.getName());
                statement.setString(2, selectedClient.getEmail());
                statement.setString(3, selectedClient.getPhone());
                statement.setDate(4, new java.sql.Date(selectedClient.getRegistrationDate().getTime()));
                statement.setInt(5, selectedClient.getId());
                statement.executeUpdate();
                loadClients(); // Reload clients after updating
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to delete a client
    public void deleteClient(int clientId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM clients WHERE id = ?")) {
            statement.setInt(1, clientId);
            statement.executeUpdate();
            loadClients(); // Reload clients after deleting
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
