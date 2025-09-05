package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminInterface extends JFrame {
    
    private JLabel totalClientsLabel;
    private JLabel totalBalanceLabel;
    private JLabel totalBeforeInterestLabel;
    private JLabel totalAfterInterestLabel;

    public AdminInterface() {
        setTitle("Interface Administrateur");
        setSize(600, 500); // Augmenté la hauteur pour les nouveaux composants
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        // Titre
        JLabel titleLabel = new JLabel("Panel Administrateur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 30, 300, 40);
        add(titleLabel);

        // Label pour le nombre total de clients
        totalClientsLabel = new JLabel("Nombre total de clients : Chargement...");
        totalClientsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        totalClientsLabel.setForeground(Color.WHITE);
        totalClientsLabel.setBounds(100, 100, 400, 30);
        add(totalClientsLabel);

        // Label pour le solde total
        totalBalanceLabel = new JLabel("Solde total des comptes : Chargement...");
        totalBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        totalBalanceLabel.setForeground(Color.WHITE);
        totalBalanceLabel.setBounds(100, 150, 400, 30);
        add(totalBalanceLabel);
        
        // Labels pour afficher les montants avant et après intérêts
        totalBeforeInterestLabel = new JLabel("Montant total avant intérêts : 0.00 €");
        totalBeforeInterestLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalBeforeInterestLabel.setForeground(Color.WHITE);
        totalBeforeInterestLabel.setBounds(100, 200, 400, 30);
        add(totalBeforeInterestLabel);
        
        totalAfterInterestLabel = new JLabel("Montant total après intérêts : 0.00 €");
        totalAfterInterestLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalAfterInterestLabel.setForeground(Color.WHITE);
        totalAfterInterestLabel.setBounds(100, 240, 400, 30);
        add(totalAfterInterestLabel);
        
        // Bouton pour appliquer les intérêts
        JButton applyInterestButton = new JButton("Ajouter les intérêts annuels");
        applyInterestButton.setBackground(new Color(0, 102, 204)); // Bleu
        applyInterestButton.setForeground(Color.WHITE);
        applyInterestButton.setOpaque(true);
        applyInterestButton.setBorderPainted(false);
        applyInterestButton.setBounds(150, 290, 300, 40);
        applyInterestButton.addActionListener(e -> applyInterest());
        add(applyInterestButton);
        
        // Bouton pour générer le hash du mot de passe admin
        JButton generateHashButton = new JButton("Générer Hash MDP");
        generateHashButton.setBackground(new Color(0, 153, 51)); // Vert
        generateHashButton.setForeground(Color.WHITE);
        generateHashButton.setOpaque(true);
        generateHashButton.setBorderPainted(false);
        generateHashButton.setBounds(125, 350, 160, 30);
        generateHashButton.addActionListener(e -> {
            PasswordUtil.printAdminPasswordHash();
            JOptionPane.showMessageDialog(null, 
                "Le hash BCrypt du mot de passe admin a été généré et affiché dans la console.",
                "Hash généré", JOptionPane.INFORMATION_MESSAGE);
        });
        add(generateHashButton);

        // Bouton de déconnexion avec nouveau style
        JButton logoutButton = new JButton("Déconnexion");
        logoutButton.setBackground(new Color(204, 0, 0));  // Rouge foncé
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setBounds(325, 350, 160, 30);
        logoutButton.addActionListener(e -> {
            new Login();
            dispose();
        });
        add(logoutButton);

        // Ajouter un fond noir avec une bordure
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icon/backbg.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(600, 500, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 600, 500);
        add(backgroundLabel);

        loadAdminData();
        updateTotalBeforeInterestLabel(); // Afficher le montant total avant intérêts au démarrage
        setVisible(true);
    }

    private void loadAdminData() {
        try {
            sqlcon c = new sqlcon();

            // Requête pour compter le nombre total de clients
            String clientQuery = "SELECT COUNT(*) as total_clients FROM signup";
            ResultSet rsClients = c.statement.executeQuery(clientQuery);

            if (rsClients.next()) {
                int totalClients = rsClients.getInt("total_clients");
                totalClientsLabel.setText("Nombre total de clients : " + totalClients);
            }

            // Requête pour calculer la somme totale des soldes
            String balanceQuery = "SELECT SUM(balance) as total_balance FROM account";
            ResultSet rsBalance = c.statement.executeQuery(balanceQuery);

            if (rsBalance.next()) {
                double totalBalance = rsBalance.getDouble("total_balance");
                totalBalanceLabel.setText("Solde total des comptes : " + String.format("%.2f €", totalBalance));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des données administrateur");
            e.printStackTrace();
        }
    }
    
    // Méthode pour afficher le montant total avant intérêts
    private void updateTotalBeforeInterestLabel() {
        try {
            double totalBefore = calculateTotalAmount();
            totalBeforeInterestLabel.setText("Montant total avant intérêts : " + String.format("%.2f €", totalBefore));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du calcul du montant total");
        }
    }
    
    // Méthode pour calculer le montant total de tous les comptes
    private double calculateTotalAmount() {
        double total = 0;
        try {
            sqlcon c = new sqlcon();
            ResultSet rs = c.statement.executeQuery("SELECT SUM(balance) AS total FROM account WHERE is_closed = FALSE");
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    

    private void applyInterest() {
        try {

            double beforeAmount = calculateTotalAmount();
            
            sqlcon c = new sqlcon();
            c.statement.executeUpdate("UPDATE account SET balance = balance + (balance * interest_rate / 100)");
            
            double afterAmount = calculateTotalAmount();
            

            loadAdminData();
            

            String message = "Montant total avant intérêts : " + String.format("%.2f €", beforeAmount) + 
                             "\nMontant total après intérêts : " + String.format("%.2f €", afterAmount);
            
            JOptionPane.showMessageDialog(this, message, "Résultats de l'application des intérêts", JOptionPane.INFORMATION_MESSAGE);
            

            totalBeforeInterestLabel.setText("Montant total avant intérêts : " + String.format("%.2f €", beforeAmount));
            totalAfterInterestLabel.setText("Montant total après intérêts : " + String.format("%.2f €", afterAmount));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'application des intérêts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminInterface();
    }
}