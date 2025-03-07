package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Signup2 extends JFrame implements ActionListener {

    JRadioButton r1, r2, r3, r4;
    JCheckBox c1, c2, c3, c4, c5, c6;
    JButton s, c;
    String formno;
    String cardNumber;
    String pin;

    // Constructeur qui reçoit le numéro de formulaire
    Signup2(String first) {
        this.formno = first;

        // Configuration de la fenêtre
        setTitle("Création de compte - Étape 2");
        setSize(850, 800);
        setLocation(360, 40);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);

        // Affichage de l'icône de la banque
        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon1 = new ImageIcon(img1);
        JLabel label1 = new JLabel(scaledIcon1);
        label1.setBounds(25, 10, 100, 100);
        add(label1);

        // Affichage du numéro de formulaire
        JLabel l12  = new JLabel("Note :");
        l12.setFont(new Font("Raleway", Font.BOLD, 12));
        l12.setBounds(700, 10, 200, 30);
        l12.setForeground(Color.WHITE);
        add(l12);
        JLabel l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD, 12));
        l13.setBounds(760, 10, 200, 30);
        l13.setForeground(Color.WHITE);
        add(l13);

        // Titres et en-têtes
        JLabel l1 = new JLabel("Page 2 :");
        l1.setFont(new Font("Osward", Font.BOLD, 22));
        l1.setForeground(Color.WHITE);
        l1.setBounds(160, 40, 600, 30);
        add(l1);
        JLabel l2 = new JLabel("Détails du compte ");
        l2.setFont(new Font("Osward", Font.BOLD, 38));
        l2.setForeground(Color.WHITE);
        l2.setBounds(300, 40, 600, 100);
        add(l2);

        // Section : Type de compte
        JLabel l3 = new JLabel("Type de compte :");
        l3.setFont(new Font("Raleway", Font.BOLD, 20));
        l3.setForeground(Color.WHITE);
        l3.setBounds(100, 150, 200, 30);
        add(l3);

        r1 = new JRadioButton("Livret d'Épargne");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);
        r1.setBounds(100, 200, 200, 30);
        r1.setOpaque(false);
        r1.setForeground(Color.WHITE);
        add(r1);
        r2 = new JRadioButton("Compte à terme");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);
        r2.setBounds(250, 200, 200, 30);
        r2.setOpaque(false);
        r2.setForeground(Color.WHITE);
        add(r2);
        r3 = new JRadioButton("Compte courant");
        r3.setFont(new Font("Raleway", Font.BOLD, 14));
        r3.setBackground(Color.WHITE);
        r3.setBounds(400, 200, 200, 30);
        r3.setOpaque(false);
        r3.setForeground(Color.WHITE);
        add(r3);
        r4 = new JRadioButton("Compte-titre");
        r4.setFont(new Font("Raleway", Font.BOLD, 14));
        r4.setBackground(Color.WHITE);
        r4.setBounds(550, 200, 200, 30);
        r4.setOpaque(false);
        r4.setForeground(Color.WHITE);
        add(r4);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);
        buttonGroup.add(r3);
        buttonGroup.add(r4);

        // Section : Numéro de Carte
        JLabel l4  = new JLabel("Numéro de Carte :");
        l4.setFont(new Font("Raleway", Font.BOLD, 18));
        l4.setForeground(Color.WHITE);
        l4.setBounds(100, 250, 200, 30);
        add(l4);
        JLabel l5  = new JLabel("(Numéro de carte à 16 chiffres)");
        l5.setFont(new Font("Raleway", Font.BOLD, 12));
        l5.setForeground(Color.WHITE);
        l5.setBounds(100, 280, 200, 30);
        add(l5);

        // Génération du numéro de carte et du code PIN
        cardNumber = generateUniqueCardNumber();
        pin = generatePin();

        JLabel l6  = new JLabel(cardNumber);
        l6.setFont(new Font("Raleway", Font.BOLD, 18));
        l6.setForeground(Color.WHITE);
        l6.setBounds(400, 250, 400, 30);
        add(l6);
        JLabel l7  = new JLabel("(Celle ci apparaitra sur votre carte, chèque, etc.)");
        l7.setFont(new Font("Raleway", Font.BOLD, 12));
        l7.setForeground(Color.WHITE);
        l7.setBounds(400, 280, 400, 30);
        add(l7);

        // Section : Code PIN
        JLabel l8  = new JLabel("Code PIN :");
        l8.setFont(new Font("Raleway", Font.BOLD, 18));
        l8.setForeground(Color.WHITE);
        l8.setBounds(100, 330, 200, 30);
        add(l8);
        JLabel l9  = new JLabel("(Code PIN à 4 chiffres)");
        l9.setFont(new Font("Raleway", Font.BOLD, 12));
        l9.setForeground(Color.WHITE);
        l9.setBounds(100, 360, 200, 30);
        add(l9);
        JLabel l10  = new JLabel(pin);
        l10.setFont(new Font("Raleway", Font.BOLD, 18));
        l10.setForeground(Color.WHITE);
        l10.setBounds(400, 330, 200, 30);
        add(l10);

        // Section : Services Requis
        JLabel l11  = new JLabel("Services Requis");
        l11.setFont(new Font("Raleway", Font.BOLD, 18));
        l11.setForeground(Color.WHITE);
        l11.setBounds(100, 400, 200, 30);
        add(l11);
        c1 = new JCheckBox("Relevé de compte électronique");
        c1.setFont(new Font("Raleway", Font.BOLD, 14));
        c1.setBackground(Color.BLACK);
        c1.setForeground(Color.WHITE);
        c1.setBounds(100, 440, 300, 30);
        add(c1);
        c2 = new JCheckBox("Carte de crédit");
        c2.setFont(new Font("Raleway", Font.BOLD, 14));
        c2.setBackground(Color.BLACK);
        c2.setForeground(Color.WHITE);
        c2.setBounds(450, 440, 200, 30);
        add(c2);
        c3 = new JCheckBox("Chéquier");
        c3.setFont(new Font("Raleway", Font.BOLD, 14));
        c3.setBackground(Color.BLACK);
        c3.setForeground(Color.WHITE);
        c3.setBounds(100, 520, 200, 30);
        add(c3);
        c4 = new JCheckBox("Assurance compte");
        c4.setFont(new Font("Raleway", Font.BOLD, 14));
        c4.setBackground(Color.BLACK);
        c4.setForeground(Color.WHITE);
        c4.setBounds(450, 520, 200, 30);
        add(c4);
        c5 = new JCheckBox("Accès mobile banking");
        c5.setFont(new Font("Raleway", Font.BOLD, 14));
        c5.setBackground(Color.BLACK);
        c5.setForeground(Color.WHITE);
        c5.setBounds(100, 600, 200, 30);
        add(c5);
        c6 = new JCheckBox("Alertes de soldes");
        c6.setFont(new Font("Raleway", Font.BOLD, 14));
        c6.setBackground(Color.BLACK);
        c6.setForeground(Color.WHITE);
        c6.setBounds(450, 600, 400, 30);
        add(c6);
        JCheckBox c7 = new JCheckBox("Je confirme que les informations fournies sont correctes", true);
        c7.setFont(new Font("Raleway", Font.BOLD, 12));
        c7.setBackground(Color.BLACK);
        c7.setForeground(Color.WHITE);
        c7.setBounds(100, 650, 400, 30);
        add(c7);

        // Boutons SUIVANT et ANNULER
        s = new JButton("SUIVANT");
        s.setBackground(Color.BLACK);
        s.setForeground(Color.WHITE);
        s.setOpaque(true);
        s.setBorderPainted(false);
        s.setBounds(650, 650, 100, 30);
        s.addActionListener(this);
        add(s);
        c = new JButton("ANNULER");
        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);
        c.setOpaque(true);
        c.setBorderPainted(false);
        c.setBounds(500, 650, 100, 30);
        c.addActionListener(this);
        add(c);

        // Finalisation de la fenêtre
        setSize(850, 800);
        setLocation(360, 40);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }

    private String generateUniqueCardNumber() {
        Random random = new Random();
        return String.valueOf(1000000000000000L + (Math.abs(random.nextLong()) % 9000000000000000L));
    }

    private String generatePin() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == s) {
            // Récupérer le type de compte sélectionné
            String accountType = "";
            if (r1.isSelected()) {
                accountType = "Livret d'Épargne";
            } else if (r2.isSelected()) {
                accountType = "Compte à terme";
            } else if (r3.isSelected()) {
                accountType = "Compte courant";
            } else if (r4.isSelected()) {
                accountType = "Compte-titre";
            }
            accountType = accountType.replace("'", "''");
            // Récupérer les services sélectionnés
            int eStatement = c1.isSelected() ? 1 : 0;
            int creditCard = c2.isSelected() ? 1 : 0;
            int chequebook = c3.isSelected() ? 1 : 0;
            int accountInsurance = c4.isSelected() ? 1 : 0;
            int mobileBanking = c5.isSelected() ? 1 : 0;
            int balanceAlerts = c6.isSelected() ? 1 : 0;

            try {
                sqlcon c1 = new sqlcon();
                String query = "INSERT INTO account (formno, card_number, pin, account_type, e_statement, credit_card, chequebook, account_insurance, mobile_banking, balance_alerts) " +
                        "VALUES ('" + formno + "', '" + cardNumber + "', '" + pin + "', '" + accountType + "', " + eStatement + ", " + creditCard + ", " + chequebook + ", " + accountInsurance + ", " + mobileBanking + ", " + balanceAlerts + ")";
                c1.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Compte créé avec succès !\nNuméro de carte : " + cardNumber + "\nCode PIN : " + pin);
                setVisible(false);
                new Login();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == c) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Signup2("1234");
    }
}
