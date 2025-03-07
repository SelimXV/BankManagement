package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deposit extends JFrame implements ActionListener {

    TextField textField;
    JButton depositButton, cancelButton;
    String cardNumber; // identifiant du compte connecté

    // Le constructeur reçoit le numéro de carte du compte connecté
    public Deposit(String cardNumber) {
        this.cardNumber = cardNumber;

        // Fond avec image d'ATM
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        // Label d'instruction
        JLabel label1 = new JLabel("Veuillez insérer le montant à déposer");
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

        // Bouton "DEPOSER"
        depositButton = new JButton("DÉPOSER");
        depositButton.setBounds(700, 362, 150, 35);
        depositButton.setBackground(new Color(65,125,128));
        depositButton.setForeground(Color.WHITE);
        depositButton.addActionListener(this);
        l3.add(depositButton);

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
        if(e.getSource() == depositButton) { // Bouton DÉPOSER
            String amountText = textField.getText();
            if(amountText.equals("") || amountText == null) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un montant.");
                return;
            }
            try {
                double amount = Double.parseDouble(amountText);
                sqlcon con = new sqlcon();
                // Insérer la transaction dans la table transactions
                String insertQuery = "INSERT INTO transactions (card_number, amount, type) VALUES ('" + cardNumber + "', " + amount + ", 'deposit')";
                con.statement.executeUpdate(insertQuery);

                // Mettre à jour le solde dans la table account
                String updateQuery = "UPDATE account SET balance = balance + " + amount + " WHERE card_number = '" + cardNumber + "'";
                con.statement.executeUpdate(updateQuery);

                JOptionPane.showMessageDialog(null, "Dépôt effectué avec succès !");
                setVisible(false);
                new Accueil(cardNumber);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if(e.getSource() == cancelButton) { // Bouton ANNULER
            setVisible(false);
            new Accueil(cardNumber);

        }
    }

    public static void main(String[] args) {
        new Deposit("1234567890123456").setVisible(true);
    }
}
