package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField cardNumberField;
    JPasswordField pinField;
    JButton b1, b2, b3;

    Login() {
        setTitle("Bank Management System");

        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon1 = new ImageIcon(img1);
        JLabel label1 = new JLabel(scaledIcon1);
        label1.setBounds((850 - 100) / 2, 10, 100, 100);
        add(label1);

        ImageIcon icon3 = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        Image img3 = icon3.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon3 = new ImageIcon(img3);
        JLabel label3 = new JLabel(scaledIcon3);
        label3.setBounds(650, 340, 100, 100);
        add(label3);

        l1 = new JLabel("Bienvenue sur l'ATM");
        l1.setFont(new Font("Osward", Font.BOLD, 30));
        l1.setForeground(Color.CYAN);
        l1.setBounds(270, 125, 450, 40);
        add(l1);

        l2 = new JLabel("Numéro de carte :");
        l2.setFont(new Font("Raleway", Font.BOLD, 24));
        l2.setForeground(Color.WHITE);
        l2.setBounds(125, 200, 375, 30);
        add(l2);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(350, 200, 200, 30);
        add(cardNumberField);

        l3 = new JLabel("Code PIN :");
        l3.setFont(new Font("Raleway", Font.BOLD, 24));
        l3.setForeground(Color.WHITE);
        l3.setBounds(125, 260, 375, 30);
        add(l3);

        pinField = new JPasswordField();
        pinField.setBounds(350, 260, 200, 30);
        add(pinField);


        b1= new JButton("S'INSCRIRE");

        b1.setBackground(Color.red);
        b1.setForeground(Color.WHITE);
        b1.setOpaque(true);
        b1.setBorderPainted(false);
        b1.setBounds(340, 300, 100, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("EFFACER");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setOpaque(true);
        b2.setBorderPainted(false);
        b2.setBounds(460, 300, 100, 30);
        b2.addActionListener(this);
        add(b2);

        b3 = new JButton("SE CONNECTER");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        b3.setOpaque(true);
        b3.setBorderPainted(false);
        b3.setBounds(340, 350, 220, 30);
        b3.addActionListener(this);
        add(b3);



        ImageIcon icon2 = new ImageIcon(ClassLoader.getSystemResource("icon/arriereplan.jpg"));
        Image img2 = icon2.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon2 = new ImageIcon(img2);
        JLabel label2 = new JLabel(scaledIcon2);
        label2.setBounds(0, 0, 850, 480);
        add(label2);

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
            if (e.getSource() == b1) {
                new Signup();
                setVisible(false);


            } else if (e.getSource()==b2) {
                cardNumberField.setText("");
                pinField.setText("");
            } else if (e.getSource()==b3) {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Login();
    }
}