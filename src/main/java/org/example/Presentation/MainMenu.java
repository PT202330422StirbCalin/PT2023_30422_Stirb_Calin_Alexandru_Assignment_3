package org.example.Presentation;

import org.example.Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The MainMenu class represents the graphical user interface for the main menu of the application.
 * It provides options for accessing different services such as products, orders, and clients.
 */
public class MainMenu {
    private Client client;
    private static JLabel services,logged;
    private static JButton product, order, clients,logout;
    private static JPanel mainPanel;
    private static JFrame mainFrame;

    public MainMenu(Client client){
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        mainFrame.setSize(800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainPanel.setLayout(null);
        mainFrame.setTitle("Store");
        ImageIcon bg = new ImageIcon("C:\\Users\\stirb\\Downloads\\Facultate\\mvc\\background.jpg");
        Image img = bg.getImage();
        Image temp = img.getScaledInstance(800,500,Image.SCALE_SMOOTH);
        bg = new ImageIcon(temp);
        JLabel back = new JLabel(bg);
        back.setLayout(null);
        back.setBounds(0,0,800,500);

        services = new JLabel("Services Available:");
        services.setFont(new Font("Dialog",Font.BOLD,16));
        services.setBounds(10,20,200,25);
        mainPanel.add(services);

        logged = new JLabel("Logged in as: "+ client.getUsername());
        logged.setBounds(650,10,150,25);
        mainPanel.add(logged);

        product = new JButton("Products");
        product.setFont(new Font("Dialog",Font.BOLD,16));
        product.setBounds(10,80,250,30);
        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ProductGui productGui = new ProductGui(client);
                mainFrame.setVisible(false);
            }
        });
        mainPanel.add(product);


        order = new JButton("Order");
        order.setBounds(10,200,250,30);
        order.setFont(new Font("Dialog",Font.BOLD,16));
        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderGui orderGui = new OrderGui(client);
                mainFrame.setVisible(false);
            }
        });
        mainPanel.add(order);

        clients = new JButton("Clients");
        clients.setFont(new Font("Dialog",Font.BOLD,16));
        clients.setBounds(10,140,250,30);
        clients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ClientGui clientGui = new ClientGui(client);
                    mainFrame.setVisible(false);
            }
        });
        mainPanel.add(clients);

        logout = new JButton("Logout");
        logout.setBounds(700,425,80,25);
        mainPanel.add(logout);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    LoginGui loginMenu = new LoginGui();
                    mainFrame.setVisible(false);
            }
        });

        mainPanel.add(back);
        mainFrame.setVisible(true);
    }
}
