package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class AccountInfo extends JFrame {

    String cardNumber;

    public AccountInfo(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("Informations du compte");
        setSize(500, 450); // Augmenté la hauteur pour le nouveau bouton
        setLocation(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Détails du compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(150, 30, 300, 30);
        add(titleLabel);

        JLabel nameLabel = new JLabel("Nom : Chargement...");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setBounds(50, 100, 400, 30);
        add(nameLabel);

        JLabel dobLabel = new JLabel("Date de naissance : Chargement...");
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dobLabel.setBounds(50, 120, 400, 30);
        add(dobLabel);

        JLabel cardLabel = new JLabel("Numéro de carte : " + cardNumber);
        cardLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cardLabel.setBounds(50, 140, 400, 30);
        add(cardLabel);

        JLabel accountTypeLabel = new JLabel("Type de compte : Chargement...");
        accountTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accountTypeLabel.setBounds(50, 180, 400, 30);
        add(accountTypeLabel);

        JLabel balanceLabel = new JLabel("Solde : Chargement...");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setBounds(50, 220, 400, 30);
        add(balanceLabel);

        // Bouton de clôture
        JButton closeAccountButton = new JButton("Clôturer le compte");
        closeAccountButton.setBackground(new Color(204, 0, 0));
        closeAccountButton.setForeground(Color.WHITE);
        closeAccountButton.setOpaque(true);
        closeAccountButton.setBorderPainted(false);
        closeAccountButton.setBounds(180, 320, 150, 30);
        closeAccountButton.addActionListener(e -> closeAccount());
        add(closeAccountButton);

        // Bouton Fermer (déplacé plus bas)
        JButton closeButton = new JButton("Fermer");
        closeButton.setBackground(new Color(51, 51, 51));
        closeButton.setForeground(Color.WHITE);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.setBounds(180, 360, 150, 30);
        closeButton.addActionListener(e -> {
            new Accueil(cardNumber).setVisible(true);
            dispose();
        });
        add(closeButton);

        // Charger les données depuis la base de données
        loadAccountInfo(nameLabel, accountTypeLabel, balanceLabel, dobLabel);

        setVisible(true);
    }

    private void loadAccountInfo(JLabel nameLabel, JLabel accountTypeLabel, JLabel balanceLabel, JLabel dobLabel) {
        try {
            sqlcon con = new sqlcon();
            // Jointure entre account (carte, solde, type) et signup (nom, prénom)
            String query = "SELECT s.name, s.fName, s.dob, s2.account_type, s2.balance, s2.currency " +
                    "FROM signup s JOIN account s2 ON s.form_no = s2.formno " +
                    "WHERE s2.card_number = '" + cardNumber + "'";
            ResultSet rs = con.statement.executeQuery(query);
            if (rs.next()) {
                String fullName = rs.getString("name") + " " + rs.getString("fName");
                String dob = rs.getString("dob");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                String currency = rs.getString("currency");

                nameLabel.setText("Nom : " + fullName);
                dobLabel.setText("Date de naissance : " + dob);
                accountTypeLabel.setText("Type de compte : " + accountType);
                balanceLabel.setText("Solde : " + balance + " " + currency);
            } else {
                JOptionPane.showMessageDialog(null, "Erreur : compte introuvable.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeAccount() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Êtes-vous sûr de vouloir clôturer ce compte ?\nCette action est irréversible.",
            "Confirmation de clôture",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                sqlcon con = new sqlcon();
                
                // Vérifier d'abord le solde
                String balanceQuery = "SELECT balance FROM account WHERE card_number = '" + cardNumber + "'";
                ResultSet rs = con.statement.executeQuery(balanceQuery);
                
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance > 0) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Le compte ne peut pas être clôturé car il contient encore un solde de " + balance + " €.\nVeuillez d'abord retirer tous les fonds.",
                            "Solde non nul",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }

                // Clôturer le compte
                String closeQuery = "UPDATE account SET is_closed = TRUE WHERE card_number = '" + cardNumber + "'";
                con.statement.executeUpdate(closeQuery);

                JOptionPane.showMessageDialog(
                    this,
                    "Le compte a été clôturé avec succès.",
                    "Compte clôturé",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Retourner à la page de connexion
                new Login().setVisible(true);
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la clôture du compte : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AccountInfo("1234567890123456");
    }
}
