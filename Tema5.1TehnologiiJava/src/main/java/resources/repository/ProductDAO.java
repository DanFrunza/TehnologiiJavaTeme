package resources.repository;

import jakarta.enterprise.inject.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import resources.model.Product;

import java.util.List;

@Model
public class ProductDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> getAllProducts() {
        return entityManager.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    public Product getProductById(int id) {
        return entityManager.find(Product.class, id);
    }

    public void createProduct(Product client) {
        entityManager.persist(client);
    }

    public void updateProduct(Product client) {
        entityManager.merge(client);
    }

    public void deleteProductById(int id) {
        entityManager.remove(entityManager.find(Product.class, id));
    }
}
