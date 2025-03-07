package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Withdraw extends JFrame implements ActionListener {

    TextField textField;
    JButton withdrawButton, cancelButton;
    String cardNumber; // identifiant du compte connecté

    // Le constructeur reçoit le numéro de carte du compte connecté
    public Withdraw(String cardNumber) {
        this.cardNumber = cardNumber;

        // Fond avec image d'ATM
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        // Label d'instruction
        JLabel label1 = new JLabel("Veuillez insérer le montant à retirer");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 400, 35);
        l3.add(label1);

        // Champ de saisie du montant
        textField = new TextField();
        textField.setBackground(new Color(65,125,128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 230, 320, 25);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        // Bouton "RETIRER"
        withdrawButton = new JButton("RETIRER");
        withdrawButton.setBounds(700, 362, 150, 35);
        withdrawButton.setBackground(new Color(65,125,128));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.addActionListener(this);
        l3.add(withdrawButton);

        // Bouton "ANNULER"
        cancelButton = new JButton("ANNULER");
        cancelButton.setBounds(700, 412, 150, 35);
        cancelButton.setBackground(new Color(65,125,128));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        l3.add(cancelButton);

        setLayout(null);
        setSize(1500, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == withdrawButton) { // Bouton RETIRER
            String amountText = textField.getText();
            if(amountText.equals("") || amountText == null) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un montant.");
                return;
            }
            try {
                double amount = Double.parseDouble(amountText);
                sqlcon con = new sqlcon();

                // Vérifier le solde actuel pour s'assurer qu'il est suffisant
                String queryBalance = "SELECT balance FROM account WHERE card_number = '" + cardNumber + "'";
                ResultSet rs = con.statement.executeQuery(queryBalance);
                double currentBalance = 0;
                if(rs.next()) {
                    currentBalance = rs.getDouble("balance");
                }
                if(amount > currentBalance) {
                    JOptionPane.showMessageDialog(null, "Solde insuffisant !");
                    return;
                }

                // Insertion de la transaction de retrait dans la table transactions
                String insertQuery = "INSERT INTO transactions (card_number, amount, type) VALUES ('" + cardNumber + "', " + amount + ", 'withdraw')";
                con.statement.executeUpdate(insertQuery);

                // Mise à jour du solde dans la table account
                String updateQuery = "UPDATE account SET balance = balance - " + amount + " WHERE card_number = '" + cardNumber + "'";
                con.statement.executeUpdate(updateQuery);

                JOptionPane.showMessageDialog(null, "Retrait effectué avec succès !");
                setVisible(false);
                new Accueil(cardNumber);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } else if(e.getSource() == cancelButton) { // Bouton ANNULER
            setVisible(false);
            new Accueil(cardNumber);
        }
    }

    public static void main(String[] args) {
        // Pour tester, passe un numéro de carte fictif
        new Withdraw("1234567890123456").setVisible(true);
    }
}
