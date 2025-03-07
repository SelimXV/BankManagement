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
    JButton next;
    String formno;

    Signup() {
        super("APPLICATION DE GESTION BANCAIRE");

        Random random = new Random();
        formno = "" + (Math.abs(random.nextLong() % 9000L) + 1000L);

        JLabel labelTitle = new JLabel("Formulaire N°" + formno);
        labelTitle.setFont(new Font("Osward", Font.BOLD, 22));
        labelTitle.setBounds(290, 50, 400, 30);
        add(labelTitle);

        JLabel labelName = new JLabel("Nom :");
        labelName.setBounds(100, 150, 100, 30);
        add(labelName);
        textName = new JTextField();
        textName.setBounds(300, 150, 200, 30);
        add(textName);

        JLabel labelfName = new JLabel("Prénom :");
        labelfName.setBounds(100, 200, 100, 30);
        add(labelfName);
        textFName = new JTextField();
        textFName.setBounds(300, 200, 200, 30);
        add(textFName);

        JLabel DOB = new JLabel("Date de Naissance :");
        DOB.setBounds(100, 250, 150, 30);
        add(DOB);
        dateChooser = new JDateChooser();
        dateChooser.setBounds(300, 250, 200, 30);
        add(dateChooser);

        JLabel labelGender = new JLabel("Genre :");
        labelGender.setBounds(100, 300, 100, 30);
        add(labelGender);
        r1 = new JRadioButton("Homme");
        r1.setBounds(300, 300, 100, 30);
        add(r1);
        r2 = new JRadioButton("Femme");
        r2.setBounds(400, 300, 100, 30);
        add(r2);
        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);

        JLabel labelEmail = new JLabel("Email :");
        labelEmail.setBounds(100, 350, 100, 30);
        add(labelEmail);
        textEmail = new JTextField();
        textEmail.setBounds(300, 350, 200, 30);
        add(textEmail);

        JLabel labelAdd = new JLabel("Adresse :");
        labelAdd.setBounds(100, 400, 100, 30);
        add(labelAdd);
        textAdd = new JTextField();
        textAdd.setBounds(300, 400, 200, 30);
        add(textAdd);

        JLabel labelCity = new JLabel("Ville :");
        labelCity.setBounds(100, 450, 100, 30);
        add(labelCity);
        textCity = new JTextField();
        textCity.setBounds(300, 450, 200, 30);
        add(textCity);

        JLabel labelPostal = new JLabel("Code Postal :");
        labelPostal.setBounds(100, 500, 100, 30);
        add(labelPostal);
        textPostal = new JTextField();
        textPostal.setBounds(300, 500, 200, 30);
        add(textPostal);

        next = new JButton("SUIVANT");
        next.setBounds(350, 600, 100, 30);
        next.addActionListener(this);
        add(next);

        setSize(850, 700);
        setLocation(360, 40);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
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
            }
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
