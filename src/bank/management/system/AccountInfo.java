package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AccountInfo extends JFrame implements ActionListener {

    String cardNumber;
    JButton closeAccountButton, closeButton;
    JLabel nameLabel, dobLabel, accountTypeLabel, balanceLabel, cardLabel;

    public AccountInfo(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("Informations du compte");
        setSize(600, 500);
        setLocation(500, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panneau d'en-tête avec dégradé
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(0, 45, 98);
                Color color2 = new Color(0, 82, 165);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setPreferredSize(new Dimension(600, 80));
        
        // Titre
        JLabel titleLabel = new JLabel("Détails du compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 20, 300, 40);
        headerPanel.add(titleLabel);
        
        // Icône de carte bancaire
        ImageIcon cardIcon = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        if (cardIcon.getIconWidth() > 0) {  // Vérifier que l'image existe
            Image scaledCardImage = cardIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon scaledCardIcon = new ImageIcon(scaledCardImage);
            JLabel cardIconLabel = new JLabel(scaledCardIcon);
            cardIconLabel.setBounds(130, 15, 50, 50);
            headerPanel.add(cardIconLabel);
        }
        
        add(headerPanel, BorderLayout.NORTH);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panneau d'informations avec un style carte
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBounds(50, 20, 500, 250);
        infoPanel.setBackground(new Color(248, 248, 248));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Labels pour afficher les informations du compte avec une meilleure disposition
        nameLabel = createInfoLabel("Nom : Chargement...", 20, 30, 460, 30);
        infoPanel.add(nameLabel);
        
        dobLabel = createInfoLabel("Date de naissance : Chargement...", 20, 65, 460, 30);
        infoPanel.add(dobLabel);
        
        cardLabel = createInfoLabel("Numéro de carte : " + cardNumber, 20, 100, 460, 30);
        infoPanel.add(cardLabel);
        
        accountTypeLabel = createInfoLabel("Type de compte : Chargement...", 20, 135, 460, 30);
        infoPanel.add(accountTypeLabel);
        
        // Ligne de séparation avant le solde
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 175, 460, 1);
        separator.setForeground(new Color(200, 200, 200));
        infoPanel.add(separator);
        
        // Label du solde avec style spécial
        balanceLabel = createInfoLabel("Solde : Chargement...", 20, 190, 460, 30);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(0, 102, 204));
        infoPanel.add(balanceLabel);
        
        mainPanel.add(infoPanel);
        
        // Panneau des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 290, 500, 120);
        buttonPanel.setBackground(Color.WHITE);
        
        // Bouton de clôture
        closeAccountButton = new JButton("CLÔTURER LE COMPTE");
        closeAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeAccountButton.setBackground(new Color(204, 0, 0));
        closeAccountButton.setForeground(Color.WHITE);
        closeAccountButton.setOpaque(true);
        closeAccountButton.setBorderPainted(false);
        closeAccountButton.setFocusPainted(false);
        closeAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeAccountButton.setBounds(150, 20, 200, 40);
        closeAccountButton.addActionListener(this);
        buttonPanel.add(closeAccountButton);
        
        // Bouton Fermer
        closeButton = new JButton("RETOUR");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(51, 51, 51));
        closeButton.setForeground(Color.WHITE);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setBounds(150, 70, 200, 40);
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);
        
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);

        // Charger les données depuis la base de données
        loadAccountInfo();

        setResizable(false);
        setVisible(true);
    }
    
    private JLabel createInfoLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBounds(x, y, width, height);
        return label;
    }

    private void loadAccountInfo() {
        try {
            sqlcon con = new sqlcon();
            // Jointure entre account (carte, solde, type) et signup (nom, prénom)
            String query = "SELECT s.name, s.fName, s.dob, s2.account_type, s2.balance, s2.currency " +
                    "FROM signup s JOIN account s2 ON s.form_no = s2.formno " +
                    "WHERE s2.card_number = '" + cardNumber + "'";
            ResultSet rs = con.statement.executeQuery(query);
            if (rs.next()) {
                String fullName = rs.getString("name") + " " + rs.getString("fName");
                String dob = rs.getString("dob");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                String currency = rs.getString("currency");

                nameLabel.setText("Nom : " + fullName);
                dobLabel.setText("Date de naissance : " + dob);
                accountTypeLabel.setText("Type de compte : " + accountType);
                balanceLabel.setText("Solde : " + String.format("%.2f %s", balance, currency));
            } else {
                JOptionPane.showMessageDialog(null, "Erreur : compte introuvable.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeAccountButton) {
            closeAccount();
        } else if (e.getSource() == closeButton) {
            new Accueil(cardNumber).setVisible(true);
            dispose();
        }
    }

    private void closeAccount() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Êtes-vous sûr de vouloir clôturer ce compte ?\nCette action est irréversible.",
            "Confirmation de clôture",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                sqlcon con = new sqlcon();
                
                // Vérifier d'abord le solde
                String balanceQuery = "SELECT balance FROM account WHERE card_number = '" + cardNumber + "'";
                ResultSet rs = con.statement.executeQuery(balanceQuery);
                
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance > 0) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Le compte ne peut pas être clôturé car il contient encore un solde de " + balance + " €.\nVeuillez d'abord retirer tous les fonds.",
                            "Solde non nul",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }

                // Clôturer le compte
                String closeQuery = "UPDATE account SET is_closed = TRUE WHERE card_number = '" + cardNumber + "'";
                con.statement.executeUpdate(closeQuery);

                JOptionPane.showMessageDialog(
                    this,
                    "Le compte a été clôturé avec succès.",
                    "Compte clôturé",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Retourner à la page de connexion
                new Login().setVisible(true);
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la clôture du compte : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AccountInfo("1234567890123456");
    }
}
