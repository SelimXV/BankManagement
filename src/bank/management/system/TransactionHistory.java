package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class TransactionHistory extends JFrame implements ActionListener {

    String cardNumber;
    String currency;
    JButton backButton;
    JTable transactionTable;

    public TransactionHistory(String cardNumber) {
        this.cardNumber = cardNumber;

        // Configuration de la fenêtre
        setTitle("Historique des transactions");
        setSize(700, 600);
        setLocation(350, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panneau en-tête avec dégradé
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
        headerPanel.setPreferredSize(new Dimension(700, 100));
        
        // Icône bancaire
        ImageIcon bankIcon = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image scaledBankImage = bankIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon scaledBankIcon = new ImageIcon(scaledBankImage);
        JLabel bankIconLabel = new JLabel(scaledBankIcon);
        bankIconLabel.setBounds(20, 25, 50, 50);
        headerPanel.add(bankIconLabel);
        
        // Texte du titre
        JLabel titleLabel = new JLabel("Historique des transactions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(80, 30, 400, 40);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Création du tableau pour afficher les transactions avec style moderne
        String[] columnNames = {"ID", "Montant", "Type", "Date", "Solde après"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Tableau non modifiable
            }
        };
        
        transactionTable = new JTable(model);
        transactionTable.setRowHeight(30);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTable.setSelectionBackground(new Color(232, 242, 254));
        transactionTable.setSelectionForeground(Color.BLACK);
        transactionTable.setShowVerticalLines(false);
        transactionTable.setGridColor(new Color(230, 230, 230));
        
        // Style de l'en-tête du tableau
        JTableHeader header = transactionTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(0, 82, 165));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Scroll pane avec bordure esthétique
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panneau d'information sur le solde avec un style encadré
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        infoPanel.setBackground(new Color(240, 248, 255));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Label pour afficher le solde du compte
        JLabel balanceLabel = new JLabel("Solde actuel : Chargement...");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(new Color(0, 45, 98));
        infoPanel.add(balanceLabel);
        
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Panneau pour les boutons en bas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        // Bouton retour
        backButton = new JButton("RETOUR");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 102, 204));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);
        
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);

        // Charger les transactions et le solde
        loadTransactionHistory(model, balanceLabel);

        setResizable(false);
        setVisible(true);
    }

    private void loadTransactionHistory(DefaultTableModel model, JLabel balanceLabel) {
        try {
            sqlcon con = new sqlcon();

            // D'abord, récupérer la devise du compte
            String currencyQuery = "SELECT currency FROM account WHERE card_number = '" + cardNumber + "'";
            ResultSet rsCurrency = con.statement.executeQuery(currencyQuery);
            if (rsCurrency.next()) {
                currency = rsCurrency.getString("currency");
            }

            // Récupérer l'historique des transactions
            String query = "SELECT id, amount, type, transaction_date, balance_after FROM transactions " +
                           "WHERE card_number = '" + cardNumber + "' ORDER BY transaction_date DESC";
            ResultSet rs = con.statement.executeQuery(query);
            
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String type = rs.getString("type");
                String date = rs.getString("transaction_date");
                double balanceAfter = rs.getDouble("balance_after");
                
                // Formater le type de transaction
                String formattedType = "Autre";
                if (type.equalsIgnoreCase("deposit")) {
                    formattedType = "Dépôt";
                } else if (type.equalsIgnoreCase("withdraw")) {
                    formattedType = "Retrait";
                }
                
                // Ajouter la ligne au tableau
                model.addRow(new Object[]{
                    id,
                    String.format("%.2f %s", amount, currency),
                    formattedType,
                    date,
                    String.format("%.2f %s", balanceAfter, currency)
                });
            }

            // Récupérer le solde actuel
            String balanceQuery = "SELECT balance FROM account WHERE card_number = '" + cardNumber + "'";
            ResultSet balanceRs = con.statement.executeQuery(balanceQuery);
            if (balanceRs.next()) {
                double balance = balanceRs.getDouble("balance");
                balanceLabel.setText("Solde actuel : " + String.format("%.2f %s", balance, currency));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erreur lors du chargement des transactions: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new TransactionHistory("1234567890123456");
    }
}
