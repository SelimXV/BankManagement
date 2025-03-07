package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class TransactionHistory extends JFrame {

    String cardNumber;

    public TransactionHistory(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("Historique des transactions");
        setSize(600, 500);
        setLocation(600, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Historique des transactions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Création du tableau pour afficher les transactions
        String[] columnNames = {"Montant", "Type", "Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Label pour afficher le solde du compte
        JLabel balanceLabel = new JLabel("Solde : Chargement...");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(JLabel.CENTER);
        add(balanceLabel, BorderLayout.SOUTH);

        // Charger les transactions et le solde
        loadTransactionHistory(model, balanceLabel);

        setVisible(true);
    }

    private void loadTransactionHistory(DefaultTableModel model, JLabel balanceLabel) {
        try {
            sqlcon con = new sqlcon();

            // Récupérer l'historique des transactions
            String query = "SELECT amount, type, transaction_date FROM transactions WHERE card_number = '" + cardNumber + "' ORDER BY transaction_date DESC";
            ResultSet rs = con.statement.executeQuery(query);
            while (rs.next()) {
                double amount = rs.getDouble("amount");
                String type = rs.getString("type");
                String date = rs.getString("transaction_date");
                model.addRow(new Object[]{amount + " €", type, date});
            }

            // Récupérer le solde actuel
            String balanceQuery = "SELECT balance FROM account WHERE card_number = '" + cardNumber + "'";
            ResultSet balanceRs = con.statement.executeQuery(balanceQuery);
            if (balanceRs.next()) {
                balanceLabel.setText("Solde actuel : " + balanceRs.getDouble("balance") + " €");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TransactionHistory("1234567890123456");
    }
}
