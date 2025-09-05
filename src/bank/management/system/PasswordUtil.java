package bank.management.system;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class PasswordUtil {
    
    private static final String ADMIN_PASSWORD = "mdp123";
    
    public static boolean verifyAdminPassword(String password) {
        // Vérification simple pour "mdp123"
        if (password.equals(ADMIN_PASSWORD)) {
            return true;
        }
        
        try {
            // Vérification avec le hash stocké en base de données (si disponible)
            sqlcon c = new sqlcon();
            String query = "SELECT password_hash FROM admin WHERE username = 'admin'";
            ResultSet rs = c.statement.executeQuery(query);
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                // Utilisation de BCrypt pour vérifier le mot de passe
                return BCrypt.checkPassword(password, storedHash);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la vérification du mot de passe: " + e.getMessage());
        }
        
        return false;
    }
    
    // Méthode pour générer un hash d'un mot de passe avec BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashPassword(password);
    }
    
    // Méthode pour générer et afficher uniquement dans la console le hash du mot de passe admin
    public static void printAdminPasswordHash() {
        String hash = hashPassword(ADMIN_PASSWORD);
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Hash BCrypt pour le mot de passe admin '" + ADMIN_PASSWORD + "' :");
        System.out.println(hash);
        System.out.println("--------------------------------------------------------------\n");
    }
    
    // Méthode utilitaire pour générer et afficher un hash BCrypt (pour les tests)
    public static void generateAndPrintHash(String password) {
        String hash = hashPassword(password);
        System.out.println("Hash BCrypt pour '" + password + "': " + hash);
        JOptionPane.showMessageDialog(null, 
            "Hash BCrypt pour '" + password + "':\n\n" + hash + "\n\n" +
            "Ce hash a été affiché dans la console pour faciliter la copie.",
            "Hash généré", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Méthode utilitaire pour générer et afficher le hash du mot de passe admin par défaut
    public static void generateDefaultAdminPasswordHash() {
        generateAndPrintHash(ADMIN_PASSWORD);
    }
}