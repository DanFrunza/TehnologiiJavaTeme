/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema3tehnologiijava;

/**
 *
 * @author Dan
 */
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean implements Serializable {

    private List<Product> products;

    @PostConstruct
    public void init() {
        ProductService productService = new ProductService();
        try {
            products = productService.getProducts();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts() {
        return products;
    }
    
    public String redirectToProducts() {
        return "products"; // Asigură-te că 'products' se referă la pagina ta XHTML
    }
}

