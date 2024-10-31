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
public class ProductBean {

    @Resource(name = "DB")
    private DataSource dataSource;

    private List<Product> products;
    private Product selectedProduct;

    // Constructor (optional)
    public ProductBean() {
        // Optional constructor
    }

    // Getter for products
    public List<Product> getProducts() {
        if (products == null) {
            loadProducts();
        }
        return products;
    }

    // Getter for selectedProduct
    public Product getSelectedProduct() {
        return selectedProduct;
    }

    // Setter for selectedProduct
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    // Load products from the database
    private void loadProducts() {
        products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method called when a row is selected
    public void onRowSelect() {
        if (selectedProduct != null) {
            System.out.println("Selected Product: " + selectedProduct.getName());
        }
    }

    // Method called when a row is unselected
    public void onRowUnselect() {
        System.out.println("Unselected Product");
        selectedProduct = null; // Reset the selected product
    }

    // Method to add a new product
    public void addProduct(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.executeUpdate();
            loadProducts(); // Reload products after adding
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a product
    public void updateProduct() {
        if (selectedProduct != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                     "UPDATE products SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?")) {
                statement.setString(1, selectedProduct.getName());
                statement.setString(2, selectedProduct.getDescription());
                statement.setDouble(3, selectedProduct.getPrice());
                statement.setInt(4, selectedProduct.getQuantity());
                statement.setInt(5, selectedProduct.getId());
                statement.executeUpdate();
                loadProducts(); // Reload products after updating
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to delete a product
    public void deleteProduct(int productId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            statement.setInt(1, productId);
            statement.executeUpdate();
            loadProducts(); // Reload products after deleting
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
