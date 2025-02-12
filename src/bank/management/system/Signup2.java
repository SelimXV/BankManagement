package bank.management.system;

import javax.swing.*;
import java.awt.*;

public class Signup2 extends JFrame {

    JRadioButton r1, r2, r3 ,r4;

    Signup2() {

        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon1 = new ImageIcon(img1);
        JLabel label1 = new JLabel(scaledIcon1);
        label1.setBounds(25, 10, 100, 100);
        add(label1);


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

        JLabel l6  = new JLabel("XXXX-XXXX-XXXX-XXXX");
        l6.setFont(new Font("Raleway", Font.BOLD, 18));
        l6.setForeground(Color.WHITE);
        l6.setBounds(400, 250, 400, 30);
        add(l6);

        JLabel l7  = new JLabel("(Celle ci apparaitra sur votre carte, chèque, etc.)");
        l7.setFont(new Font("Raleway", Font.BOLD, 12));
        l7.setForeground(Color.WHITE);
        l7.setBounds(400, 280, 400, 30);
        add(l7);

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

        JLabel l10  = new JLabel("XXXX");
        l10.setFont(new Font("Raleway", Font.BOLD, 18));
        l10.setForeground(Color.WHITE);
        l10.setBounds(400, 330, 200, 30);
        add(l10);











        setSize(850, 800);
        setLocation(360, 40);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);



    }

    public static void main(String[] args) {
        new Signup2();
    }
}
