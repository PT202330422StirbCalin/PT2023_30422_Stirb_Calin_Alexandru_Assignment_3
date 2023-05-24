package org.example.DataAccess;

import org.example.Model.Order;
/**
 * The OrderDAO class provides data access operations for the Order entity.
 * It allows performing CRUD (Create, Read, Update, Delete) operations on the database table for orders.
 */
public class OrderDAO extends AbstractDAO<Order>{
    public OrderDAO() {
        super();
    }
}
