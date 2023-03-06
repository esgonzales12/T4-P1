package administrator;

import dao.SecurityDao;
import dao.UserProfileDao;
import dao.domain.*;
import dao.domain.Operation;
import log.StaticLogBase;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static dao.domain.Operation.*;

public class AdministratorImpl  extends StaticLogBase implements Administrator {

    private static final String ALGORITHM = "AES";
    private static final Map<Authority, List<Operation>> AUTHORITY_MAP = Map.ofEntries(
                    Map.entry(Authority.ROOT, List.of(USER_MANAGEMENT,
                            CHANGE_PASSWORD,
                            EXPORT_LOGS,
                            BASE_ACCESS)),
                    Map.entry(Authority.USER, List.of(CHANGE_PASSWORD, BASE_ACCESS))
            );
    private static final String KEY = "546Q6T09HCM5KAEC2RLLBD4SYUVQ9OQ2";
    private UserProfileDao userProfileDao;
    private SecurityDao securityDao;
    public AdministratorImpl() {

    }


    public void saveUser(String username, String password, Authority authority) {
        UserProfile userProfile = new UserProfile(username, password, authority.getValue());
//        userProfileDao.saveUserProfile(userProfile);
    }


    public void deleteUser(String username) {
//        userProfileDao.deleteUserProfileByUsername(username);
    }

    @Override
    public void authorizeUser(UserProfile user, Operation operation) {

    }



//    public static void authorizeUser(User user) {
//        userProfileDao.updateUserProfile(user);
//    }
//
//    public static void deauthorizeUser(User user) {
//        userProfileDao.updateUserProfile(user);
//    }

    @Override
    public List<LogRecord> getLogs() {
        return null;
    }

    public static String stringHash(String input) {
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

    public String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedValue = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedValue = Base64.getDecoder().decode(value);
        byte[] decryptedValue = cipher.doFinal(decodedValue);
        return new String(decryptedValue);
    }

}
