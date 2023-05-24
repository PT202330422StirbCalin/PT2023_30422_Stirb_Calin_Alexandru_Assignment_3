package org.example.Presentation;

import org.example.DataAccess.ClientDAO;
import org.example.Model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The ClientGui class represents the graphical user interface for managing client information.
 * It provides various services and functionality such as listing all clients, updating username and password,
 * deleting a client, and displaying a table of client information.
 */
public class ClientGui implements ActionListener{
    private static Client clientAux;
    private static JLabel services,logged,success;
    private static JButton list, delete, user,pass, back;
    private static JPanel mainPanel;
    private static JTextField textField;
    private static JFrame mainFrame;
    private static JTable table;

    public ClientGui(Client client){
        clientAux = client;
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        mainFrame.setSize(1000, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainPanel.setLayout(null);
        mainFrame.setTitle("Store");
        ImageIcon bg = new ImageIcon("C:\\Users\\stirb\\Downloads\\Facultate\\mvc\\background.jpg");
        Image img = bg.getImage();
        Image temp = img.getScaledInstance(1000,800,Image.SCALE_SMOOTH);
        bg = new ImageIcon(temp);
        JLabel back = new JLabel(bg);
        back.setLayout(null);
        back.setBounds(0,0,1000,800);

        services = new JLabel("Services Available:");
        services.setFont(new Font("Dialog",Font.BOLD,16));
        services.setBounds(10,20,200,25);
        mainPanel.add(services);

        success = new JLabel("");
        success.setFont(new Font("Dialog",Font.BOLD,16));
        success.setBounds(10,350,350,25);
        mainPanel.add(success);

        textField = new JTextField("");
        textField.setFont(new Font("Dialog",Font.BOLD,16));
        textField.setBounds(10,295,250,30);
        textField.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(textField);

        logged = new JLabel("Logged in as: "+ client.getUsername());
        logged.setBounds(10,700,300,25);
        logged.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(logged);

        list = new JButton("List all clients");
        list.setFont(new Font("Dialog",Font.BOLD,16));
        list.setBounds(10,80,250,30);
        list.addActionListener(this::actionPerformed);
        mainPanel.add(list);


        delete = new JButton("Delete client");
        delete.setBounds(10,200,250,30);
        delete.setFont(new Font("Dialog",Font.BOLD,16));
        delete.addActionListener(this::actionPerformed);
        mainPanel.add(delete);

        user = new JButton("Update username");
        user.setFont(new Font("Dialog",Font.BOLD,16));
        user.setBounds(10,140,250,30);
        user.addActionListener(this::actionPerformed);
        mainPanel.add(user);

        pass = new JButton("Update password");
        pass.setFont(new Font("Dialog",Font.BOLD,16));
        pass.setBounds(10,260,250,30);
        pass.addActionListener(this::actionPerformed);
        mainPanel.add(pass);

        ClientGui.back = new JButton("Back");
        ClientGui.back.setBounds(10,650,80,25);
        ClientGui.back.addActionListener(this::actionPerformed);
        mainPanel.add(ClientGui.back);

        mainPanel.add(back);
        mainFrame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == list) {
            createTable();
        }
        if (e.getSource() == back) {
            MainMenu mainMenu = new MainMenu(clientAux);
            mainFrame.setVisible(false);
        }
        if (e.getSource() == user) {
            ClientDAO clientDAO = new ClientDAO();
            String username = textField.getText();
            String originalUser = clientAux.getUsername();
            clientDAO.update("username", username,originalUser);
            clientAux.setUsername(username);
            logged.setText("Logged in as: " + clientAux.getUsername());
            success.setText("Username successfully changed");
        }
        if(e.getSource() == delete){
            ClientDAO clientDAO = new ClientDAO();
            int id = clientAux.getId();
            clientDAO.delete("id",id);
            LoginGui loginGui = new LoginGui();
            mainFrame.setVisible(false);
        }
        if (e.getSource() == pass) {
            ClientDAO clientDAO = new ClientDAO();
            String password = textField.getText();
            String originalPass = clientAux.getPassword();
            clientDAO.update("password", password,originalPass);
            clientAux.setPassword(password);
            success.setText("Password successfully changed");
        }
    }
    /**
     * Creates a table to display client information.
     * The table includes columns for id, username, and password.
     */
    private void createTable(){
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        String[] columnNames = {"id","username","password"};
        Object[][] data = new Object[clients.size()][columnNames.length];
        for(int i =0;i<clients.size();i++){
            Client client1 = clients.get(i);
            data[i][0] = client1.getId();
            data[i][1] = client1.getUsername();
            data[i][2] = client1.getPassword();
        }
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        JTable table = new JTable(model);
        JFrame frame = new JFrame("Client Table");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}

