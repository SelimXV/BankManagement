package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField cardNumberField;
    JPasswordField pinField;
    JButton signupButton, clearButton, loginButton;

    public Login() {
        setTitle("Bank Management System - Login");
        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        l1 = new JLabel("Bienvenue sur l'ATM");
        l1.setFont(new Font("Osward", Font.BOLD, 30));
        l1.setForeground(Color.CYAN);
        l1.setBounds(270, 125, 450, 40);
        add(l1);

        l2 = new JLabel("Numéro de carte :");
        l2.setFont(new Font("Raleway", Font.BOLD, 24));
        l2.setForeground(Color.WHITE);
        l2.setBounds(125, 200, 375, 30);
        add(l2);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(350, 200, 200, 30);
        add(cardNumberField);

        l3 = new JLabel("Code PIN :");
        l3.setFont(new Font("Raleway", Font.BOLD, 24));
        l3.setForeground(Color.WHITE);
        l3.setBounds(125, 260, 375, 30);
        add(l3);

        pinField = new JPasswordField();
        pinField.setBounds(350, 260, 200, 30);
        add(pinField);

        signupButton = new JButton("S'INSCRIRE");
        signupButton.setBackground(Color.RED);
        signupButton.setForeground(Color.WHITE);
        signupButton.setBounds(340, 300, 120, 30);
        signupButton.addActionListener(this);
        add(signupButton);

        clearButton = new JButton("EFFACER");
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        clearButton.setBounds(460, 300, 100, 30);
        clearButton.addActionListener(this);
        add(clearButton);

        loginButton = new JButton("SE CONNECTER");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(340, 350, 220, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        // Exemple d'ajout d'un arrière-plan (optionnel)
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icon/arriereplan.jpg"));
        Image img = icon.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 850, 480);
        add(background);

        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            new Signup();
            setVisible(false);
        } else if (e.getSource() == clearButton) {
            cardNumberField.setText("");
            pinField.setText("");
        } else if (e.getSource() == loginButton) {
            String cardNumber = cardNumberField.getText();
            String pin = new String(pinField.getPassword());
            try {
                sqlcon c = new sqlcon();
                String query = "SELECT * FROM account WHERE card_number = '" + cardNumber + "' AND pin = '" + pin + "'";
                ResultSet rs = c.statement.executeQuery(query);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");
                    // Par exemple, ouvrir la fenêtre Transactions (à adapter selon ton code)
                    new Accueil(cardNumber);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Numéro de carte ou PIN incorrect !");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
