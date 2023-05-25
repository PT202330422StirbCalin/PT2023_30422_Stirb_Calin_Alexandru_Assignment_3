package org.example.Presentation;

import org.example.DataAccess.ClientDAO;
import org.example.DataAccess.OrderDAO;
import org.example.DataAccess.ProductDAO;
import org.example.Logic.ProductLogic;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**

 The ProductGui class represents the graphical user interface for managing products.
 It provides functionality for listing products, updating stock and price, deleting products, and adding new products.
 The GUI is implemented using Swing components.
 */
public class ProductGui implements ActionListener{
    private static Client clientAux;
    private static JLabel services,logged,success;
    private static JButton list, delete, stock, pric, back,insert;
    private static JPanel mainPanel;
    private static JTextField textField;
    private static JComboBox<String> dropDown;
    private static JFrame mainFrame;
    private static JTable table;

    public ProductGui(Client client){
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
        success.setBounds(10,420,350,25);
        mainPanel.add(success);

        textField = new JTextField("");
        textField.setFont(new Font("Dialog",Font.BOLD,16));
        textField.setBounds(10,380,250,30);
        textField.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(textField);

        logged = new JLabel("Logged in as: "+ client.getUsername());
        logged.setBounds(10,700,300,25);
        logged.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(logged);

        list = new JButton("List all products");
        list.setFont(new Font("Dialog",Font.BOLD,16));
        list.setBounds(10,140,250,30);
        list.addActionListener(this::actionPerformed);
        mainPanel.add(list);

        String[] productNames = ProductLogic.dropDownString();
        dropDown = new JComboBox<String>(productNames);
        dropDown.setFont(new Font("Dialog",Font.BOLD,16));
        dropDown.setBounds(300,80,250,30);
        dropDown.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(dropDown);

        delete = new JButton("Delete product");
        delete.setBounds(10,260,250,30);
        delete.setFont(new Font("Dialog",Font.BOLD,16));
        delete.addActionListener(this::actionPerformed);
        mainPanel.add(delete);

        stock = new JButton("Update stock");
        stock.setFont(new Font("Dialog",Font.BOLD,16));
        stock.setBounds(10,200,250,30);
        stock.addActionListener(this::actionPerformed);
        mainPanel.add(stock);

        pric = new JButton("Update price");
        pric.setFont(new Font("Dialog",Font.BOLD,16));
        pric.setBounds(10,320,250,30);
        pric.addActionListener(this::actionPerformed);
        mainPanel.add(pric);

        insert = new JButton("Add new product");
        insert.setFont(new Font("Dialog",Font.BOLD,16));
        insert.setBounds(10,80,250,30);
        insert.addActionListener(this::actionPerformed);
        mainPanel.add(insert);

        ProductGui.back = new JButton("Back");
        ProductGui.back.setBounds(10,650,80,25);
        ProductGui.back.addActionListener(this::actionPerformed);
        mainPanel.add(ProductGui.back);

        mainPanel.add(back);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == list) {
            ProductDAO productDAO = new ProductDAO();
            AbstractTable table = new AbstractTable();
            List<Product> clients = productDAO.findAll();
            table.createTable(clients);
        }
        if (e.getSource() == back) {
            MainMenu mainMenu = new MainMenu(clientAux);
            mainFrame.setVisible(false);
        }
        if (e.getSource() == stock) {
            int stock = Integer.parseInt(textField.getText());
            String productName = String.valueOf(dropDown.getSelectedItem());
            ProductDAO productDAO = new ProductDAO();
            List<Product> products = productDAO.findBy("name",productName);
            int oldStock = products.get(0).getStock();
            productDAO.update("stock", stock,oldStock);
            success.setText("Stock successfully changed");
            String[] productNames = ProductLogic.dropDownString();
            dropDown = new JComboBox<>(productNames);
        }
        if(e.getSource() == delete){
            ProductDAO productDAO = new ProductDAO();
            String productName = String.valueOf(dropDown.getSelectedItem());
            List<Product> products = productDAO.findBy("name",productName);
            int id = products.get(0).getStock();
            productDAO.delete("id",id);
            String[] productNames = ProductLogic.dropDownString();
            dropDown = new JComboBox<>(productNames);
            success.setText("Product successfully deleted");
        }
        if (e.getSource() == pric) {
            int price = Integer.parseInt(textField.getText());
            String productName = String.valueOf(dropDown.getSelectedItem());
            ProductDAO productDAO = new ProductDAO();
            List<Product> products = productDAO.findBy("name",productName);
            int oldPrice = products.get(0).getPrice();
            productDAO.update("price", price,oldPrice);
            success.setText("Price successfully changed");
            String[] productNames = ProductLogic.dropDownString();
            dropDown = new JComboBox<>(productNames);
        }
        if(e.getSource() == insert){
            createInsertFrame();
            String[] productNames = ProductLogic.dropDownString();
            dropDown = new JComboBox<>(productNames);
            success.setText("Product successfully inserted");

        }
    }
    private void createInsertFrame(){
        ProductDAO productDAO = new ProductDAO();
        JFrame frame = new JFrame();
        frame.setTitle("Insert new product");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        JTextField textField1 = new JTextField("Type new name here");
        textField1.setFont(new Font("Dialog",Font.BOLD,16));
        textField1.setBounds(10,20,250,30);
        textField1.setFont(new Font("Dialog",Font.BOLD,16));
        panel.add(textField1);
        JTextField textField2 = new JTextField("Type new price here");
        textField2.setFont(new Font("Dialog",Font.BOLD,16));
        textField2.setBounds(10,80,250,30);
        textField2.setFont(new Font("Dialog",Font.BOLD,16));
        panel.add(textField2);
        JTextField textField3 = new JTextField("Type new stock here");
        textField3.setFont(new Font("Dialog",Font.BOLD,16));
        textField3.setBounds(10,140,250,30);
        textField3.setFont(new Font("Dialog",Font.BOLD,16));
        panel.add(textField3);

        JButton insert = new JButton("Insert");
        insert.setBounds(10,175,80,25);
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = new Product();
                String name = textField1.getText();
                int price= Integer.parseInt(textField2.getText());
                int stock = Integer.parseInt(textField3.getText());
                ProductLogic.insertProduct(name,price,stock);
                frame.setVisible(false);
            }
        });
        panel.add(insert);
        frame.pack();
        frame.setVisible(true);
    }
}

