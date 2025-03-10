package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Accueil extends JFrame {

    String cardNumber;
    JLabel balanceLabel;
    JButton depositButton, logoutButton, WithdrawButton, AccountInfoButton, historyButton;

    public Accueil(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("Transactions - Espace Bancaire");
        setSize(850, 480);
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Message de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans votre espace bancaire !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(200, 50, 500, 50);
        add(welcomeLabel);

        // Affichage du numéro de carte
        JLabel cardLabel = new JLabel("Numéro de carte : " + cardNumber);
        cardLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        cardLabel.setBounds(200, 100, 500, 30);
        add(cardLabel);

        // Récupérer et afficher le solde
        balanceLabel = new JLabel("Solde : Chargement...");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setBounds(200, 150, 300, 30);
        add(balanceLabel);
        updateBalance();

        // Bouton pour accéder à la page Deposit
        depositButton = new JButton("DÉPÔT");
        depositButton.setFont(new Font("Arial", Font.BOLD, 16));
        depositButton.setBackground(new Color(0, 102, 204)); // Bleu
        depositButton.setForeground(Color.WHITE);
        depositButton.setOpaque(true);
        depositButton.setBorderPainted(false);
        depositButton.setBounds(350, 200, 150, 40);
        depositButton.addActionListener(e -> {
            new Deposit(cardNumber).setVisible(true);
            setVisible(false);
        });
        add(depositButton);

        WithdrawButton = new JButton("RETRAIT");
        WithdrawButton.setFont(new Font("Arial", Font.BOLD, 16));
        WithdrawButton.setBackground(new Color(0, 102, 204)); // Bleu
        WithdrawButton.setForeground(Color.WHITE);
        WithdrawButton.setOpaque(true);
        WithdrawButton.setBorderPainted(false);
        WithdrawButton.setBounds(350, 240, 150, 40);
        WithdrawButton.addActionListener(e -> {
            new Withdraw(cardNumber).setVisible(true);
            setVisible(false);
        });
        add(WithdrawButton);

        // Bouton "Infos du compte"
        AccountInfoButton = new JButton("Infos du compte");
        AccountInfoButton.setFont(new Font("Arial", Font.BOLD, 16));
        AccountInfoButton.setBackground(new Color(51, 51, 51)); // Gris foncé
        AccountInfoButton.setForeground(Color.WHITE);
        AccountInfoButton.setOpaque(true);
        AccountInfoButton.setBorderPainted(false);
        AccountInfoButton.setBounds(350, 280, 150, 40);
        AccountInfoButton.addActionListener(e -> {
            new AccountInfo(cardNumber).setVisible(true);
            setVisible(false);
        });
        add(AccountInfoButton);

        // Bouton "Historique des transactions"
        historyButton = new JButton("Historique");
        historyButton.setFont(new Font("Arial", Font.BOLD, 16));
        historyButton.setBackground(new Color(51, 51, 51)); // Gris foncé
        historyButton.setForeground(Color.WHITE);
        historyButton.setOpaque(true);
        historyButton.setBorderPainted(false);
        historyButton.setBounds(350, 360, 150, 40);
        historyButton.addActionListener(e -> {
            new TransactionHistory(cardNumber).setVisible(true);
        });
        add(historyButton);

        // Bouton de déconnexion
        logoutButton = new JButton("Déconnexion");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(204, 0, 0)); // Rouge foncé
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setBounds(350, 400, 150, 40);
        logoutButton.addActionListener(e -> {
            new Login();
            setVisible(false);
        });
        add(logoutButton);

        setVisible(true);
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
