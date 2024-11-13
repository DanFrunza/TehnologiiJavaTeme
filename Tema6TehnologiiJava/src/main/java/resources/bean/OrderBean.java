package resources.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Remove;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.transaction.Transactional;
import jdk.jfr.Name;
import resources.model.Product;
import resources.repository.ProductDAO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Stateful
@SessionScoped
@Named
public class OrderBean implements Serializable {
    @EJB
    private ProductDAO productDAO;
    @EJB
    private AllOrdersBean allOrdersBean;
    private Map<Integer, Integer> order = new HashMap<>();
    public Integer getProductOrder(Product product) {
        System.out.println(order);
        Integer x = order.get(product.getId());
        if(x == null) {
            return 0;
        }
        return x;
    }
    public void setProductOrder(Product product, Integer quantity) {
        order.put(product.getId(), quantity);
    }
    public void increaseProductOrder(Product product) {
        order.put(product.getId(), getProductOrder(product) + 1);
    }
    @Remove
    public void decreaseProductOrder(Product product) {
        if (getProductOrder(product) > 0) {
            order.put(product.getId(), getProductOrder(product) - 1);
        }
    }
    public Map<Integer, Integer> getOrder() {
        return order;
    }
    public void setOrder(Map<Integer, Integer> order) {
        this.order = order;
    }
    public void register() throws Exception {
        for(Map.Entry<Integer, Integer> entry : order.entrySet()) {
            Product product = productDAO.getProductById(entry.getKey());
            if(product.getStock() > entry.getValue()) {
                product.setStock(product.getStock() - entry.getValue());
            } else {
                throw new Exception("Too big order");
            }
        }
        allOrdersBean.addOrder(order);
        order = new HashMap<>();
    }
    @AroundInvoke
    public Object log (InvocationContext ctx) throws Exception {
        String className = ctx.getTarget().getClass().getName();
        String methodName = ctx.getMethod().getName();
        String target = className + "." + methodName + "()";
        long t1 = System.currentTimeMillis();
        try {
            return ctx.proceed();
        } catch(Exception e) {
            throw e;
        } finally {
            long t2 = System.currentTimeMillis();
            System.out.println(target + " took " +
                    (t2-t1) + "ms to execute");
        }
    }
}