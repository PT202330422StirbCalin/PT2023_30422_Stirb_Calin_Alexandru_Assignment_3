package org.example.Presentation;

import org.example.DataAccess.OrderDAO;
import org.example.DataAccess.ProductDAO;
import org.example.Logic.OrderLogic;
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
 * The OrderGui class represents the graphical user interface for managing orders.
 * It allows users to add products to an order, clear the order, place the order, and view a list of all orders.
 */
public class OrderGui implements ActionListener{

    private static Client clientAux;
    private static Order ord;
    private static JLabel services,logged,success;
    private static JButton list, delete, back, order,finish;
    private static JPanel mainPanel;
    private static JComboBox<String> dropDown;
    private static JFrame mainFrame;
    private static JTable table;
    private static JTextField textField;
    StringBuilder sb;

    public OrderGui(Client client){
        ord = new Order();
        ord.setUsername(client.getUsername());
        sb = new StringBuilder();
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
        success.setBounds(10,420,900,25);
        mainPanel.add(success);

        textField = new JTextField("Nr of items added");
        textField.setFont(new Font("Dialog",Font.BOLD,16));
        textField.setBounds(550,80,250,30);
        textField.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(textField);

        logged = new JLabel("Logged in as: "+ client.getUsername());
        logged.setBounds(10,700,300,25);
        logged.setFont(new Font("Dialog",Font.BOLD,16));
        mainPanel.add(logged);

        list = new JButton("List all orders");
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

        finish = new JButton("Place order");
        finish.setFont(new Font("Dialog",Font.BOLD,16));
        finish.setBounds(10,260,250,30);
        finish.addActionListener(this::actionPerformed);
        mainPanel.add(finish);

        delete = new JButton("Clear order");
        delete.setBounds(10,200,250,30);
        delete.setFont(new Font("Dialog",Font.BOLD,16));
        delete.addActionListener(this::actionPerformed);
        mainPanel.add(delete);

        order = new JButton("Add to order");
        order.setFont(new Font("Dialog",Font.BOLD,16));
        order.setBounds(10,80,250,30);
        order.addActionListener(this::actionPerformed);
        mainPanel.add(order);

        OrderGui.back = new JButton("Back");
        OrderGui.back.setBounds(10,650,80,25);
        OrderGui.back.addActionListener(this::actionPerformed);
        mainPanel.add(OrderGui.back);

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
        if(e.getSource() == delete){
            ord = new Order();
            ord.setUsername(clientAux.getUsername());
            sb.delete(0,sb.length());
        }
        if(e.getSource() == order){
            ProductDAO productDAO = new ProductDAO();
            String productName = String.valueOf(dropDown.getSelectedItem());
            int nrOf = Integer.parseInt(textField.getText());
            OrderLogic.UpdateDescription(ord,productName,nrOf);
            if(ord.getDescription().contains("Insufficient")){
               success.setText(ord.getDescription());
            }
            else {
                sb.append(ord.getDescription() + " ");
                ord.setDescription(sb.toString());
                success.setText(ord.getDescription());
            }
        }
        if(e.getSource()== finish){
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.insert(ord);
            success.setText("Order successfully placed");
        }
    }
    private void createTable(){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> products = orderDAO.findAll();
        String[] columnNames = {"id","usernname","price","description"};
        Object[][] data = new Object[products.size()][columnNames.length];
        for(int i =0;i<products.size();i++){
            Order product = products.get(i);
            data[i][0] = product.getId();
            data[i][1] = product.getUsername();
            data[i][2] = product.getPrice();
            data[i][3] = product.getDescription();
        }
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        JTable table = new JTable(model);
        JFrame frame = new JFrame("Product Table");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }

}

