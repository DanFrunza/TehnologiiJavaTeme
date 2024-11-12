package resources.model;

import jakarta.enterprise.inject.Model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "select p from Product p")
})
public class Product {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
