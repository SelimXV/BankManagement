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

    private static final String ADMIN_CARD = "192837465";
    private static final String ADMIN_PIN = "3482";
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private int loginAttempts = 0;

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
        signupButton.setBackground(new Color(204, 0, 0));  // Rouge foncé
        signupButton.setForeground(Color.WHITE);
        signupButton.setOpaque(true);
        signupButton.setBorderPainted(false);
        signupButton.setBounds(340, 300, 120, 30);
        signupButton.addActionListener(this);
        add(signupButton);

        clearButton = new JButton("EFFACER");
        clearButton.setBackground(new Color(51, 51, 51));  // Gris foncé
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        clearButton.setBounds(460, 300, 100, 30);
        clearButton.addActionListener(this);
        add(clearButton);

        loginButton = new JButton("SE CONNECTER");
        loginButton.setBackground(new Color(0, 102, 204));  // Bleu
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
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
            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                JOptionPane.showMessageDialog(null, "Compte temporairement bloqué. Veuillez réessayer plus tard.");
                return;
            }

            String cardNumber = cardNumberField.getText();
            String pin = new String(pinField.getPassword());

            // Vérifier si c'est une connexion admin
            if (cardNumber.equals(ADMIN_CARD) && pin.equals(ADMIN_PIN)) {
                loginAttempts = 0;
                new AdminInterface();
                setVisible(false);
                return;
            }

            try {
                sqlcon c = new sqlcon();
                String query = "SELECT * FROM account WHERE card_number = '" + cardNumber + "' AND pin = '" + pin + "' AND is_closed = FALSE";
                ResultSet rs = c.statement.executeQuery(query);
                
                if (rs.next()) {
                    loginAttempts = 0;
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");
                    new Accueil(cardNumber);
                    setVisible(false);
                } else {
                    // Vérifier si le compte existe mais est clôturé
                    query = "SELECT is_closed FROM account WHERE card_number = '" + cardNumber + "'";
                    rs = c.statement.executeQuery(query);
                    if (rs.next() && rs.getBoolean("is_closed")) {
                        JOptionPane.showMessageDialog(null, "Ce compte a été clôturé. Veuillez contacter votre agence pour plus d'informations.");
                        return;
                    }
                    
                    loginAttempts++;
                    int remainingAttempts = MAX_LOGIN_ATTEMPTS - loginAttempts;
                    JOptionPane.showMessageDialog(null, 
                        "Numéro de carte ou PIN incorrect !\nTentatives restantes : " + remainingAttempts);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données");
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
