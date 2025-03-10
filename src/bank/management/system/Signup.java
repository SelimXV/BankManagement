package bank.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

public class Signup extends JFrame implements ActionListener {
    JTextField textName, textFName, textEmail, textAdd, textCity, textPostal;
    JDateChooser dateChooser;
    JRadioButton r1, r2;
    JButton next, cancel;
    String formno;

    Signup() {
        super("APPLICATION DE GESTION BANCAIRE");
        
        // Génération du numéro de formulaire
        Random random = new Random();
        formno = "" + (Math.abs(random.nextLong() % 9000L) + 1000L);
        
        // Configuration de la fenêtre
        setSize(850, 800);
        setLocation(360, 40);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        
        // Affichage de l'icône de la banque
        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon1 = new ImageIcon(img1);
        JLabel label1 = new JLabel(scaledIcon1);
        label1.setBounds(25, 10, 100, 100);
        add(label1);
        
        // Affichage du numéro de formulaire
        JLabel l12 = new JLabel("Note :");
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
        JLabel l1 = new JLabel("Page 1 :");
        l1.setFont(new Font("Osward", Font.BOLD, 22));
        l1.setForeground(Color.WHITE);
        l1.setBounds(160, 40, 600, 30);
        add(l1);
        JLabel labelTitle = new JLabel("Informations personnelles");
        labelTitle.setFont(new Font("Osward", Font.BOLD, 38));
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setBounds(290, 40, 600, 100);
        add(labelTitle);

        // Champs du formulaire
        JLabel labelName = new JLabel("Nom :");
        labelName.setFont(new Font("Raleway", Font.BOLD, 20));
        labelName.setForeground(Color.WHITE);
        labelName.setBounds(100, 150, 100, 30);
        add(labelName);
        textName = new JTextField();
        textName.setFont(new Font("Raleway", Font.BOLD, 14));
        textName.setBounds(300, 150, 400, 30);
        add(textName);

        JLabel labelfName = new JLabel("Prénom :");
        labelfName.setFont(new Font("Raleway", Font.BOLD, 20));
        labelfName.setForeground(Color.WHITE);
        labelfName.setBounds(100, 200, 100, 30);
        add(labelfName);
        textFName = new JTextField();
        textFName.setFont(new Font("Raleway", Font.BOLD, 14));
        textFName.setBounds(300, 200, 400, 30);
        add(textFName);

        JLabel DOB = new JLabel("Date de Naissance :");
        DOB.setFont(new Font("Raleway", Font.BOLD, 20));
        DOB.setForeground(Color.WHITE);
        DOB.setBounds(100, 250, 200, 30);
        add(DOB);
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Raleway", Font.BOLD, 14));
        dateChooser.setForeground(new Color(105, 105, 105));
        dateChooser.setBounds(300, 250, 400, 30);
        add(dateChooser);

        JLabel labelGender = new JLabel("Genre :");
        labelGender.setFont(new Font("Raleway", Font.BOLD, 20));
        labelGender.setForeground(Color.WHITE);
        labelGender.setBounds(100, 300, 100, 30);
        add(labelGender);
        r1 = new JRadioButton("Homme");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBounds(300, 300, 100, 30);
        r1.setBackground(Color.BLACK);
        r1.setForeground(Color.WHITE);
        add(r1);
        r2 = new JRadioButton("Femme");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBounds(450, 300, 100, 30);
        r2.setBackground(Color.BLACK);
        r2.setForeground(Color.WHITE);
        add(r2);
        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);

        JLabel labelEmail = new JLabel("Email :");
        labelEmail.setFont(new Font("Raleway", Font.BOLD, 20));
        labelEmail.setForeground(Color.WHITE);
        labelEmail.setBounds(100, 350, 100, 30);
        add(labelEmail);
        textEmail = new JTextField();
        textEmail.setFont(new Font("Raleway", Font.BOLD, 14));
        textEmail.setBounds(300, 350, 400, 30);
        add(textEmail);

        JLabel labelAdd = new JLabel("Adresse :");
        labelAdd.setFont(new Font("Raleway", Font.BOLD, 20));
        labelAdd.setForeground(Color.WHITE);
        labelAdd.setBounds(100, 400, 100, 30);
        add(labelAdd);
        textAdd = new JTextField();
        textAdd.setFont(new Font("Raleway", Font.BOLD, 14));
        textAdd.setBounds(300, 400, 400, 30);
        add(textAdd);

        JLabel labelCity = new JLabel("Ville :");
        labelCity.setFont(new Font("Raleway", Font.BOLD, 20));
        labelCity.setForeground(Color.WHITE);
        labelCity.setBounds(100, 450, 100, 30);
        add(labelCity);
        textCity = new JTextField();
        textCity.setFont(new Font("Raleway", Font.BOLD, 14));
        textCity.setBounds(300, 450, 400, 30);
        add(textCity);

        JLabel labelPostal = new JLabel("Code Postal :");
        labelPostal.setFont(new Font("Raleway", Font.BOLD, 20));
        labelPostal.setForeground(Color.WHITE);
        labelPostal.setBounds(100, 500, 150, 30);
        add(labelPostal);
        textPostal = new JTextField();
        textPostal.setFont(new Font("Raleway", Font.BOLD, 14));
        textPostal.setBounds(300, 500, 400, 30);
        add(textPostal);

        // Boutons SUIVANT et ANNULER avec nouveau style
        next = new JButton("SUIVANT");
        next.setBackground(new Color(0, 102, 204)); // Bleu
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Raleway", Font.BOLD, 14));
        next.setOpaque(true);
        next.setBorderPainted(false);
        next.setCursor(new Cursor(Cursor.HAND_CURSOR));
        next.setBounds(500, 600, 100, 30);
        next.addActionListener(this);
        add(next);

        cancel = new JButton("ANNULER");
        cancel.setBackground(new Color(204, 0, 0)); // Rouge foncé
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Raleway", Font.BOLD, 14));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.setBounds(350, 600, 100, 30);
        cancel.addActionListener(e -> {
            setVisible(false);
            new Login().setVisible(true);
        });
        add(cancel);

        // Finalisation de la fenêtre
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = textName.getText();
        String fName = textFName.getText();
        String dobString = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String gender = (r1.isSelected()) ? "Homme" : (r2.isSelected()) ? "Femme" : "";
        String email = textEmail.getText();
        String address = textAdd.getText();
        String city = textCity.getText();
        String postal_code = textPostal.getText();

        if (name.isEmpty() || fName.isEmpty() || dobString.isEmpty() || gender.isEmpty() || email.isEmpty() || address.isEmpty() || city.isEmpty() || postal_code.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
        } else {
            try {
                // Correction du format de la date
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
                java.util.Date utilDate = sdf.parse(dobString);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                sqlcon c1 = new sqlcon();
                String query = "INSERT INTO signup (form_no, name, fName, dob, gender, email, address, city, postal_code) " +
                        "VALUES ('" + formno + "', '" + name + "', '" + fName + "', '" + sqlDate + "', '" + gender + "', '" + email + "', '" + address + "', '" + city + "', '" + postal_code + "')";
                c1.statement.executeUpdate(query);
                new Signup2(formno);
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
