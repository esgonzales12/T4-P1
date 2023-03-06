package administrator;

import dao.SecurityDao;
import dao.User;
import dao.UserProfileDao;
import dao.domain.Operation;
import dao.domain.LogRecord;
import dao.domain.Operation;
import dao.domain.UserProfile;
import log.StaticLogBase;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class AdministratorImpl  extends StaticLogBase implements Administrator {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "ABC";
    private static UserProfileDao userProfileDao;
    private SecurityDao securityDao;


    public void saveUser(String username, String password, Operation authority) {
        UserProfile userProfile = new UserProfile(username, password, authority);
        userProfileDao.saveUserProfile(userProfile);
    }

    public void deleteUser(String username) {
        userProfileDao.deleteUserProfileByUsername(username);
    }


    public static void authorizeUser(User user) {
        userProfileDao.updateUserProfile(user);
    }

    public static void deauthorizeUser(User user) {
        userProfileDao.updateUserProfile(user);
    }

    @Override
    public List<LogRecord> getLogs() {
        return null;
    }

    private static String stringHash(String input) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            log.warning("NO SHA-256 ALGORITHM FOUND");
            System.exit(1);
        }
        return hexString.toString();
    }

    private static String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedValue = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    private static String decrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedValue = Base64.getDecoder().decode(value);
        byte[] decryptedValue = cipher.doFinal(decodedValue);
        return new String(decryptedValue);
    }

}
