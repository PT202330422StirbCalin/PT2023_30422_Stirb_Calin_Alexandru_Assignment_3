package org.example.Logic;

import org.example.DataAccess.ProductDAO;
import org.example.Model.Product;

import java.util.List;

/**
 * The ProductLogic class represents the logic for product-related operations in the application.
 * It provides methods for retrieving product names, inserting products, and updating product stock.
 */
public class ProductLogic {

    /**
     * Retrieves an array of product names from the database.
     *
     * @return an array of product names
     */
    public static String[] dropDownString(){
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        String[] productNames = new String[products.size()];
        int i =0;
        for(Product entry:products){
            productNames[i] = entry.getName();
            i++;
        }
        return productNames;
    }

    /**
     * Inserts a new product into the database with the specified name, price, and stock.
     *
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the stock quantity of the product
     */
    public static void insertProduct(String name, int price, int stock){
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        int lastId = 0;
        if (!products.isEmpty()) {
            Product product = products.get(products.size() - 1);
            lastId = product.getId();
        }
        int id =lastId+1;
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        productDAO.insert(product);
    }
    /**
     * Updates the stock of a product after an order is placed.
     *
     * @param id    the ID of the product
     * @param value the value by which to decrease the stock
     */
    public static void updateStockAfterOrder(int id , int value){
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findBy("id",id);
        if(!products.isEmpty()){
            Product product = products.get(0);
            int newStock = product.getStock()-value;
            productDAO.update("stock",newStock,product.getStock());
        }
    }

}
