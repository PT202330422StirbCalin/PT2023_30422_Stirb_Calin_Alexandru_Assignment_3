package org.example;

import org.example.DataAccess.ClientDAO;
import org.example.Model.Client;
import org.example.Presentation.LoginGui;

import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        LoginGui loginGui = new LoginGui();
        /*
        ClientDAO dao = new ClientDAO();
        List<Client> clients = dao.findAll();
        for(Client ent: clients){
            System.out.println(ent.getId()+" "+ent.getUserName());

        }
         */
    }
}
