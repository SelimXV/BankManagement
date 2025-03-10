package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Deposit extends JFrame implements ActionListener {

    TextField textField;
    JButton depositButton, cancelButton;
    String cardNumber; // identifiant du compte connecté
    JLabel amountLabel;

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

        // Récupérer la devise du compte
        String currency = "";
        try {
            sqlcon con = new sqlcon();
            String query = "SELECT currency FROM account WHERE card_number = '" + cardNumber + "'";
            ResultSet rs = con.statement.executeQuery(query);
            if (rs.next()) {
                currency = rs.getString("currency");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label d'instruction avec la devise
        amountLabel = new JLabel("Veuillez insérer le montant à déposer (" + currency + ")");
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setFont(new Font("System", Font.BOLD, 16));
        amountLabel.setBounds(460, 180, 400, 35);
        l3.add(amountLabel);

        // Champ de saisie du montant
        textField = new TextField();
        textField.setBackground(new Color(65,125,128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 230, 320, 25);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        // Bouton "DÉPOSER"
        depositButton = new JButton("DÉPOSER");
        depositButton.setBounds(700, 362, 150, 35);
        depositButton.setBackground(new Color(0, 102, 204));  // Bleu
        depositButton.setForeground(Color.WHITE);
        depositButton.setOpaque(true);
        depositButton.setBorderPainted(false);
        depositButton.addActionListener(this);
        l3.add(depositButton);

        // Bouton "ANNULER"
        cancelButton = new JButton("ANNULER");
        cancelButton.setBounds(700, 412, 150, 35);
        cancelButton.setBackground(new Color(204, 0, 0));  // Rouge foncé
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(this);
        l3.add(cancelButton);

        setLayout(null);
        setSize(1500, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == depositButton) {
            String amountText = textField.getText();
            if(amountText.equals("") || amountText == null) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un montant.");
                return;
            }
            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Le montant doit être supérieur à 0.");
                    return;
                }

                sqlcon con = new sqlcon();
                try {
                    // Récupérer la devise pour l'affichage
                    String currencyQuery = "SELECT currency FROM account WHERE card_number = ?";
                    java.sql.PreparedStatement pstmt = con.connection.prepareStatement(currencyQuery);
                    pstmt.setString(1, cardNumber);
                    ResultSet rs = pstmt.executeQuery();
                    String currency = "";
                    if (rs.next()) {
                        currency = rs.getString("currency");
                    }

                    // Enregistrer seulement la transaction - le trigger dans MySQL s'occupera de mettre à jour le solde
                    String insertQuery = "INSERT INTO transactions (card_number, amount, type) VALUES (?, ?, 'deposit')";
                    pstmt = con.connection.prepareStatement(insertQuery);
                    pstmt.setString(1, cardNumber);
                    pstmt.setDouble(2, amount);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Dépôt de " + amount + " " + currency + " effectué avec succès !");
                    setVisible(false);
                    new Accueil(cardNumber);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du dépôt : " + ex.getMessage());
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Montant invalide. Veuillez entrer un nombre valide.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if(e.getSource() == cancelButton) {
            setVisible(false);
            new Accueil(cardNumber);
        }
    }

    public static void main(String[] args) {
        new Deposit("1234567890123456").setVisible(true);
    }
}
