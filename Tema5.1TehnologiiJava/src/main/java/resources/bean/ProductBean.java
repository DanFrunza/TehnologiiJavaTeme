package resources.bean;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import resources.model.Product;
import resources.repository.ProductDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProductBean implements Serializable {
    @Inject
    private ProductDAO productDAO;

    private Product selectedProduct;

    public List<Product> getProducts() {
        List<Product> asd = productDAO.getAllProducts();
        System.out.println(asd);
        return asd;
    }

    @Transactional
    public void saveProduct(Product product) {
        if (product == null) {
            return;
        }
        if (product.getId() == 0)
            productDAO.createProduct(product);
        else
            productDAO.updateProduct(product);
    }

    @Transactional
    public void deleteProduct(Product product) {
        productDAO.deleteProductById(product.getId());
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void prepareNewProduct() {
        selectedProduct = new Product();
    }
}
