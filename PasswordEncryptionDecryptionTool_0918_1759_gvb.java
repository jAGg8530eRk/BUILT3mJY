// 代码生成时间: 2025-09-18 17:59:34
import io.micronaut.core.annotation.Introspected;
    import javax.crypto.Cipher;
    import javax.crypto.KeyGenerator;
    import javax.crypto.SecretKey;
    import javax.crypto.spec.SecretKeySpec;
    import java.security.SecureRandom;
    import java.util.Base64;

    /**
     * PasswordEncryptionDecryptionTool is a utility class for encrypting and decrypting passwords.
     */
    @Introspected
    public class PasswordEncryptionDecryptionTool {

        /**
         * Encrypts a plain text password using AES algorithm.
         *
         * @param password the plain text password to be encrypted
         * @return the encrypted password as a Base64 encoded string
         * @throws Exception if encryption fails
         */
        public String encryptPassword(String password) throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, SecureRandom.getInstanceStrong());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes("UTF-8\));
            return Base64.getEncoder().encodeToString(encryptedBytes);
# 添加错误处理
        }

        /**
         * Decrypts a Base64 encoded encrypted password using AES algorithm.
         *
         * @param encryptedPassword the Base64 encoded encrypted password to be decrypted
# 添加错误处理
         * @return the decrypted plain text password
         * @throws Exception if decryption fails
         */
        public String decryptPassword(String encryptedPassword) throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, SecureRandom.getInstanceStrong());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, "UTF-8");
        }

        /**
         * Main method for testing the password encryption and decryption.
         *
         * @param args command line arguments
         */
        public static void main(String[] args) {
            PasswordEncryptionDecryptionTool tool = new PasswordEncryptionDecryptionTool();
            try {
                String password = "mysecretpassword";
                String encryptedPassword = tool.encryptPassword(password);
                System.out.println("Encrypted Password: " + encryptedPassword);
                String decryptedPassword = tool.decryptPassword(encryptedPassword);
                System.out.println("Decrypted Password: " + decryptedPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }