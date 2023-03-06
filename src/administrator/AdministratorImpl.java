package administrator;

import dao.SecurityDao;
import dao.UserProfileDao;
import dao.domain.*;
import dao.domain.Operation;
import log.StaticLogBase;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static dao.domain.Operation.*;
import static java.util.Objects.isNull;

public class AdministratorImpl  extends StaticLogBase implements Administrator {

    private static final String ALGORITHM = "AES";
    private static final Map<Authority, List<Operation>> ALLOWED_OPERATIONS = Map.ofEntries(
                    Map.entry(Authority.ROOT, List.of(USER_MANAGEMENT, CHANGE_PASSWORD, EXPORT_LOGS, BASE_ACCESS)),
                    Map.entry(Authority.USER, List.of(CHANGE_PASSWORD, BASE_ACCESS)));
    private static final String KEY = "546Q6T09HCM5KAEC2RLLBD4SYUVQ9OQ2";
    private final UserProfileDao userProfileDao;
    private final SecurityDao securityDao;
    public AdministratorImpl() {
        userProfileDao = new UserProfileDao();
        securityDao = new SecurityDao();
    }


    @Override
    public boolean saveUser(String username, String password, Authority authority) {
        UserProfile userProfile;
        try {
            userProfile = new UserProfile(
                    encrypt(username),
                    hash(password),
                    encrypt(authority.getValue()));
        } catch (NullPointerException e) {
            log.warning("UNABLE TO SAVE USER PROFILE");
            log.warning(e.getMessage());
            return false;
        }
        return userProfileDao.save(userProfile);
    }


    @Override
    public boolean deleteUser(String username) {
        String encryptedRootAuthority = encrypt(Authority.ROOT.getValue());
        UserProfile root = userProfileDao.getRoot();
        String rootUsername = decrypt(root.getUsername());
        if  (isNull(rootUsername) || rootUsername.equals(username)) {
            log.info("UNABLE TO DECRYPT OR ATTEMPTED TO DELETE ROOT USER");
            return false;
        }
        return userProfileDao.delete(username);
    }

    @Override
    public Authorization authorizeUser(UserProfile user, Operation operation) {
        String encryptedUsername = encrypt(user.getUsername());
        if (isNull(encryptedUsername)) {
            log.warning("ERROR ENCRYPTING USERNAME");
            return Authorization.UNAUTHORIZED;
        }

        UserProfile searchResult = userProfileDao.getUser(encryptedUsername);
        if (isNull(searchResult)) return Authorization.UNAUTHORIZED;

        String authorityValue = decrypt(user.getAuthorities());
        if

        for (Authority authority: Authority.values()) {
            if (authorityValue.equals(authority.getValue())) {
                return ALLOWED_OPERATIONS.get(authority).contains(operation) ?
                Authorization.AUTHORIZED : Authorization.UNAUTHORIZED;
            }
        }
        return Authorization.UNAUTHORIZED;
    }

    @Override
    public List<LogRecord> getLogs() {
        return securityDao.getRecords();
    }

    public String hash(String input) {
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
            log.warning(e.getMessage());
            return null;
        }
        return hexString.toString();
    }

    public String encrypt(String value) {
        byte[] encryptedValue;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            encryptedValue = cipher.doFinal(value.getBytes());
        } catch (IllegalBlockSizeException | NoSuchPaddingException |
                 NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            log.warning("UNABLE TO ENCRYPT");
            log.info(e.getMessage());
            return null;
        }
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decrypt(String value) {
        byte[] decryptedValue;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(value);
            decryptedValue = cipher.doFinal(decodedValue);
        } catch (IllegalBlockSizeException | BadPaddingException |
                 NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            log.warning("UNABLE TO DECRYPT");
            log.info(e.getMessage());
            return null;
        }
        return new String(decryptedValue);
    }

}
