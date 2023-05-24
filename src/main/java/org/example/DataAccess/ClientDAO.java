package org.example.DataAccess;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO extends AbstractDAO<Client>{

    public ClientDAO(){
    super();
    }


}
