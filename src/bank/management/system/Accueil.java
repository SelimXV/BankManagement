package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Accueil extends JFrame {

    String cardNumber;
    JLabel balanceLabel;
    JButton depositButton, withdrawButton, accountInfoButton, historyButton, logoutButton, adminButton;

    public Accueil(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("Transactions - Espace Bancaire");
        setSize(850, 630);
        setLocation(350, 40);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Fond avec image de banque
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icon/backbg.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(850, 630, Image.SCALE_DEFAULT);
        ImageIcon scaledBackground = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackground);
        backgroundLabel.setBounds(0, 0, 850, 630);
        
        // Ajout de l'icône de banque
        ImageIcon bankIcon = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image scaledBankImage = bankIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
        ImageIcon scaledBankIcon = new ImageIcon(scaledBankImage);
        JLabel bankIconLabel = new JLabel(scaledBankIcon);
        bankIconLabel.setBounds(50, 30, 80, 80);
        add(bankIconLabel);

        // Panneau principal semi-transparent
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(180, 120, 500, 430);
        mainPanel.setBackground(new Color(0, 0, 0, 180));
        add(mainPanel);

        // Message de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans votre espace bancaire");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(40, 20, 450, 40);
        mainPanel.add(welcomeLabel);

        // Affichage du numéro de carte
        JLabel cardLabel = new JLabel("Numéro de carte : " + cardNumber);
        cardLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBounds(40, 70, 400, 30);
        mainPanel.add(cardLabel);

        // Récupérer et afficher le solde
        balanceLabel = new JLabel("Solde : Chargement...");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(Color.CYAN);
        balanceLabel.setBounds(40, 110, 300, 30);
        mainPanel.add(balanceLabel);
        updateBalance();

        // Ligne de séparation
        JSeparator separator = new JSeparator();
        separator.setBounds(40, 150, 420, 2);
        separator.setForeground(Color.GRAY);
        mainPanel.add(separator);

        // Boutons avec design harmonisé
        // Bouton DÉPÔT
        depositButton = createStyledButton("DÉPÔT", new Color(0, 102, 204), 40, 180, 420, 40);
        depositButton.addActionListener(e -> {
            new Deposit(cardNumber).setVisible(true);
            setVisible(false);
        });
        mainPanel.add(depositButton);

        // Bouton RETRAIT
        withdrawButton = createStyledButton("RETRAIT", new Color(0, 102, 204), 40, 230, 420, 40);
        withdrawButton.addActionListener(e -> {
            new Withdraw(cardNumber).setVisible(true);
            setVisible(false);
        });
        mainPanel.add(withdrawButton);

        // Bouton INFOS DU COMPTE
        accountInfoButton = createStyledButton("INFOS DU COMPTE", new Color(51, 51, 51), 40, 280, 420, 40);
        accountInfoButton.addActionListener(e -> {
            new AccountInfo(cardNumber).setVisible(true);
            setVisible(false);
        });
        mainPanel.add(accountInfoButton);

        // Bouton HISTORIQUE
        historyButton = createStyledButton("HISTORIQUE", new Color(51, 51, 51), 40, 330, 420, 40);
        historyButton.addActionListener(e -> {
            new TransactionHistory(cardNumber).setVisible(true);
        });
        mainPanel.add(historyButton);

        // Bouton DÉCONNEXION
        logoutButton = createStyledButton("DÉCONNEXION", new Color(204, 0, 0), 40, 380, 420, 40);
        logoutButton.addActionListener(e -> {
            new Login();
            setVisible(false);
        });
        mainPanel.add(logoutButton);
        
        // Bouton discret pour l'accès admin
        adminButton = new JButton("Espace Admin");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 12));
        adminButton.setBackground(new Color(50, 50, 50));
        adminButton.setForeground(Color.LIGHT_GRAY);
        adminButton.setBorderPainted(false);
        adminButton.setFocusPainted(false);
        adminButton.setOpaque(true);
        adminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminButton.setBounds(680, 560, 120, 25);
        adminButton.addActionListener(e -> {
            // Création d'une boîte de dialogue de connexion admin
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                "Mot de passe administrateur:", passwordField
            };
            
            int option = JOptionPane.showConfirmDialog(null, message, "Connexion administrateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                String password = new String(passwordField.getPassword());
                
                // Pour ce contrôle sur table, on vérifie simplement si le mot de passe est "mdp123"
                // Dans une vraie application, il faudrait vérifier le hash dans la base de données
                if (password.equals("mdp123")) {
                    new AdminInterface();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Mot de passe incorrect", "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(adminButton);

        // Ajout de l'arrière-plan en dernier pour qu'il soit derrière tous les éléments
        add(backgroundLabel);

        setResizable(false);
        setVisible(true);
    }

    // Méthode pour créer des boutons stylisés
    private JButton createStyledButton(String text, Color bgColor, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBounds(x, y, width, height);
        return button;
    }

    private void updateBalance() {
        try {
            sqlcon con = new sqlcon();
            String query = "SELECT balance, currency FROM account WHERE card_number = '" + cardNumber + "'";
            ResultSet rs = con.statement.executeQuery(query);
            if (rs.next()) {
                String currency = rs.getString("currency");
                balanceLabel.setText("Solde : " + rs.getDouble("balance") + " " + currency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Accueil("1234567890123456");
    }
}
