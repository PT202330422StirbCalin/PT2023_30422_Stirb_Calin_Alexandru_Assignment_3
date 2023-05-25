package org.example.Logic;

import org.example.DataAccess.OrderDAO;
import org.example.DataAccess.ProductDAO;
import org.example.Model.Order;
import org.example.Model.Product;

import java.util.List;
/**
 * The OrderLogic class represents the logic for order-related operations in the application.
 * It provides methods for updating order description and related functionality.
 */
public class OrderLogic {
    /**
     * Updates the description of an order based on the given product name and quantity.
     *
     * @param order       the order to update
     * @param productName the name of the product
     * @param nrOf        the quantity of the product
     * @return the updated order with the new description
     */
    public static Order UpdateDescription(Order order, String productName, int nrOf) {
        OrderDAO orderDAO = new OrderDAO();
        String update = new String();
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findBy("name", productName);
        Product product = products.get(0);
        String name = product.getName();
        int lastId = 0;
        List<Order> orders = orderDAO.findAll();
        if (!orders.isEmpty()) {
            Order order1 = orders.get(orders.size() - 1);
            lastId = order1.getId();
        }
        int id = lastId + 1;
        if (product.getStock() - nrOf >= 0) {
            int price = product.getPrice();
            update = name + " x" + nrOf;
            order.setId(id);
            order.setDescription(update);
            order.setPrice(order.getPrice() + price);
            product.setStock(product.getStock() - nrOf);
            ProductLogic.updateStockAfterOrder(product.getId(), nrOf);
        } else {
            order.setDescription("Insufficient stock: " + product.getStock());
        }
        return order;
    }
}


