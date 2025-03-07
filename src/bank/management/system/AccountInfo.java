package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class AccountInfo extends JFrame {

    String cardNumber;

    public AccountInfo(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("Informations du compte");
        setSize(500, 400);
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

        JButton closeButton = new JButton("Fermer");
        closeButton.setBounds(180, 280, 120, 30);
        closeButton.addActionListener(e ->
                new Accueil(cardNumber).setVisible(true));
        add(closeButton);

        // Charger les données depuis la base de données
        loadAccountInfo(nameLabel, accountTypeLabel, balanceLabel, dobLabel);

        setVisible(true);
    }

    private void loadAccountInfo(JLabel nameLabel, JLabel accountTypeLabel, JLabel balanceLabel, JLabel dobLabel) {
        try {
            sqlcon con = new sqlcon();
            // Jointure entre account (carte, solde, type) et signup (nom, prénom)
            String query = "SELECT s.name, s.fName, s.dob, s2.account_type, s2.balance " +
                    "FROM signup s JOIN account s2 ON s.form_no = s2.formno " +
                    "WHERE s2.card_number = '" + cardNumber + "'";
            ResultSet rs = con.statement.executeQuery(query);
            if (rs.next()) {
                String fullName = rs.getString("name") + " " + rs.getString("fName");
                String dob = rs.getString("dob");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");

                nameLabel.setText("Nom : " + fullName);
                dobLabel.setText("Date de naissance : " + dob);
                accountTypeLabel.setText("Type de compte : " + accountType);
                balanceLabel.setText("Solde : " + balance + " €");
            } else {
                JOptionPane.showMessageDialog(null, "Erreur : compte introuvable.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AccountInfo("1234567890123456");
    }
}
