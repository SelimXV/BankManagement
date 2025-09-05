package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    
    JTextField cardNumberField;
    JPasswordField pinField;
    JButton signupButton, clearButton, loginButton, adminButton;
    JLabel welcomeLabel;

    private static final String ADMIN_CARD = "192837465";
    private static final String ADMIN_PIN = "3482";
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private int loginAttempts = 0;

    public Login() {
        setTitle("Bank Management System - Login");
        setSize(850, 480);
        setLocation(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Configuration du fond avec transparence pour une superposition avec l'image
        setLayout(null);
        
        // Panneau principal avec fond semi-transparent
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 850, 480);
        mainPanel.setOpaque(false);
        add(mainPanel);
        
        // Panneau pour le formulaire de connexion
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(450, 80, 350, 320);
        loginPanel.setBackground(new Color(0, 0, 0, 180));  // Noir avec transparence
        mainPanel.add(loginPanel);
        
        // Titre de bienvenue
        welcomeLabel = new JLabel("Bienvenue sur l'ATM");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 30, 300, 40);
        loginPanel.add(welcomeLabel);
        
        // Icône de carte bancaire
        ImageIcon cardIcon = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        if (cardIcon.getIconWidth() > 0) {  // Vérifier que l'image existe
            Image scaledCardImage = cardIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            ImageIcon scaledCardIcon = new ImageIcon(scaledCardImage);
            JLabel cardIconLabel = new JLabel(scaledCardIcon);
            cardIconLabel.setBounds(50, 80, 60, 60);
            loginPanel.add(cardIconLabel);
        }
        
        // Ligne de séparation
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 80, 250, 1);
        separator.setForeground(new Color(200, 200, 200));
        loginPanel.add(separator);
        
        // Labels et champs de texte
        JLabel cardLabel = new JLabel("Numéro de carte");
        cardLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBounds(125, 85, 200, 30);
        loginPanel.add(cardLabel);
        
        cardNumberField = new JTextField();
        cardNumberField.setBounds(125, 115, 175, 30);
        cardNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        cardNumberField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(cardNumberField);
        
        JLabel pinLabel = new JLabel("Code PIN");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pinLabel.setForeground(Color.WHITE);
        pinLabel.setBounds(125, 155, 200, 30);
        loginPanel.add(pinLabel);
        
        pinField = new JPasswordField();
        pinField.setBounds(125, 185, 175, 30);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(pinField);
        
        // Boutons avec style moderne
        loginButton = createStyledButton("SE CONNECTER", new Color(0, 102, 204), 125, 230, 175, 35);
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);
        
        signupButton = createStyledButton("S'INSCRIRE", new Color(204, 0, 0), 125, 270, 82, 35);
        signupButton.addActionListener(this);
        loginPanel.add(signupButton);
        
        clearButton = createStyledButton("EFFACER", new Color(51, 51, 51), 218, 270, 82, 35);
        clearButton.addActionListener(this);
        loginPanel.add(clearButton);
        
        // Panneau pour le logo et informations bancaires sur la gauche
        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(null);
        brandPanel.setBounds(50, 80, 350, 320);
        brandPanel.setOpaque(false);
        mainPanel.add(brandPanel);
        
        // Logo de banque plus grand
        ImageIcon bankIcon = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        if (bankIcon.getIconWidth() > 0) {
            Image scaledBankImage = bankIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon scaledBankIcon = new ImageIcon(scaledBankImage);
            JLabel bankIconLabel = new JLabel(scaledBankIcon);
            bankIconLabel.setBounds(115, 20, 120, 120);
            brandPanel.add(bankIconLabel);
        }
        
        // Nom de la banque
        JLabel bankNameLabel = new JLabel("BANK MANAGEMENT SYSTEM");
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bankNameLabel.setForeground(Color.WHITE);
        bankNameLabel.setBounds(35, 160, 300, 30);
        brandPanel.add(bankNameLabel);
        
        // Slogan
        JLabel sloganLabel = new JLabel("Votre sécurité est notre priorité");
        sloganLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        sloganLabel.setForeground(Color.WHITE);
        sloganLabel.setBounds(65, 200, 300, 30);
        brandPanel.add(sloganLabel);
        
        // Bouton discret pour l'accès admin en bas à droite de la fenêtre
        adminButton = new JButton("Espace Admin");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 12));
        adminButton.setBackground(new Color(50, 50, 50));
        adminButton.setForeground(Color.LIGHT_GRAY);
        adminButton.setBorderPainted(false);
        adminButton.setFocusPainted(false);
        adminButton.setOpaque(true);
        adminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminButton.setBounds(680, 415, 120, 25);
        adminButton.addActionListener(this);
        mainPanel.add(adminButton);
        
        // Image d'arrière-plan
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icon/arriereplan.jpg"));
        if (backgroundIcon.getIconWidth() > 0) {
            Image backgroundImg = backgroundIcon.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
            ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImg);
            JLabel background = new JLabel(scaledBackgroundIcon);
            background.setBounds(0, 0, 850, 480);
            add(background);
        }
        
        setVisible(true);
    }
    
    // Méthode utilitaire pour créer des boutons stylisés
    private JButton createStyledButton(String text, Color bgColor, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBounds(x, y, width, height);
        return button;
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
        } else if (e.getSource() == adminButton) {
            // Création d'une boîte de dialogue de connexion admin
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                "Mot de passe administrateur:", passwordField
            };
            
            int option = JOptionPane.showConfirmDialog(null, message, "Connexion administrateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                String password = new String(passwordField.getPassword());
                
                if (bank.management.system.PasswordUtil.verifyAdminPassword(password)) {
                    new AdminInterface();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Mot de passe incorrect", "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
