package bank.management.system;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Une implémentation de BCrypt pour le hashage sécurisé des mots de passe
 */
public class BCrypt {
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Hashe un mot de passe avec un sel généré aléatoirement
     * @param password Le mot de passe à hasher
     * @return Le hash formaté en Base64 (sel:hash)
     */
    public static String hashPassword(String password) {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        
        byte[] hash = hash(password.toCharArray(), salt);
        
        // Format: encodedSalt:encodedHash
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedHash = Base64.getEncoder().encodeToString(hash);
        
        return encodedSalt + ":" + encodedHash;
    }
    
    /**
     * Vérifie si un mot de passe correspond à un hash
     * @param password Le mot de passe à vérifier
     * @param storedHash Le hash stocké (au format sel:hash en Base64)
     * @return true si le mot de passe correspond, false sinon
     */
    public static boolean checkPassword(String password, String storedHash) {
        try {
            // Séparer le sel et le hash
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);
            
            // Recalculer le hash avec le mot de passe fourni et le sel stocké
            byte[] testHash = hash(password.toCharArray(), salt);
            
            // Comparaison des hash en temps constant pour éviter les attaques par timing
            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            
            return diff == 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Méthode interne pour hasher un mot de passe avec PBKDF2
     */
    private static byte[] hash(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erreur lors du hashage: " + e.getMessage(), e);
        }
    }
}