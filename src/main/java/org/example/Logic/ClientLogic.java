package org.example.Logic;

import org.example.DataAccess.ClientDAO;
import org.example.Model.Client;
import org.example.Presentation.AbstractTable;
import org.example.Presentation.LoginGui;

import java.util.List;
import java.util.Objects;

/**
 * The ClientLogic class represents the logic for client-related operations in the application.
 * It provides methods for login check, account creation, and related functionality.
 */
public class ClientLogic {
    private LoginGui gui;
    private final ClientDAO clientDAO;
    /**
     * Constructs a ClientLogic object with the specified LoginGui.
     *
     * @param gui the LoginGui object associated with the logic
     */
    public ClientLogic(LoginGui gui) {
        this.gui = gui;
        this.clientDAO = new ClientDAO();
    }

    /**
     * Checks if a client with the given column and data exists in the database.
     *
     * @param column the column to search for
     * @param data   the data to match
     * @return true if a client with the specified column and data exists, false otherwise
     */
    public boolean loginCheck(String column, String data){
        boolean exists = false;
        List<Client> clients = clientDAO.findBy(column,data);
        if(!clients.isEmpty()){
            exists =true;
        }
        return exists;
    }
    /**
     * Checks if a client with the given username already exists in the database.
     *
     * @param userName the username to check
     * @param pass     the password associated with the username
     * @return true if a client with the specified username exists, false otherwise
     */
    public boolean createCheck(String userName, String pass) {
        boolean exists = false;
        String columnName = "username";
            List<Client> clients = clientDAO.findBy(columnName,userName);
            if(!clients.isEmpty()){
                exists = true;
            }
        return exists;
    }
    /**
     * Creates a new client account with the given username and password.
     *
     * @param userName the username for the new account
     * @param password the password for the new account
     */
    public void createAccount(String userName, String password){
        boolean exists = createCheck(userName, password);
        if((!Objects.equals(userName, "") && !Objects.equals(password, ""))|| !Objects.equals(userName, "") || !Objects.equals(password, "")) {
            if (!exists) {
                List<Client> clients = clientDAO.findAll();
                int lastId = 0;
                if (!clients.isEmpty()) {
                    Client client = clients.get(clients.size() - 1);
                    lastId = client.getId();
                }
                int newId = lastId + 1;
                Client client = new Client();
                client.setId(newId);
                client.setUsername(userName);
                client.setPassword(password);
                clientDAO.insert(client);
                gui.success.setText("Account created successfully");
            } else {
                gui.success.setText("Account already exists");
            }
        }
        else{
            gui.success.setText("Enter the necessary information");
        }
}
    public static void buildClientTable(){
        ClientDAO clientDAO = new ClientDAO();
        AbstractTable table = new AbstractTable();
        List<Client> clients = clientDAO.findAll();
        table.createTable(clients);
    }
}

