package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminInterface extends JFrame {
    
    private JLabel totalClientsLabel;
    private JLabel totalBalanceLabel;

    public AdminInterface() {
        setTitle("Interface Administrateur");
        setSize(600, 400);
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

        // Bouton de déconnexion avec nouveau style
        JButton logoutButton = new JButton("Déconnexion");
        logoutButton.setBackground(new Color(204, 0, 0));  // Rouge foncé
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setBounds(250, 300, 120, 30);
        logoutButton.addActionListener(e -> {
            new Login();
            dispose();
        });
        add(logoutButton);

        // Ajouter un fond noir avec une bordure
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icon/backbg.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 600, 400);
        add(backgroundLabel);

        loadAdminData();
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

    public static void main(String[] args) {
        new AdminInterface();
    }
}