package com.mycompany.tema4tehnologiijava;

import com.mycompany.tema4tehnologiijava.Item;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class GenericBean<T extends Item> {

    @Resource(name = "DB")
    private DataSource dataSource;

    private List<T> items;   // Lista generică de itemi
    private T selectedItem;  // Item-ul selectat
    private Class<T> entityType;  // Tipul entității curente (Client sau Product)

    // Constructor
    public GenericBean(Class<T> entityType) {
        this.entityType = entityType;
    }

    // Metoda pentru a încărca itemii din baza de date în funcție de tipul de entitate
    public List<T> getItems() {
        if (items == null) {
            loadItems();  // Încarcă itemii din baza de date
        }
        return items;
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
    }

    private void loadItems() {
        items = new ArrayList<>();
        String query = "";

        if (entityType == Client.class) {
            query = "SELECT * FROM clients";
        } else if (entityType == Product.class) {
            query = "SELECT * FROM products";
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                T item = entityType.getDeclaredConstructor().newInstance();

                if (item instanceof Client) {
                    Client client = (Client) item;
                    client.setId(resultSet.getInt("id"));
                    client.setName(resultSet.getString("name"));
                    client.setEmail(resultSet.getString("email"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setRegistrationDate(resultSet.getDate("registration_date"));
                } else if (item instanceof Product) {
                    Product product = (Product) item;
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                }

                items.add(item);  // Adaugăm item-ul la listă
            }
            System.out.println(entityType.getSimpleName() + "s loaded: " + items.size());
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    // Metode pentru gestionarea selecției
    public void onRowSelect() {
        System.out.println("Selected " + entityType.getSimpleName() + ": " + selectedItem.getName());
    }

    public void onRowUnselect() {
        System.out.println("Unselected " + entityType.getSimpleName());
    }
}
