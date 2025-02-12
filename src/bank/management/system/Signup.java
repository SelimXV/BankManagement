package bank.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Signup extends JFrame implements ActionListener {

    // Declare text fields and date chooser
    JTextField textName, textFName, textEmail, textAdd, textCity, textPostal;
    JDateChooser dateChooser;

    // Declare radio buttons
    JRadioButton r1, r2;
    JButton next;

    Signup() {
        super("APPLICATION DE GESTION BANCAIRE");

        // Add bank icon
        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon1 = new ImageIcon(img1);
        JLabel label1 = new JLabel(scaledIcon1);
        label1.setBounds(25, 10, 100, 100);
        add(label1);

        // Add application form label
        JLabel l1 = new JLabel("APPLICATION FORM N°");
        l1.setFont(new Font("Osward", Font.BOLD, 38));
        l1.setForeground(Color.WHITE);
        l1.setBounds(160, 20, 600, 30);
        add(l1);

        // Add page number label
        JLabel l2 = new JLabel("Page 1");
        l2.setFont(new Font("Osward", Font.BOLD, 22));
        l2.setForeground(Color.WHITE);
        l2.setBounds(370, 80, 600, 30);
        add(l2);

        // Add personal information label
        JLabel l3 = new JLabel("Informations personnelles");
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("Osward", Font.BOLD, 22));
        l3.setBounds(290, 105, 600, 30);
        add(l3);

        // Add name label and text field
        JLabel labelName = new JLabel("Nom :");
        labelName.setFont(new Font("Raleway", Font.BOLD, 20));
        labelName.setForeground(Color.WHITE);
        labelName.setBounds(100, 200, 100, 30);
        add(labelName);

        textName = new JTextField();
        textName.setFont(new Font("Raleway", Font.BOLD, 14));
        textName.setBounds(300, 200, 200, 30);
        add(textName);

        // Add first name label and text field
        JLabel labelfName = new JLabel("Prénom :");
        labelfName.setFont(new Font("Raleway", Font.BOLD, 20));
        labelfName.setForeground(Color.WHITE);
        labelfName.setBounds(100, 250, 100, 30);
        add(labelfName);

        textFName = new JTextField();
        textFName.setFont(new Font("Raleway", Font.BOLD, 14));
        textFName.setBounds(300, 250, 200, 30);
        add(textFName);

        // Add date of birth label and date chooser
        JLabel DOB = new JLabel("Date de Naissance :");
        DOB.setFont(new Font("Raleway", Font.BOLD, 20));
        DOB.setForeground(Color.WHITE);
        DOB.setBounds(90, 300, 250, 30);
        add(DOB);

        dateChooser = new JDateChooser();
        dateChooser.setForeground(new Color(105, 105, 105));
        dateChooser.setBounds(300, 300, 200, 30);
        add(dateChooser);

        // Add gender label and radio buttons
        JLabel labelGender = new JLabel("Genre :");
        labelGender.setFont(new Font("Raleway", Font.BOLD, 20));
        labelGender.setForeground(Color.WHITE);
        labelGender.setBounds(100, 350, 100, 30);
        add(labelGender);

        r1 = new JRadioButton("Homme");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBounds(300, 350, 100, 30);
        r1.setOpaque(false);
        r1.setForeground(Color.WHITE);
        add(r1);

        r2 = new JRadioButton("Femme");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBounds(450, 350, 100, 30);
        r2.setForeground(Color.WHITE);
        r2.setOpaque(false);
        add(r2);

        // Group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);

        // Add email label and text field
        JLabel labelEmail = new JLabel("Email :");
        labelEmail.setFont(new Font("Raleway", Font.BOLD, 20));
        labelEmail.setForeground(Color.WHITE);
        labelEmail.setBounds(100, 400, 100, 30);
        add(labelEmail);

        textEmail = new JTextField();
        textEmail.setFont(new Font("Raleway", Font.BOLD, 14));
        textEmail.setBounds(300, 400, 200, 30);
        add(textEmail);

        // Add address label and text field
        JLabel labelAdd = new JLabel("Adress :");
        labelAdd.setFont(new Font("Raleway", Font.BOLD, 20));
        labelAdd.setForeground(Color.WHITE);
        labelAdd.setBounds(100, 450, 100, 30);
        add(labelAdd);

        textAdd = new JTextField();
        textAdd.setFont(new Font("Raleway", Font.BOLD, 14));
        textAdd.setBounds(300, 450, 200, 30);
        add(textAdd);

        // Add city label and text field
        JLabel labelCity = new JLabel("City :");
        labelCity.setFont(new Font("Raleway", Font.BOLD, 20));
        labelCity.setForeground(Color.WHITE);
        labelCity.setBounds(100, 500, 100, 30);
        add(labelCity);

        textCity = new JTextField();
        textCity.setFont(new Font("Raleway", Font.BOLD, 14));
        textCity.setBounds(300, 500, 200, 30);
        add(textCity);

        // Code Postal label and text field
        JLabel labelPostal = new JLabel("Code Postal :");
        labelPostal.setFont(new Font("Raleway", Font.BOLD, 20));
        labelPostal.setForeground(Color.WHITE);
        labelPostal.setBounds(100, 550, 200, 30);
        add(labelPostal);

        textPostal = new JTextField();
        textPostal.setFont(new Font("Raleway", Font.BOLD, 14));
        textPostal.setBounds(300, 550, 200, 30);
        add(textPostal);

        // Add next button
        next = new JButton("SUIVANT");
        next.setFont(new Font("Raleway", Font.BOLD, 14));
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.setBounds(650, 650, 100, 30);
        next.addActionListener(this);
        add(next);

        // Configure the frame
        setSize(850, 800);
        setLocation(360, 40);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = textName.getText();
        String fName = textFName.getText();
        String dobString = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String gender = null;
        if (r1.isSelected()) {
            gender = "Homme";
        } else if (r2.isSelected()) {
            gender = "Femme";
        }

        String email = textEmail.getText();
        String address = textAdd.getText();
        String city = textCity.getText();
        String postal = textPostal.getText();

        try {
            if (name.equals("") || fName.equals("") || dobString.equals("") || email.equals("") || address.equals("") || city.equals("") || postal.equals("")) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
            } else {
                // Convert dobString to java.sql.Date with French locale
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.FRENCH);
                java.util.Date utilDate = sdf.parse(dobString);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                // Save the data to the database
                sqlcon c1 = new sqlcon();
                String q1 = "insert into signup (name, fName, dob, gender, email, address, city, postal) values('" + name + "','" + fName + "','" + sqlDate + "','" + gender + "','" + email + "','" + address + "','" + city + "','" + postal + "')";
                c1.statement.executeUpdate(q1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}