package org.example.Presentation;

import org.example.DataAccess.ClientDAO;
import org.example.Logic.ClientLogic;
import org.example.Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The LoginGui class represents the graphical user interface for the login functionality.
 * It allows users to log in or sign up by entering their username and password.
 */
public class LoginGui implements ActionListener{
        public boolean logged = false;
        public static JLabel userLabel, success, passLabel;
        private static JTextField userText;
        private static JPasswordField pasText;
        private static JButton login, signUp;
        private static JPanel loginPanel;
        private static JFrame loginFrame;

        public boolean isLogged() {
            return logged;
        }

        public void setLogged(boolean logged) {
            this.logged = logged;
        }

        public LoginGui() {
            loginPanel = new JPanel();
            loginFrame = new JFrame();
            loginFrame.setSize(400, 200);
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.add(loginPanel);
            loginPanel.setLayout(null);
            loginFrame.setTitle("Store");
            ImageIcon bg = new ImageIcon("C:\\Users\\stirb\\Downloads\\Facultate\\mvc\\background.jpg");
            Image img = bg.getImage();
            Image temp = img.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            bg = new ImageIcon(temp);
            JLabel back = new JLabel(bg);
            back.setLayout(null);
            back.setBounds(0, 0, 400, 200);

            userLabel = new JLabel("UserName:");
            userLabel.setBounds(10, 20, 80, 25);
            loginPanel.add(userLabel);
            userText = new JTextField();
            userText.setBounds(100, 20, 165, 25);
            loginPanel.add(userText);

            passLabel = new JLabel("Password");
            passLabel.setBounds(10, 50, 80, 25);
            loginPanel.add(passLabel);
            pasText = new JPasswordField();
            pasText.setBounds(100, 50, 165, 25);
            loginPanel.add(pasText);

            login = new JButton("Login");
            login.setBounds(10, 80, 80, 25);
            loginPanel.add(login);
            login.addActionListener(this::actionPerformed);
            signUp = new JButton("Sign Up");
            signUp.setBounds(100, 80, 80, 25);
            loginPanel.add(signUp);
            signUp.addActionListener(this::actionPerformed);

            success = new JLabel("");
            success.setBounds(10, 110, 300, 25);
            loginPanel.add(success);

            loginPanel.add(back);
            loginFrame.setVisible(true);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == signUp){
                String userName = userText.getText();
                String password = pasText.getText();
                ClientLogic logic = new ClientLogic(this);
                logic.createAccount(userName,password);
            }
            if(e.getSource() == login){
                String userName = userText.getText();
                String password = pasText.getText();
                ClientLogic logic = new ClientLogic(this);
                boolean correctUser = logic.loginCheck("username",userName);
                boolean correctPass = logic.loginCheck("password",password);
                if(correctPass && correctUser) {
                    ClientDAO clientDAO = new ClientDAO();
                    Client client = clientDAO.findBy("username",userName).get(0);
                    MainMenu mainMenu = new MainMenu(client);
                    loginFrame.setVisible(false);
                }
                else{
                    success.setText("Username or password is incorrect");
                }
            }
        }
    }

