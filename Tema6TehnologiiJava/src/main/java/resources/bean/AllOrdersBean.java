package resources.bean;

import jakarta.ejb.*;
import jakarta.inject.Named;
import resources.model.Product;
import resources.repository.ProductDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Singleton
@Startup
@Stateful
@Named
public class AllOrdersBean implements Serializable {
    @EJB
    private ProductDAO productDAO;
    List<Map<Integer, Integer>> orders = new ArrayList<>();
    public class OrderEntry {
        public Product product;
        public Integer quantity;
        public String order;
        public OrderEntry(Product product, Integer quantity, String order) {
            this.product = product;
            this.quantity = quantity;
            this.order = order;
        }
        public Integer getQuantity() {
            return quantity;
        }
        public Product getProduct() {
            return product;
        }
        public String getOrder() {
            return order;
        }
    }
    public List<OrderEntry> getOrdersArray() {
        List<OrderEntry> l = new ArrayList<>();
        for (Map<Integer, Integer> map : orders) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                l.add(new OrderEntry(productDAO.getProductById(entry.getKey()), entry.getValue(), String.valueOf(map.hashCode())));
            }
        }
        return l;
    }
    public List<Map<Integer, Integer>> getOrders() {
        return orders;
    }
    @Lock
    public void setOrders(List<Map<Integer, Integer>> orders) {
        this.orders = orders;
    }
    @Lock
    public void addOrder(Map<Integer, Integer> order) {
        orders.add(order);
    }
}