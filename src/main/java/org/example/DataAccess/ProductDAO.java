package org.example.DataAccess;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Product;

public class ProductDAO extends AbstractDAO<Product>{

    public ProductDAO() {
        super();
    }

}
